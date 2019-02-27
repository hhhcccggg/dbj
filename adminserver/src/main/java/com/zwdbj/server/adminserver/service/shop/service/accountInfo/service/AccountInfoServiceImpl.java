package com.zwdbj.server.adminserver.service.shop.service.accountInfo.service;

import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.adminserver.middleware.mq.MQWorkSender;
import com.zwdbj.server.adminserver.service.shop.service.accountInfo.model.SmsSendCfg;
import com.zwdbj.server.adminserver.service.user.model.AdModifyManagerPasswordInput;
import com.zwdbj.server.adminserver.service.user.model.AdNewlyPwdInput;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {
    private Logger logger = LoggerFactory.getLogger(AccountInfoServiceImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    /**
     * 校验手机和验证码合法性
     *
     * @param phone
     * @param code
     * @return
     */
    @Override
    public ServiceStatusInfo<Object> checkPhoneCode(String phone, String code) {

        //TODO 审核以后删除
        // 验证手机验证码是否正确
        String cacheKey = AppConfigConstant.getRedisPhoneCodeKey(phone);
        boolean hasPhoneCode = stringRedisTemplate.hasKey(cacheKey);
        if (!hasPhoneCode) {
            return new ServiceStatusInfo<>(1, "请输入正确的手机号和验证码", null);
        }
        String cachePhoneCode = this.stringRedisTemplate.opsForValue().get(cacheKey);
        if (!code.equals(cachePhoneCode)) {
            return new ServiceStatusInfo<>(1, "请输入正确的验证码", null);
        }
        //移除验证码
        stringRedisTemplate.delete(cacheKey);
        return new ServiceStatusInfo<>(0, "验证成功", null);
    }

    /**
     * @param phone
     * @return 发送给手机验证码并返回验证码
     */
    @Override
    public ServiceStatusInfo<String> sendPhoneCode(String phone) {
        String regEx = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern r = Pattern.compile(regEx);
        Matcher m1 = r.matcher(phone);
        boolean rs1 = m1.matches();
        if (!rs1  ) return new ServiceStatusInfo<>(1, "请填写正确格式的手机号", null);
        boolean a = this.userService.phoneIsExit(phone);
        if (!a)return new ServiceStatusInfo<String>(1, "此手机号还没有注册", "");
        //判断是否可以发送验证码
        ValueOperations<String, SmsSendCfg> operations = redisTemplate.opsForValue();
        SmsSendCfg cfg = null;
        long currentTimeStamp = System.currentTimeMillis() / 1000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = simpleDateFormat.format(new Date().getTime());

        if (redisTemplate.hasKey(AppConfigConstant.getRedisPhoneCodeCfgKey(phone))) {
            try {
                cfg = operations.get(AppConfigConstant.getRedisPhoneCodeCfgKey(phone));
                if (currentTimeStamp - cfg.getLastSendSmsTimeStamp() < AppConfigConstant.APP_SMS_SEND_INTERVAL) {
                    return new ServiceStatusInfo<String>(1, "发送验证码太频繁，请稍后再试.", "");
                }
                String[] cfgDayArr = cfg.getDaySendCount().split(":");
                int currentSendCount = Integer.parseInt(cfgDayArr[1]);
                if (todayStr.equals(cfgDayArr[0])) {
                    if (currentSendCount > AppConfigConstant.APP_SMS_SEND_MAX_COUNT_DAY) {
                        return new ServiceStatusInfo<String>(1, "超过当日最大验证码发送次数", "");
                    }
                    cfg.setLastSendSmsTimeStamp(currentTimeStamp);
                    cfg.setDaySendCount(todayStr + ":" + (currentSendCount + 1));
                } else {
                    // 不是当天
                    cfg.setLastSendSmsTimeStamp(currentTimeStamp);
                    cfg.setDaySendCount(todayStr + ":" + 1);
                }
            } catch (Exception ex) {
                logger.warn(ex.getMessage());
                logger.warn(ex.getStackTrace().toString());
                //重新处理缓存
                cfg = new SmsSendCfg(currentTimeStamp, todayStr + ":1");
                operations.set(AppConfigConstant.getRedisPhoneCodeCfgKey(phone), cfg);
                return new ServiceStatusInfo<String>(1, "发送验证码失败，请稍后再试.", "");
            }
        }


        //生成验证码
        String code = UniqueIDCreater.generatePhoneCode();

        if (AppConfigConstant.APP_SMS_SEND_OPEN) {
            try {
                // 发送验证码加入消息队列
                QueueWorkInfoModel.QueueWorkPhoneCode phoneCode = QueueWorkInfoModel.QueueWorkPhoneCode.newBuilder()
                        .setPhone(phone)
                        .setCode(code)
                        .build();
                QueueWorkInfoModel.QueueWorkInfo queueWorkInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                        .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.SEND_PHONE_CODE)
                        .setPhoneCode(phoneCode)
                        .build();
                MQWorkSender.shareSender().send(queueWorkInfo);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                return new ServiceStatusInfo<>(1, "发送验证码失败，请稍后重试.", "");
            }
        }
        //刷新缓存
        if (cfg == null) {
            cfg = new SmsSendCfg(currentTimeStamp, todayStr + ":1");
        }
        operations.set(AppConfigConstant.getRedisPhoneCodeCfgKey(phone), cfg);
        // 存到缓存
        stringRedisTemplate.opsForValue().set(AppConfigConstant.getRedisPhoneCodeKey(phone), code, AppConfigConstant.APP_SMS_CODE_EXPIRE_TIME, TimeUnit.SECONDS);
        if (AppConfigConstant.APP_SMS_SEND_OPEN) {
            return new ServiceStatusInfo<>(0, "发送成功", "");
        } else {
            return new ServiceStatusInfo<>(0, "", code);
        }
    }

    @Override
    public ServiceStatusInfo<Long> newlyPwdAd(AdNewlyPwdInput input) {
        try {
            ServiceStatusInfo<Long> result = this.userService.newlyPwdAd(input);
            return result;
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "找回密码失败" + e.getMessage(), null);
        }
    }

    /**
     * 修改用户密码
     *
     * @param id
     * @param input
     * @return
     */
    public ServiceStatusInfo<Long> modifyPwdAd(Long id, AdModifyManagerPasswordInput input) {


        try {
            ServiceStatusInfo<Long> result = this.userService.modifyPwdAd(id, input);
            return result;
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改密码失败" + e.getMessage(), null);
        }
    }
}
