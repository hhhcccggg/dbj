package com.zwdbj.server.mobileapi.service.pay.wechat.service;

import com.alibaba.fastjson.JSON;
import com.zwdbj.server.mobileapi.service.pay.wechat.model.ChargeCoinWXResult;
import com.zwdbj.server.mobileapi.service.pay.model.ChargeCoinInput;
import com.zwdbj.server.mobileapi.service.pay.wechat.model.WXRefundInput;
import com.zwdbj.server.mobileapi.service.shop.order.model.PayOrderInput;
import com.zwdbj.server.mobileapi.service.shop.order.service.OrderService;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailModifyInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.IUserAssetService;
import com.zwdbj.server.pay.wechat.wechatpay.WXPayAppCfg;
import com.zwdbj.server.pay.wechat.wechatpay.model.*;
import com.zwdbj.server.pay.wechat.wechatpay.service.WechatPayService;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class WXPayService {
    private Logger logger = LoggerFactory.getLogger(WXPayService.class);
    @Autowired
    private IUserAssetService userAssetServiceImpl;
    @Autowired
    private WechatPayService wechatPayService;
    @Autowired
    private OrderService orderService;
    private WXPayAppCfg wxPayAppCfg;
    public WXPayService(WXPayAppCfg cfg) {
        this.wxPayAppCfg = cfg;
    }
    /**
     * @param input 充值信息
     * @param userId 谁充值
     * @return 返回预付信息
     */
    @Transactional
    public ServiceStatusInfo<ChargeCoinWXResult> chargeCoins(ChargeCoinInput input, long userId) {
        // TODO 解析充值模板，当前直接解析小饼干
        // 生成充值明细订单
        // 1:10比例充值小饼干，单位分
        int rmbs = 0;
        if(this.wxPayAppCfg.isSandBox()) {
            rmbs = 201;
        } else {
            if (this.wxPayAppCfg.getIsTest()) {
                rmbs = 1;
            } else {
                rmbs = (input.getCoins() / 10) * 100;
            }
        }
        UserCoinDetailAddInput detailInput = new UserCoinDetailAddInput();
        detailInput.setTitle("充值"+input.getCoins()+"小饼干");
        detailInput.setNum(input.getCoins());
        detailInput.setExtraData("");
        detailInput.setType("PAY");
        detailInput.setTradeNo("");
        detailInput.setTradeType("WECHAT");
        detailInput.setStatus("PROCESSING");
        long id = this.userAssetServiceImpl.addUserCoinDetail(userId,detailInput);
        // 生成预付单
        UnifiedOrderInput unifiedOrderInput = new UnifiedOrderInput();
        unifiedOrderInput.setBody("爪子 充值"+input.getCoins()+"小饼干");
        unifiedOrderInput.setFeeType("CNY");
        unifiedOrderInput.setNotifyUrl(this.wxPayAppCfg.getPayResultCallbackUrl());
        unifiedOrderInput.setTradeType("APP");
        unifiedOrderInput.setTotalFee(rmbs);
        unifiedOrderInput.setOutTradeNo(String.valueOf(id));
        ServiceStatusInfo<UnifiedOrderDto> unifiedOrderDtoServiceStatusInfo =
                this.wechatPayService.unifiedOrder(unifiedOrderInput);
        if (!unifiedOrderDtoServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,unifiedOrderDtoServiceStatusInfo.getMsg(),null);
        }
        //构建预付订单信息
        ServiceStatusInfo<Map<String,String>> mapServiceStatusInfo =
                this.wechatPayService.invokePayRequestInfo(unifiedOrderDtoServiceStatusInfo.getData().getPrepayId());
        if (!mapServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,mapServiceStatusInfo.getMsg(),null);
        }
        ChargeCoinWXResult chargeCoinWXResult = new ChargeCoinWXResult();
        chargeCoinWXResult.setPrepayId(unifiedOrderDtoServiceStatusInfo.getData().getPrepayId());
        chargeCoinWXResult.setNonceStr(mapServiceStatusInfo.getData().get("noncestr"));
        chargeCoinWXResult.setPackageN(mapServiceStatusInfo.getData().get("package"));
        chargeCoinWXResult.setSign(mapServiceStatusInfo.getData().get("sign"));
        chargeCoinWXResult.setTimestamp(mapServiceStatusInfo.getData().get("timestamp"));
        chargeCoinWXResult.setOutTradeNo(String.valueOf(id));
        return new ServiceStatusInfo<>(0,"OK", chargeCoinWXResult);
    }
    /**
     * @param input 付款信息
     * @param userId 谁付款
     * @return 返回预付信息
     */
    @Transactional
    public ServiceStatusInfo<ChargeCoinWXResult> payOrder(PayOrderInput input, long userId) {
        // TODO 解析充值模板，当前直接解析小饼干
        // 生成充值明细订单
        // 1:10比例充值小饼干，单位分
        int rmbs = 0;
        if(this.wxPayAppCfg.isSandBox()) {
            rmbs = 201;
        } else {
            if (this.wxPayAppCfg.getIsTest()) {
                rmbs = 1;
            } else {
                rmbs = input.getPayMoney();
            }
        }
        // 生成预付单
        UnifiedOrderInput unifiedOrderInput = new UnifiedOrderInput();
        unifiedOrderInput.setBody("爪子 订单付款"+rmbs/100f+"元");
        unifiedOrderInput.setFeeType("CNY");
        unifiedOrderInput.setNotifyUrl(this.wxPayAppCfg.getOrderPayResultCallbackUrl());
        unifiedOrderInput.setTradeType("APP");
        unifiedOrderInput.setTotalFee(rmbs);
        unifiedOrderInput.setOutTradeNo(String.valueOf(input.getOrderId()));
        ServiceStatusInfo<UnifiedOrderDto> unifiedOrderDtoServiceStatusInfo =
                this.wechatPayService.unifiedOrder(unifiedOrderInput);
        if (!unifiedOrderDtoServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,unifiedOrderDtoServiceStatusInfo.getMsg(),null);
        }
        //构建预付订单信息
        ServiceStatusInfo<Map<String,String>> mapServiceStatusInfo =
                this.wechatPayService.invokePayRequestInfo(unifiedOrderDtoServiceStatusInfo.getData().getPrepayId());
        if (!mapServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,mapServiceStatusInfo.getMsg(),null);
        }
        ChargeCoinWXResult chargeCoinWXResult = new ChargeCoinWXResult();
        chargeCoinWXResult.setPrepayId(unifiedOrderDtoServiceStatusInfo.getData().getPrepayId());
        chargeCoinWXResult.setNonceStr(mapServiceStatusInfo.getData().get("noncestr"));
        chargeCoinWXResult.setPackageN(mapServiceStatusInfo.getData().get("package"));
        chargeCoinWXResult.setSign(mapServiceStatusInfo.getData().get("sign"));
        chargeCoinWXResult.setTimestamp(mapServiceStatusInfo.getData().get("timestamp"));
        chargeCoinWXResult.setOutTradeNo(String.valueOf(input.getOrderId()));
        return new ServiceStatusInfo<>(0,"OK", chargeCoinWXResult);
    }
    /**
     * @param input 退款信息
     * @param userId 谁退款
     * @return 返回退款信息
     */
    @Transactional
    public ServiceStatusInfo<RefundOrderDto> refundOrder(WXRefundInput input, long userId) {
        int rmbs = 0;
        if(this.wxPayAppCfg.isSandBox()) {
            rmbs = 201;
        } else {
            if (this.wxPayAppCfg.getIsTest()) {
                rmbs = 1;
            } else {
                rmbs = input.getRefundFee();
            }
        }
        // 生成预付单
        RefundOrderInput refundOrderInput = new RefundOrderInput();
        refundOrderInput.setRefundDesc(input.getRefundDesc());
        refundOrderInput.setTotalFee(rmbs);
        refundOrderInput.setRefundFee(rmbs);
        refundOrderInput.setTransactionId(input.getTransactionId());
        refundOrderInput.setType("WECHAT");
        refundOrderInput.setNotifyUrl(this.wxPayAppCfg.getOrderRefundResultCallbackUrl());
        ServiceStatusInfo<RefundOrderDto> refundOrderDtoServiceStatusInfo =
                this.wechatPayService.refundOrder(refundOrderInput);
        if (!refundOrderDtoServiceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,refundOrderDtoServiceStatusInfo.getMsg(),null);
        }
        return new ServiceStatusInfo<>(0,"OK", refundOrderDtoServiceStatusInfo.getData());
    }

    /**
     * @param type  1:小饼干充值  2:订单付款
     * @param input
     * @return 订单支付查询结果
     */
    public ServiceStatusInfo<OrderPayResultDto> orderQuery(OrderQueryInput input,int type) {
        ServiceStatusInfo<OrderPayResultDto> serviceStatusInfo = this.wechatPayService.orderQuery(input);
        logger.info(JSON.toJSONString(serviceStatusInfo));
        if (!serviceStatusInfo.isSuccess()) {
            return serviceStatusInfo;
        }
        if (type==1){
            processPayResult(serviceStatusInfo.getData());
        }else if (type==2){
            orderPayResult(serviceStatusInfo.getData());
        }

        return serviceStatusInfo;
    }

    /**
     * 查询退款
     * @param input
     * @return
     */
    public ServiceStatusInfo<RefundQueryResultDto> refundOrderQuery(RefundQueryInput input){
        ServiceStatusInfo<RefundQueryResultDto> serviceStatusInfo = this.wechatPayService.RefundOrderQuery(input);
        logger.info(JSON.toJSONString(serviceStatusInfo));
        if (!serviceStatusInfo.isSuccess()) {
            return serviceStatusInfo;
        }
        this.orderRefundResult(serviceStatusInfo.getData());

        return serviceStatusInfo;
    }

    /**
     * @param type 1:小饼干充值  2:订单付款
     * @param resFromWX 来自微信的支付结果通知
     * @return 响应微信支付结果通知
     */
    public ServiceStatusInfo<String> responseWeChatPayResult(String resFromWX,int type) {
        logger.info("收到微信支付回调："+resFromWX);
        ServiceStatusInfo<PayNotifyResult> stringServiceStatusInfo = this.wechatPayService.responseWeChatPayResult(resFromWX);
        if(!stringServiceStatusInfo.isSuccess()) {
            logger.info(stringServiceStatusInfo.getMsg());
            return new ServiceStatusInfo<>(stringServiceStatusInfo.getCode(),stringServiceStatusInfo.getMsg(),null);
        }
        if (type==1){
            processPayResult(stringServiceStatusInfo.getData().getPayResultDto());
        }else if (type==2){
            orderPayResult(stringServiceStatusInfo.getData().getPayResultDto());
        }

        return new ServiceStatusInfo<>(0,"OK",stringServiceStatusInfo.getData().getResponseWeChatXML());
    }
    /**
     * @param
     * @param resFromWX 来自微信的退款结果通知
     * @return 响应微信退款结果通知
     */
    public ServiceStatusInfo<String> responseWeChatRefundResult(String resFromWX) {
        logger.info("收到微信支付回调："+resFromWX);
        ServiceStatusInfo<RefundNotifyResult> stringServiceStatusInfo = this.wechatPayService.responseWeChatRefundResult(resFromWX);
        if(!stringServiceStatusInfo.isSuccess()) {
            logger.info(stringServiceStatusInfo.getMsg());
            return new ServiceStatusInfo<>(stringServiceStatusInfo.getCode(),stringServiceStatusInfo.getMsg(),null);
        }
        this.orderRefundResult(stringServiceStatusInfo.getData().getDto());

        return new ServiceStatusInfo<>(0,"OK",stringServiceStatusInfo.getData().getResponseWeChatXML());
    }
    @Transactional
    protected void processPayResult(OrderPayResultDto resultDto) {
        logger.info("处理微信支付结果"+resultDto.toString());
        if (resultDto.getTradeState().equals("SUCCESS")) {
            logger.info("交易成功");
            UserCoinDetailModifyInput coinDetailModifyInput = new UserCoinDetailModifyInput();
            coinDetailModifyInput.setId(Long.parseLong(resultDto.getOutTradeNo()));
            coinDetailModifyInput.setType("PAY");
            coinDetailModifyInput.setStatus("SUCCESS");
            coinDetailModifyInput.setTradeNo(resultDto.getTransactionId());
            this.userAssetServiceImpl.updateUserCoinDetail(coinDetailModifyInput);
        } else {
            logger.info("交易失败");
        }
    }
    @Transactional
    protected void orderPayResult(OrderPayResultDto resultDto) {
        logger.info("处理微信支付结果"+resultDto.toString());
        if (resultDto.getTradeState().equals("SUCCESS")) {
            logger.info("交易成功");
            long id = Long.valueOf(resultDto.getOutTradeNo());
            String tradeNo = resultDto.getTransactionId();
            String params = resultDto.toString();
            this.orderService.updateOrderPay(id,"WECHAT",tradeNo,params);
        } else {
            logger.info("交易失败");
        }
    }
    @Transactional
    protected void orderRefundResult(RefundQueryResultDto resultDto) {
        logger.info("处理微信退款结果"+resultDto.toString());
        if (resultDto.getRefundStatus().equals("SUCCESS")) {
            logger.info("退款成功");
            long orderId = Long.valueOf(resultDto.getOutTradeNo());
            String tradeNo = resultDto.getTransactionId();
            String params = resultDto.toString();
            //TODO  解决退款后的问题
        } else {
            logger.info("交易失败");
        }
    }
}
