package com.zwdbj.server.pay.wechat.wechatpay.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.zwdbj.server.config.settings.PayConfigs;
import com.zwdbj.server.utility.common.util.AESUtil;
import com.zwdbj.server.utility.common.util.MD5Util;
import com.zwdbj.server.pay.wechat.wechatpay.WeChatPayConfig;
import com.zwdbj.server.pay.wechat.wechatpay.model.*;
import com.zwdbj.server.utility.common.IP;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.UnifiedOrderInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
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
        this.isSandbox = this.payConfigs.isWechatIsSandbox();
        if (isSandbox) {
            Map<String,String> resData = sandboxSignKey();
            if (resData.get("return_code").equals("SUCCESS")) {
                config = WeChatPayConfig.sandBoxPayConfig(resData.get("sandbox_signkey"),this.payConfigs.getWechatCertPath());
            } else {
                logger.info("微信支付获取沙箱KEY失败>>"+resData.get("return_msg"));
                return null;
            }
        } else {
            config = WeChatPayConfig.payConfig(this.payConfigs.getWechatCertPath());
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
        WXPay pay = new WXPay(WeChatPayConfig.payConfig(this.payConfigs.getWechatCertPath()),WXPayConstants.SignType.MD5,false);
        try {
            Map<String, String> reqData = pay.fillRequestData(new HashMap<>());
            String responseData = pay.requestWithoutCert(url,reqData,WeChatPayConfig.payConfig(this.payConfigs.getWechatCertPath()).getHttpConnectTimeoutMs()
                    ,WeChatPayConfig.payConfig(this.payConfigs.getWechatCertPath()).getHttpReadTimeoutMs());
            Map<String,String> resData = WXPayUtil.xmlToMap(responseData);
            return resData;
        }catch ( Exception ex ) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    @Autowired
    private PayConfigs payConfigs;

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


    /**
     * @param input
     * @return 统一申请退款
     */
    public ServiceStatusInfo<RefundOrderDto> refundOrder(WXRefundOrderInput input) {
        try {
            WeChatPayConfig config = chatConfig();
            WXPay pay = new WXPay(config,WXPayConstants.SignType.MD5,isSandbox);

            Map<String,String> data = new HashMap<String, String>();
            if (input.getType().equals("WECHAT")){
                data.put("transaction_id", input.getTransactionId());
            }else {
                data.put("out_trade_no", input.getTransactionId());
            }

            data.put("refund_fee", Integer.toString(input.getRefundFee()));
            data.put("total_fee", Integer.toString(input.getTotalFee()));
            data.put("out_refund_no", input.getOutRefundNo());
            data.put("notify_url", input.getNotifyUrl());

            logger.info(data.toString());

            Map<String,String> resp = pay.refund(data);
            System.out.println(resp);
            PayResult payResult = this.parseResult(resp);
            if (payResult.isSuccess()) {
                RefundOrderDto dto = new RefundOrderDto();
                dto.setCashFee(Integer.valueOf(resp.get("cash_fee")));
                dto.setOutRefundNo(resp.get("out_refund_no"));
                dto.setOutTradeNo(resp.get("out_trade_no"));
                dto.setRefundFee(Integer.valueOf(resp.get("refund_fee")));
                dto.setRefundId(resp.get("refund_id"));
                dto.setTransactionId(resp.get("transaction_id"));
                dto.setTotalFee(Integer.valueOf(resp.get("total_fee")));
                return new ServiceStatusInfo<>(0,"OK",dto);
            } else {
                return new ServiceStatusInfo<>(1,payResult.getErrMsg(),null);
            }
        } catch ( Exception e ) {
            logger.info(e.getMessage());
            logger.info(e.getStackTrace().toString());
        }
        return new ServiceStatusInfo<>(1,"退款失败",null);
    }


    public ServiceStatusInfo<RefundQueryResultDto> RefundOrderQuery(RefundQueryInput input) {
        try {
            WeChatPayConfig config = chatConfig();
            WXPay pay = new WXPay(config,WXPayConstants.SignType.MD5,isSandbox);
            Map<String,String> reqData=new HashMap<>();
            // 微信单号
            if (input.getType().equals("WECHAT")) {
                reqData.put("transaction_id",input.getTransactionId());
            } else if (input.getType().equals("MCH")){ //商户单号
                reqData.put("out_trade_no",input.getTransactionId());
            }else if (input.getType().equals("REWECHAT")){ //微信退款单号
                reqData.put("out_trade_no",input.getTransactionId());
            }else if (input.getType().equals("REMCH")){ //商户退款单号
                reqData.put("out_trade_no",input.getTransactionId());
            }
            logger.info("REFUNDORDERQUERY>>"+reqData.toString());
            Map<String,String> resp = pay.refundQuery(reqData);
            logger.info("REFUNDORDERQUERY>>"+resp.toString());
            PayResult payResult = this.parseResult(resp);
            if (payResult.isSuccess()) {
                RefundQueryResultDto dto = new RefundQueryResultDto();
                if (resp.containsKey("cash_fee")){
                    dto.setCashFee(Integer.valueOf(resp.get("cash_fee")));
                }
                if (resp.containsKey("total_fee")){
                    dto.setTotalFee(Integer.valueOf(resp.get("total_fee")));
                }
                if (resp.containsKey("refund_fee_$n")){
                    dto.setRefundFee(Integer.valueOf(resp.get("refund_fee_$n")));
                }
                if (resp.containsKey("refund_count")){
                    dto.setRefundCount(Integer.valueOf(resp.get("refund_count")));
                }
                dto.setOutRefundNo(resp.get("out_refund_no_$n"));
                dto.setOutTradeNo(resp.get("out_trade_no"));
                dto.setRefundId(resp.get("refund_id_$n"));
                dto.setRefundRecvAccout(resp.get("refund_recv_accout_$n"));
                dto.setRefundStatus(resp.get("refund_status_$n"));
                dto.setTransactionId(resp.get("transaction_id"));
                if (resp.containsKey("refund_success_time_$n")) {
                    dto.setRefundSuccessTime(resp.get("refund_success_time_$n"));
                }
                if (resp.containsKey("settlement_refund_fee_$n")) {
                    dto.setSettlementRefundFee(Integer.parseInt(resp.get("settlement_refund_fee_$n")));
                }
                return new ServiceStatusInfo<>(0,"OK",dto);
            } else {
                return new ServiceStatusInfo<>(1,payResult.getErrMsg(),null);
            }
        }catch ( Exception ex ) {
            logger.error(ex.toString());
            logger.info(ex.getMessage());
            logger.info(ex.getStackTrace().toString());
            logger.info(ex.getLocalizedMessage());
            return new ServiceStatusInfo<>(1,"来自微信退款查询失败",null);
        }
    }


    /**
     * @param responseRefundFromWeChat 微信退款异步结果通知
     * @return 响应微信
     */
    public ServiceStatusInfo<RefundNotifyResult> responseWeChatRefundResult(String responseRefundFromWeChat) {
        try {
            WeChatPayConfig config = chatConfig();
            WXPay pay = new WXPay(config, WXPayConstants.SignType.MD5, isSandbox);
            logger.info("1111111111111111111111111111111111111111111111111111111111111111111");
            Map<String,String> resData = pay.processResponseXml(responseRefundFromWeChat);
            logger.info("2222222222222222222222222222222222222222222222222222222222222222222");
            PayResult payResult = this.parseResult(resData);
            logger.info("3333333333333333333333333333333333333333333333333333333333333333333");
            if (!payResult.isSuccess()) {
                return new ServiceStatusInfo<>(1,payResult.getErrMsg(),null);
            }
            //解密
            logger.info(resData.toString());
            String a = resData.get("req_info");
            String aa = AESUtil.decryptData(a);
            Map<String,String> reqInfo =pay.processResponseXml(aa);
            logger.info(reqInfo.toString());

            RefundNotifyResult notifyResult = new RefundNotifyResult();
            //解析支付结果
            RefundQueryResultDto resultDto = new RefundQueryResultDto();
            resultDto.setTransactionId(reqInfo.get("transaction_id"));
            if(reqInfo.containsKey("cash_fee")) {
                resultDto.setCashFee(Integer.parseInt(reqInfo.get("cash_fee")));
            }
            if(reqInfo.containsKey("total_fee")) {
                resultDto.setTotalFee(Integer.parseInt(resData.get("total_fee")));
            }

            if (reqInfo.containsKey("settlement_refund_fee")){
                resultDto.setSettlementRefundFee(Integer.valueOf(reqInfo.get("settlement_refund_fee")));
            }

            resultDto.setOutRefundNo(reqInfo.get("out_trade_no"));
            resultDto.setRefundAccount(reqInfo.get("refund_account"));
            resultDto.setOutTradeNo(reqInfo.get("out_trade_no"));
            resultDto.setRefundId(reqInfo.get("refund_id"));
            if (reqInfo.containsKey("refund_fee")){
                resultDto.setRefundFee(Integer.valueOf(reqInfo.get("refund_fee")));
            }
            resultDto.setRefundRecvAccout(reqInfo.get("refund_recv_accout"));
            resultDto.setReFundRequestSource(reqInfo.get("refund_request_source"));
            resultDto.setRefundStatus(reqInfo.get("refund_status"));
            notifyResult.setDto(resultDto);
            notifyResult.setResponseWeChatXML("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");

            return new ServiceStatusInfo<>(0, "OK", notifyResult);
        } catch ( Exception ex ){
            logger.info("异步退款异常:"+ex.getMessage());
        }
        return new ServiceStatusInfo<>(1,"参数或者签名失败",null);
    }

}
