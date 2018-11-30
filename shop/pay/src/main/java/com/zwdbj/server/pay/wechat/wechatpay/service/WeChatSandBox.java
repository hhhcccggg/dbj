package com.zwdbj.server.pay.wechat.wechatpay.service;

import java.util.HashMap;
import java.util.Map;

public class WeChatSandBox {

    /**
     * @return 获取沙箱环境key
     */
    public static String sandboxSignKey() {
        String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
        Map<String,String> reqData = new HashMap<>();
    }
}
