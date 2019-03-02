package com.zwdbj.server.basearc.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.config.settings.AliyunConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendSmsService implements ISendSmsService {
    @Autowired
    private AliyunConfigs aliyunConfigs;
    /**
     * 短信发送验证码
     *
     * @param phone        手机号
     * @param code         验证码
     * @param smsSignName  短信签名
     * @param templateCode 短信模板
     * @return 返回发送状态
     */
    @Override
    public ServiceStatusInfo<Object> sendCode(String phone, String code, String smsSignName, String templateCode) {
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

