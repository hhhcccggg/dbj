package com.zwdbj.server.basearc.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.config.settings.AliyunConfigs;
import com.zwdbj.server.config.settings.SmsSendConfigs;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class SendSmsService implements ISendSmsService {
    @Autowired
    private AliyunConfigs aliyunConfigs;
    @Autowired
    private SmsSendConfigs smsSendConfigs;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    private static String REDIS_PHONE_CODE_KEY = "phoneCode";
    protected static String getRedisPhoneCodeKey(String phone) {
        return REDIS_PHONE_CODE_KEY + "_" + phone;
    }
    private static String REDIS_PHONE_CODE_CFG_KEY = "phoneCodeCfg";
    protected static String getRedisPhoneCodeCfgKey(String phone) {
        return REDIS_PHONE_CODE_CFG_KEY + "_" + phone;
    }

    /**
     * 短信发送验证码
     *
     * @param phone        手机号
     * @param smsSignName  短信签名
     * @param templateCode 短信模板
     * @return 返回发送状态
     */
    @Override
    public ServiceStatusInfo<Object> sendCode(String phone, String smsSignName, String templateCode) {
        SmsSendCfg cfg = null;
        long currentTimeStamp = System.currentTimeMillis() / 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayFormat = simpleDateFormat.format(new Date().getTime());
        if (this.redisTemplate.hasKey(getRedisPhoneCodeCfgKey(phone))) {
            try {
                cfg = (SmsSendCfg)this.redisTemplate.opsForValue().get(getRedisPhoneCodeCfgKey(phone));
                if (currentTimeStamp - cfg.getLastSendSmsTimeStamp() < this.smsSendConfigs.getSendInterval()) {
                    return new ServiceStatusInfo<Object>(500, "发送验证码太频繁，请稍后再试.", null);
                }
                String[] cfgDayArr = cfg.getDaySendCount().split(":");
                int currentSendCount = Integer.parseInt(cfgDayArr[1]);
                if (todayFormat.equals(cfgDayArr[0])) {
                    if (currentSendCount > this.smsSendConfigs.getSendMaxCountDay()) {
                        return new ServiceStatusInfo<Object>(500, "超过当日最大验证码发送次数", null);
                    }
                    cfg.setLastSendSmsTimeStamp(currentTimeStamp);
                    cfg.setDaySendCount(todayFormat + ":" + (currentSendCount + 1));
                } else {
                    cfg.setLastSendSmsTimeStamp(currentTimeStamp);
                    cfg.setDaySendCount(todayFormat + ":" + 1);
                }
            } catch (Exception ex) {
                cfg = new SmsSendCfg(currentTimeStamp, todayFormat + ":1");
                this.redisTemplate.opsForValue().set(getRedisPhoneCodeCfgKey(phone), cfg);
                return new ServiceStatusInfo<Object>(500, "发送验证码失败，请稍后再试.", null);
            }
        }
        String code = UniqueIDCreater.generatePhoneCode();
        if (this.smsSendConfigs.isSendOpen()) {
            ServiceStatusInfo<Object> sendResult = this.sendCodeByAli(phone,
                    code,
                    this.aliyunConfigs.getSmsCodeSignName(),
                    this.aliyunConfigs.getSmsTemplateCode());
            if (!sendResult.isSuccess()) {
                return sendResult;
            }
        }
        if (cfg == null) {
            cfg = new SmsSendCfg(currentTimeStamp, todayFormat + ":1");
        }
        this.redisTemplate.opsForValue().set(getRedisPhoneCodeCfgKey(phone), cfg);
        this.stringRedisTemplate.opsForValue().set(getRedisPhoneCodeKey(phone), code, this.smsSendConfigs.getCodeExpireTime(), TimeUnit.SECONDS);
        if (this.smsSendConfigs.isSendOpen()) {
            return new ServiceStatusInfo<>(0, "发送成功", null);
        } else {
            return new ServiceStatusInfo<>(0, "发送成功", code);
        }
    }

    /**
     * 校验验证码
     *
     * @param phone
     * @param code
     * @return
     */
    @Override
    public ServiceStatusInfo<Object> checkPhoneCode(String phone, String code) {
        try {
            String cacheKey = getRedisPhoneCodeKey(phone);
            boolean hasPhoneCode = this.stringRedisTemplate.hasKey(cacheKey);
            if (!hasPhoneCode) {
                return new ServiceStatusInfo<>(500, "请输入正确的手机号和验证码", null);
            }
            String cachePhoneCode = this.stringRedisTemplate.opsForValue().get(cacheKey);
            if (!code.equals(cachePhoneCode)) {
                return new ServiceStatusInfo<>(500, "请输入正确的验证码", null);
            }
            //移除验证码
            this.stringRedisTemplate.delete(cacheKey);
            return new ServiceStatusInfo<>(0, "验证成功", null);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(500, "请输入正确的手机号和验证码"+e.getMessage(), null);
        }
    }

    protected ServiceStatusInfo<Object> sendCodeByAli(String phone,String code,String smsSignName,String templateCode) {
        //超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                this.aliyunConfigs.getAccessKey(),
                this.aliyunConfigs.getAccessSecrect());
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        } catch (Exception e) {
            return new ServiceStatusInfo<>(500,e.getMessage(),null);
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phone);
        request.setSignName(smsSignName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode().equals("OK")) {
                return new ServiceStatusInfo<>(0,"OK",null);
            }
            return new ServiceStatusInfo<>(500,sendSmsResponse.getMessage(),null);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(500,e.getMessage(),null);
        }
    }
}

