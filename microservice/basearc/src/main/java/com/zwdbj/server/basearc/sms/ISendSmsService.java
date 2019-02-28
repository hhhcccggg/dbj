package com.zwdbj.server.basearc.sms;


import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

/**
 * 发送短信服务
 */
public interface ISendSmsService {
    /**
     * 短信发送验证码
     * @param phone 手机号
     * @param code 验证码
     * @param smsSignName 短信签名
     * @param templateCode 短信模板
     * @return 返回发送状态
     */
    ServiceStatusInfo<Object> sendCode(String phone, String code, String smsSignName, String templateCode);
}
