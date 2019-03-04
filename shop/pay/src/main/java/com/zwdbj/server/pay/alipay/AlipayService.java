package com.zwdbj.server.pay.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.zwdbj.server.pay.alipay.model.*;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AlipayService {
    private AlipayClient alipayClient = AlipaySDKClient.getInstance().getAlipayClient();
    @Autowired
    private AliPayConfig aliPayConfig;
    private Logger logger = LoggerFactory.getLogger(AlipayService.class);
    /**
     * @param input
     * @param type  1 :小饼干充值  2:订单付款
     * @return 阿里支付预下单
     */
    public ServiceStatusInfo<AliAppPayResult> appPay(AliAppPayInput input,int type) {
        try {
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            String bizJson = JSON.toJSONString(input);
            request.setBizContent(bizJson);
            //  异步回调
            if (type==1){
                request.setNotifyUrl(this.aliPayConfig.getPayResultCallbackUrl());
            }else if (type==2){
                // TODO 订单付款的回调地址
                request.setNotifyUrl(this.aliPayConfig.getOrderPayResultCallbackUrl());
            }

            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            if (response.isSuccess()) {
                AliAppPayResult result = new AliAppPayResult();
                result.setOutTradeNo(response.getOutTradeNo());
                result.setSellerId(response.getSellerId());
                result.setTotalAmount(response.getTotalAmount());
                result.setTradeNo(response.getTradeNo());
                result.setOrderString(response.getBody());
                return new ServiceStatusInfo<>(0,"OK",result);
            } else {
                logger.info(response.getCode()+","+response.getMsg());
                return new ServiceStatusInfo<>(1,"下单失败("+response.getCode()+")",null);
            }
        } catch ( AlipayApiException ex ) {
            logger.info(ex.getErrMsg());
            logger.info(ex.getErrCode());
            return new ServiceStatusInfo<>(1,ex.getErrMsg(),null);
        }
    }

    /**
     * @param input 订单查询参数
     * @return 返回订单情况
     */
    public ServiceStatusInfo<AliOrderQueryResult> orderQuery(AliOrderQueryInput input) {
        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            String json = JSON.toJSONString(input);
            request.setBizContent(json);
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                AliOrderQueryResult aliOrderQueryResult = new AliOrderQueryResult();
                aliOrderQueryResult.setBuyerPayAmount(response.getBuyerPayAmount());
                aliOrderQueryResult.setOutTradeNo(response.getOutTradeNo());
                aliOrderQueryResult.setTotalAmount(response.getTotalAmount());
                aliOrderQueryResult.setTradeNo(response.getTradeNo());
                aliOrderQueryResult.setTradeStatus(response.getTradeStatus());
                return new ServiceStatusInfo<>(0,"OK",aliOrderQueryResult);
            } else {
                logger.warn(response.getCode());
                logger.warn(response.getMsg());
                logger.warn(response.getSubCode());
                logger.warn(response.getSubMsg());
                return new ServiceStatusInfo<>(1,response.getMsg()+","+response.getSubMsg(),null);
            }
        } catch ( AlipayApiException ex ) {
            logger.info(ex.getLocalizedMessage());
            logger.info(ex.getErrMsg());
            logger.info(ex.getErrCode());
            return new ServiceStatusInfo<>(1,"查询失败("+ex.getErrCode()+")",null);
        }
    }

    public ServiceStatusInfo<Object> paramsRsaCheckV1(Map<String,String> params) {
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipaySDKClient.getPublicKey(), "UTF-8","RSA2");
            if (flag) {
                return new ServiceStatusInfo<>(0, "OK", params);
            } else {
                return new ServiceStatusInfo<>(1,"签名校验失败",null);
            }
        } catch ( AlipayApiException ex ) {
            logger.info(ex.getLocalizedMessage());
            logger.info(ex.getErrMsg());
            logger.info(ex.getErrCode());
            return new ServiceStatusInfo<>(1,"签名校验失败("+ex.getErrCode()+")",null);
        }
    }


    /**
     * @param input
     * @return 阿里支付退款
     */
    public ServiceStatusInfo<AliAppRefundDto> appPayRefund(AliAppRefundInput input) {
        try {
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest ();
            String bizJson = JSON.toJSONString(input);
            logger.info(bizJson);
            request.setBizContent("{" +
                    "\"out_trade_no\":\""+input.getOutTradeNo()+"\"," +
                    "\"trade_no\":\""+input.getTradeNo()+"\"," +
                    "\"refund_amount\":"+input.getRefundAmount()+"," +
                    "\"refund_currency\":\"CNY\"," +
                    "\"refund_reason\":\""+input.getRefundReason()+"\"," +
                    "\"out_request_no\":\""+input.getOutRequestNo()+"\"" +
                    "  }");
            //  异步回调
            // TODO 订单退款的回调地址
            request.setNotifyUrl(this.aliPayConfig.getOrderRefundResultCallbackUrl());

            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if ("10000".equals(response.getCode())) {
                AliAppRefundDto result = new AliAppRefundDto();
                result.setOutTradeNo(response.getOutTradeNo());
                result.setTradeNo(response.getTradeNo());
                result.setBuyerLogonId(response.getBuyerLogonId());
                result.setFundChange(response.getFundChange());
                result.setRefundFee(response.getRefundFee());
                result.setGmtRefundPay(response.getGmtRefundPay());
                result.setBuyerUserId(response.getBuyerUserId());
                return new ServiceStatusInfo<>(0,"OK",result);
            } else {
                logger.info(response.getCode()+","+response.getMsg()+","+response.getSubMsg());
                return new ServiceStatusInfo<>(1,"退款失败("+response.getCode()+")",null);
            }
        } catch ( AlipayApiException ex ) {
            logger.info(ex.getErrMsg());
            logger.info(ex.getErrCode());
            logger.info("支付宝退款失败异常:"+ ex.getMessage());
            return new ServiceStatusInfo<>(1,ex.getErrMsg(),null);
        }
    }

    /**
     * @param input 订单退款查询参数
     * @return 返回订单退款情况
     */
    public ServiceStatusInfo<AliAppRefundQueryDto> orderRefundQuery(AliAppRefundQueryInput input) {
        try {
            AlipayTradeFastpayRefundQueryRequest  request = new AlipayTradeFastpayRefundQueryRequest ();
            String json = JSON.toJSONString(input);
            request.setBizContent(json);
            AlipayTradeFastpayRefundQueryResponse  response = alipayClient.execute(request);
            if (response.isSuccess()) {
                AliAppRefundQueryDto appRefundQueryDto = new AliAppRefundQueryDto();
                appRefundQueryDto.setOutRequestNo(response.getOutRequestNo());
                appRefundQueryDto.setOutTradeNo(response.getOutTradeNo());
                appRefundQueryDto.setTotalAmount(response.getTotalAmount());
                appRefundQueryDto.setTradeNo(response.getTradeNo());
                appRefundQueryDto.setRefundReason(response.getRefundReason());
                appRefundQueryDto.setRefundAmount(response.getRefundAmount());
                appRefundQueryDto.setSendBackFee(response.getSendBackFee());
                appRefundQueryDto.setGmtRefundPay(response.getGmtRefundPay());
                appRefundQueryDto.setSubCode(response.getSubCode());
                return new ServiceStatusInfo<>(0,"OK",appRefundQueryDto);
            } else {
                logger.warn(response.getCode());
                logger.warn(response.getMsg());
                logger.warn(response.getSubCode());
                logger.warn(response.getSubMsg());
                return new ServiceStatusInfo<>(1,response.getMsg()+","+response.getSubMsg(),null);
            }
        } catch ( AlipayApiException ex ) {
            logger.info(ex.getLocalizedMessage());
            logger.info(ex.getErrMsg());
            logger.info(ex.getErrCode());
            return new ServiceStatusInfo<>(1,"退款查询失败("+ex.getErrCode()+")",null);
        }
    }

    /**
     * 支付宝账号之间转账
     * @param input 转账输入参数
     * @return 返回转账结果
     */
    public ServiceStatusInfo<AliTransferResult> transfer(AliTransferInput input) {
        try {
            AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
            String json = JSON.toJSONString(input);
            request.setBizContent(json);
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                AliTransferResult transferResult = new AliTransferResult();
                transferResult.setOrderId(response.getOrderId());
                transferResult.setOutBizNo(response.getOutBizNo());
                transferResult.setPayDate(response.getPayDate());
                return new ServiceStatusInfo<>(0,"OK",transferResult);
            }
            logger.warn(response.getMsg());
            logger.warn(response.getCode());
            return new ServiceStatusInfo<>(1,response.getMsg()+","+response.getSubMsg(),null);
        } catch (AlipayApiException ex) {
            logger.warn(ex.getLocalizedMessage());
            logger.warn(ex.getErrCode());
            logger.warn(ex.getErrMsg());
            return new ServiceStatusInfo<>(1,ex.getErrMsg()+"("+ex.getErrCode()+")",null);
        }
    }

    /**
     * 查询转账结果
     * @param input 查询转账参数
     * @return 返回转账结果
     */
    public ServiceStatusInfo<AliTransferQueryResult> transferQuery(AliTransferQueryInput input) {
        try {
            AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
            String json = JSON.toJSONString(input);
            request.setBizContent(json);
            AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                AliTransferQueryResult queryResult = new AliTransferQueryResult();
                queryResult.setOrderId(response.getOrderId());
                queryResult.setStatus(response.getStatus());
                queryResult.setPayDate(response.getPayDate());
                queryResult.setArrivalTimeEnd(response.getArrivalTimeEnd());
                queryResult.setOrderFee(response.getOrderFee());
                queryResult.setFailReason(response.getFailReason());
                queryResult.setOutBizNo(response.getOutBizNo());
                queryResult.setErrorCode(response.getErrorCode());
                return new ServiceStatusInfo<>(0,"OK",queryResult);
            }
            logger.warn(response.getMsg());
            logger.warn(response.getCode());
            return new ServiceStatusInfo<>(1,response.getMsg()+","+response.getSubMsg(),null);
        } catch (AlipayApiException ex) {
            logger.warn(ex.getLocalizedMessage());
            logger.warn(ex.getErrCode());
            logger.warn(ex.getErrMsg());
            return new ServiceStatusInfo<>(1,ex.getErrMsg()+"("+ex.getErrCode()+")",null);
        }
    }

    public ServiceStatusInfo<AliAppAuthLoginResult> appAuthLoginSign() {
        try {
            Map<String,String> reqData = new HashMap<>();
            reqData.put("apiname","com.alipay.account.auth");
            reqData.put("method","alipay.open.auth.sdk.code.get");
            reqData.put("app_id",AlipaySDKClient.getAppId());
            reqData.put("app_name","mc");
            reqData.put("biz_type","openservice");
            reqData.put("pid","2088231225893489");
            reqData.put("product_id","APP_FAST_LOGIN");
            reqData.put("scope","kuaijie");
            reqData.put("target_id",UUID.randomUUID().toString());
            reqData.put("auth_type","AUTHACCOUNT");
            reqData.put("sign_type","RSA2");
            String signTxt = AlipaySignature.rsaSign(reqData,AlipaySDKClient.getPrivateKey(),"UTF-8");
            reqData.put("sign",signTxt);
            String resultStr = AlipaySignature.getSignContent(reqData);
            AliAppAuthLoginResult result = new AliAppAuthLoginResult();
            result.setAuthString(resultStr);
            return new ServiceStatusInfo<>(0,"OK",result);
        } catch ( AlipayApiException ex ) {
            logger.warn(ex.getLocalizedMessage());
            logger.warn(ex.getErrCode());
            logger.warn(ex.getErrMsg());
            return new ServiceStatusInfo<>(1,ex.getErrMsg()+"("+ex.getErrCode()+")",null);
        }
    }

    public ServiceStatusInfo<AlipaySystemOauthTokenResponse> accessToken(String authCode,String userId) {
        try {
            AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
            request.setGrantType("authorization_code");
            request.setCode(authCode);
            AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return new ServiceStatusInfo<>(0,"OK",response);
            }
            logger.warn(response.getMsg());
            logger.warn(response.getCode());
            return new ServiceStatusInfo<>(1,response.getMsg()+","+response.getSubMsg(),null);
        } catch ( AlipayApiException ex ) {
            logger.warn(ex.getLocalizedMessage());
            logger.warn(ex.getErrCode());
            logger.warn(ex.getErrMsg());
            return new ServiceStatusInfo<>(1,ex.getErrMsg()+"("+ex.getErrCode()+")",null);
        }
    }

    public ServiceStatusInfo<AlipayUserInfoShareResponse> userInfo(String accessToken) {
        try {
            AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
            AlipayUserInfoShareResponse response = alipayClient.execute(request,accessToken);
            if (response.isSuccess()) {
                return new ServiceStatusInfo<>(0,"OK",response);
            }
            logger.warn(response.getMsg());
            logger.warn(response.getCode());
            return new ServiceStatusInfo<>(1,response.getMsg()+","+response.getSubMsg(),null);
        } catch ( AlipayApiException ex ) {
            logger.warn(ex.getLocalizedMessage());
            logger.warn(ex.getErrCode());
            logger.warn(ex.getErrMsg());
            return new ServiceStatusInfo<>(1,ex.getErrMsg()+"("+ex.getErrCode()+")",null);
        }
    }

}
