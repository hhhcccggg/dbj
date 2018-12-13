package com.zwdbj.server.pay.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.zwdbj.server.pay.alipay.model.AppPayInput;
import com.zwdbj.server.pay.alipay.model.AppPayResult;
import com.zwdbj.server.pay.alipay.model.OrderQueryInput;
import com.zwdbj.server.pay.alipay.model.OrderQueryResult;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlipayService {
    private AlipayClient alipayClient = AlipaySDKClient.getInstance().getAlipayClient();
    private Logger logger = LoggerFactory.getLogger(AlipayService.class);
    /**
     * @param input
     * @return 阿里支付预下单
     */
    public ServiceStatusInfo<AppPayResult> appPay(AppPayInput input) {
        try {
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            String bizJson = JSON.toJSONString(input);
            request.setBizContent(bizJson);
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            if (response.isSuccess()) {
                AppPayResult result = new AppPayResult();
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
    public ServiceStatusInfo<OrderQueryResult> orderQuery(OrderQueryInput input) {
        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            String json = JSON.toJSONString(input);
            request.setBizContent(json);
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return new ServiceStatusInfo<>(0,"OK",(OrderQueryResult)response);
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

}
