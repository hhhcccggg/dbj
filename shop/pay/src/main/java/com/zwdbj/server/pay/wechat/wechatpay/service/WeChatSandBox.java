package com.zwdbj.server.pay.wechat.wechatpay.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.zwdbj.server.pay.wechat.wechatpay.WeChatConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WeChatSandBox {
    /**
     * @return 获取沙箱环境key
     */
    public static Map<String,String> sandboxSignKey() {
        String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
        WXPay pay = new WXPay(WeChatConfig.payConfig(),WXPayConstants.SignType.MD5,false);
        try {
            Map<String, String> reqData = pay.fillRequestData(new HashMap<>());
            String responseData = pay.requestWithoutCert(url,reqData,WeChatConfig.payConfig().getHttpConnectTimeoutMs()
            ,WeChatConfig.payConfig().getHttpReadTimeoutMs());
            Map<String,String> resData = WXPayUtil.xmlToMap(responseData);
            return resData;
        }catch ( Exception ex ) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
