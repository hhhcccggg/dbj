package com.zwdbj.server.pay.wechat.wechatpay.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.zwdbj.server.pay.wechat.wechatpay.WXPayAppCfg;
import com.zwdbj.server.pay.wechat.wechatpay.WeChatPayConfig;
import com.zwdbj.server.pay.wechat.wechatpay.model.*;
import com.zwdbj.server.utility.common.IP;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    private WeChatPayConfig chatConfig() throws Exception {
        WeChatPayConfig config = null;
        this.isSandbox = this.wxPayAppCfg.isSandBox();
        if (isSandbox) {
            Map<String,String> resData = sandboxSignKey();
            if (resData.get("return_code").equals("SUCCESS")) {
                config = WeChatPayConfig.sandBoxPayConfig(resData.get("sandbox_signkey"),this.wxPayAppCfg.getCertPath());
            } else {
                logger.info("微信支付获取沙箱KEY失败>>"+resData.get("return_msg"));
                return null;
            }
        } else {
            config = WeChatPayConfig.payConfig(this.wxPayAppCfg.getCertPath());
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
     * @return 获取沙箱环境key
     */
    public Map<String,String> sandboxSignKey() {
        String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
        WXPay pay = new WXPay(WeChatPayConfig.payConfig(this.wxPayAppCfg.getCertPath()),WXPayConstants.SignType.MD5,false);
        try {
            Map<String, String> reqData = pay.fillRequestData(new HashMap<>());
            String responseData = pay.requestWithoutCert(url,reqData,WeChatPayConfig.payConfig(this.wxPayAppCfg.getCertPath()).getHttpConnectTimeoutMs()
                    ,WeChatPayConfig.payConfig(this.wxPayAppCfg.getCertPath()).getHttpReadTimeoutMs());
            Map<String,String> resData = WXPayUtil.xmlToMap(responseData);
            return resData;
        }catch ( Exception ex ) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private WXPayAppCfg wxPayAppCfg = null;
    public WechatPayService(WXPayAppCfg cfg) {
        this.wxPayAppCfg = cfg;
    }

    /**
     * @param prepayId 预付单ID
     * @return 生成APP调用支付的请求数据
     */
    public ServiceStatusInfo<Map<String,String>> invokePayRequestInfo(String prepayId) {
        try {
            WeChatPayConfig config = chatConfig();
            Map<String, String> reqData = new HashMap<>();
            reqData.put("prepayid",prepayId);
            reqData.put("package","Sign=WXPay");
            reqData.put("appid",config.getAppID());
            reqData.put("partnerid",config.getMchID());
            reqData.put("noncestr",WXPayUtil.generateNonceStr());
            reqData.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
            reqData.put("sign", WXPayUtil.generateSignature(reqData, config.getKey(), WXPayConstants.SignType.MD5));
            return new ServiceStatusInfo<>(0,"OK",reqData);
        } catch ( Exception ex ) {
            logger.info(ex.getMessage());
        }
        return new ServiceStatusInfo<>(1,"发生错误",null);
    }

    /**
     * @param responsePayFromWeChat 微信支付结果通知
     * @return 响应微信
     */
    public ServiceStatusInfo<PayNotifyResult> responseWeChatPayResult(String responsePayFromWeChat) {
        try {
            WeChatPayConfig config = chatConfig();
            WXPay pay = new WXPay(config, WXPayConstants.SignType.MD5, isSandbox);
            Map<String,String> resData = pay.processResponseXml(responsePayFromWeChat);
            PayResult payResult = this.parseResult(resData);
            if (!payResult.isSuccess()) {
                return new ServiceStatusInfo<>(1,payResult.getErrMsg(),null);
            }

            PayNotifyResult notifyResult = new PayNotifyResult();
            //解析支付结果
            OrderPayResultDto payResultDto = new OrderPayResultDto();
            payResultDto.setBankType(resData.get("bank_type"));
            if(resData.containsKey("cash_fee")) {
                payResultDto.setCashFee(Integer.parseInt(resData.get("cash_fee")));
            }
            if(resData.containsKey("total_fee")) {
                payResultDto.setTotalFee(Integer.parseInt(resData.get("total_fee")));
            }

            payResultDto.setCashFeeType(resData.get("cash_fee_type"));
            payResultDto.setFeeType(resData.get("fee_type"));
            payResultDto.setOpenId(resData.get("openid"));
            payResultDto.setTimeEnd(resData.get("time_end"));
            payResultDto.setOutTradeNo(resData.get("out_trade_no"));
            payResultDto.setTransactionId(resData.get("transaction_id"));
            payResultDto.setTradeType(resData.get("trade_type"));
            payResultDto.setTradeState("SUCCESS");
            notifyResult.setPayResultDto(payResultDto);
            notifyResult.setResponseWeChatXML("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");


            return new ServiceStatusInfo<>(
                    0,
                    "OK",
                    notifyResult);
        } catch ( Exception ex ){
            logger.info(ex.getMessage());
        }
        return new ServiceStatusInfo<>(1,"参数或者签名失败",null);
    }
    /**
     * @param input
     * @return 统一下单
     */
    public ServiceStatusInfo<UnifiedOrderDto> unifiedOrder(UnifiedOrderInput input) {
        try {
            WeChatPayConfig config = chatConfig();
            WXPay pay = new WXPay(config,WXPayConstants.SignType.MD5,isSandbox);

            Map<String,String> data = new HashMap<String, String>();
            data.put("body", input.getBody());
            data.put("out_trade_no", input.getOutTradeNo());
            data.put("fee_type", input.getFeeType());
            data.put("total_fee", Integer.toString(input.getTotalFee()));
            data.put("spbill_create_ip", IP.getIpAddr());
            data.put("notify_url", input.getNotifyUrl());
            data.put("trade_type", input.getTradeType());

            logger.info(data.toString());

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

    public ServiceStatusInfo<OrderPayResultDto> orderQuery(OrderQueryInput input) {
        try {
            WeChatPayConfig config = chatConfig();
            WXPay pay = new WXPay(config,WXPayConstants.SignType.MD5,isSandbox);
            Map<String,String> reqData=new HashMap<>();
            // 微信单号
            if (input.getType().equals("WECHAT")) {
                reqData.put("transaction_id",input.getTransactionId());
            } else { //商户单号
                reqData.put("out_trade_no",input.getTransactionId());
            }
            logger.info("ORDERQUERY>>"+reqData.toString());
            Map<String,String> resp = pay.orderQuery(reqData);
            logger.info("ORDERQUERY>>"+resp.toString());
            PayResult payResult = this.parseResult(resp);
            if (payResult.isSuccess()) {
                OrderPayResultDto dto = new OrderPayResultDto();
                dto.setOpenId(resp.get("openid"));
                dto.setTradeType(resp.get("trade_type"));
                dto.setTradeState(resp.get("trade_state"));
                dto.setBankType(resp.get("bank_type"));
                if (resp.containsKey("total_fee")) {
                    dto.setTotalFee(Integer.parseInt(resp.get("total_fee")));
                }
                if (resp.containsKey("settlement_total_fee")) {
                    dto.setSettlementTotalFee(Integer.parseInt(resp.get("settlement_total_fee")));
                }
                if (resp.containsKey("cash_fee")) {
                    dto.setCashFee(Integer.parseInt(resp.get("cash_fee")));
                }
                dto.setFeeType(resp.get("fee_type"));
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
            logger.error(ex.toString());
            logger.info(ex.getMessage());
            logger.info(ex.getStackTrace().toString());
            logger.info(ex.getLocalizedMessage());
            return new ServiceStatusInfo<>(1,"来自微信查询失败",null);
        }
    }
}
