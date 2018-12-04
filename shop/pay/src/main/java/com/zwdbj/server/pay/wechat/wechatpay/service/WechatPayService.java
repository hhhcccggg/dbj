package com.zwdbj.server.pay.wechat.wechatpay.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.zwdbj.server.pay.wechat.wechatpay.WeChatConfig;
import com.zwdbj.server.pay.wechat.wechatpay.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WechatPayService {

    /**
     * 结果情况
     */
    protected class PayResult {
        private boolean isSuccess;
        private String errMsg;

        public PayResult(boolean isSuccess, String errMsg) {
            this.isSuccess = isSuccess;
            this.errMsg = errMsg;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }
    }

    private Logger logger = LoggerFactory.getLogger(WechatPayService.class);
    private boolean isSandbox = true;

    private WeChatConfig chatConfig() throws Exception {
        WeChatConfig config = null;
        if (isSandbox) {
            Map<String,String> resData = WeChatSandBox.sandboxSignKey();
            if (resData.get("return_code").equals("SUCCESS")) {
                config = WeChatConfig.sandBoxPayConfig(resData.get("sandbox_signkey"));
            } else {
                logger.info("微信支付获取沙箱KEY失败>>"+resData.get("return_msg"));
                return null;
            }
        } else {
            config = WeChatConfig.payConfig();
        }
        return config;
    }

    /**
     * @param resp 微信支付关联接口返回的数据
     * @return 解析微信支付返回的结果是否成功
     */
    protected PayResult parseResult(Map<String,String> resp) {
        if (resp.get("return_code").equals("SUCCESS")) {
            if(resp.get("result_code").equals("SUCCESS")) {
                return new PayResult(true,"OK");
            } else {
                logger.info(resp.get("err_code"));
                return new PayResult(false,resp.get("err_code_des"));
            }
        } else {
            return new PayResult(false,resp.get("return_msg"));
        }
    }

    /**
     * @param input
     * @return 统一下单
     */
    public ServiceStatusInfo<UnifiedOrderDto> unifiedOrder(UnifiedOrderInput input) {
        try {
            WeChatConfig config = chatConfig();
            WXPay pay = new WXPay(config,WXPayConstants.SignType.MD5,isSandbox);

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

            Map<String,String> resp = pay.unifiedOrder(data);
            System.out.println(resp);
            PayResult payResult = this.parseResult(resp);
            if (payResult.isSuccess()) {
                UnifiedOrderDto dto = new UnifiedOrderDto();
                dto.setPrepayId(resp.get("prepay_id"));
                return new ServiceStatusInfo<>(0,"OK",dto);
            } else {
                return new ServiceStatusInfo<>(1,payResult.getErrMsg(),null);
            }
        } catch ( Exception e ) {
            logger.info(e.getMessage());
            logger.info(e.getStackTrace().toString());
        }
        return new ServiceStatusInfo<>(1,"下单失败",null);
    }

    public ServiceStatusInfo<OrderQueryDto> orderQuery(OrderQueryInput input) {
        try {
            WeChatConfig config = chatConfig();
            WXPay pay = new WXPay(config,WXPayConstants.SignType.MD5,isSandbox);
            Map<String,String> reqData=new HashMap<>();
            // 微信单号
            if (input.getType().equals("WECHAT")) {
                reqData.put("transaction_id",input.getTransactionId());
            } else { //商户单号
                reqData.put("out_trade_no",input.getTransactionId());
            }
            Map<String,String> resp = pay.orderQuery(reqData);
            logger.info(resp.toString());
            PayResult payResult = this.parseResult(resp);
            if (payResult.isSuccess()) {
                OrderQueryDto dto = new OrderQueryDto();
                dto.setOpenId(resp.get("openid"));
                dto.setTradeType(resp.get("trade_type"));
                dto.setTradeState(resp.get("trade_state"));
                dto.setBankType(resp.get("bank_type"));
                dto.setTotalFee(Integer.parseInt(resp.get("total_fee")));
                dto.setSettlementTotalFee(Integer.parseInt(resp.get("settlement_total_fee")));
                dto.setFeeType(resp.get("fee_type"));
                dto.setCashFee(Integer.parseInt(resp.get("cash_fee")));
                dto.setCashFeeType(resp.get("cash_fee_type"));
                dto.setTransactionId(resp.get("transaction_id"));
                dto.setOutTradeNo(resp.get("out_trade_no"));
                dto.setTimeEnd(resp.get("time_end"));
                dto.setTradeStateDesc(resp.get("trade_state_desc"));
                return new ServiceStatusInfo<>(0,"OK",dto);
            } else {
                return new ServiceStatusInfo<>(1,payResult.getErrMsg(),null);
            }
        }catch ( Exception ex ) {
            logger.info(ex.getMessage());
            logger.info(ex.getStackTrace().toString());
        }
        return new ServiceStatusInfo<>(1,"查询失败",null);
    }
}
