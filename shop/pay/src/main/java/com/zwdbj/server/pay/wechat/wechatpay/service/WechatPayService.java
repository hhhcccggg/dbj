package com.zwdbj.server.pay.wechat.wechatpay.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.zwdbj.server.pay.wechat.wechatpay.WeChatConfig;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WechatPayService {
    private Logger logger = LoggerFactory.getLogger(WechatPayService.class);

    public UnifiedOrderDto unifiedOrder(UnifiedOrderInput input) {
        WXPay pay = new WXPay(WeChatConfig.payConfig(),WXPayConstants.SignType.MD5,false);

        Map<String,String> data = new HashMap<String, String>();
        data.put("body", input.getBody());
        data.put("out_trade_no", input.getOutTradeNo());
        data.put("device_info", input.getDeviceInfo());
        data.put("fee_type", input.getFeeType());
        data.put("total_fee", Integer.toString(input.getTotalFee()));
        data.put("spbill_create_ip", input.getSpbillCreateIP());
        data.put("notify_url", input.getNotifyUrl());
        data.put("trade_type", input.getTradeType());
        data.put("product_id", input.getProductId());

        try {
            Map<String,String> resp = pay.unifiedOrder(data);
            System.out.println(resp);
            UnifiedOrderDto dto = new UnifiedOrderDto();
            if (resp.containsKey("prepay_id")) {
                dto.setPrepayId(resp.get("prepay_id"));
                dto.setReturnCode("SUCCESS");
                dto.setReturnMsg("OK");
            } else {
                dto.setReturnCode(resp.get("return_code"));
                dto.setReturnMsg(resp.get("return_msg"));
            }
            return dto;
        } catch ( Exception e ) {
            logger.info(e.getMessage());
            logger.info(e.getStackTrace().toString());
        }
        return null;
    }
}
