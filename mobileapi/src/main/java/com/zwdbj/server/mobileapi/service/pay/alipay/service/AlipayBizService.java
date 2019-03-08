package com.zwdbj.server.mobileapi.service.pay.alipay.service;

import com.zwdbj.server.mobileapi.service.pay.Refund.model.PayRefundInput;
import com.zwdbj.server.mobileapi.service.pay.alipay.model.ChargeCoinAlipayResult;
import com.zwdbj.server.mobileapi.service.pay.model.ChargeCoinInput;
import com.zwdbj.server.mobileapi.service.shop.order.model.PayOrderInput;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderDetailModel;
import com.zwdbj.server.mobileapi.service.shop.order.service.OrderService;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailModifyInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.IUserAssetService;
import com.zwdbj.server.pay.alipay.AlipayService;
import com.zwdbj.server.pay.alipay.model.*;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class AlipayBizService {
    @Autowired
    private AlipayService alipayService;
    private Logger logger = LoggerFactory.getLogger(AlipayBizService.class);
    @Autowired
    private IUserAssetService userAssetServiceImpl;
    @Autowired
    private OrderService orderService;

    /**
     * @param input 充值信息
     * @param userId 谁充值
     * @return 返回订单信息
     */
    @Transactional
    public ServiceStatusInfo<ChargeCoinAlipayResult> chargeCoins(ChargeCoinInput input,long userId) {
        // 1:10比例充值小饼干，单位分
        int rmbs = 0;
        rmbs = (input.getCoins() / 10) * 100;
        rmbs = 1;// 测试
        UserCoinDetailAddInput detailInput = new UserCoinDetailAddInput();
        detailInput.setTitle("充值"+input.getCoins()+"小饼干");
        detailInput.setNum(input.getCoins());
        detailInput.setExtraData("");
        detailInput.setType("PAY");
        detailInput.setTradeNo("");
        detailInput.setTradeType("ALIPAY");
        detailInput.setStatus("PROCESSING");
        long id = this.userAssetServiceImpl.addUserCoinDetail(userId,detailInput);
        AliAppPayInput aliAppPayInput = new AliAppPayInput();
        aliAppPayInput.setBody("充值"+input.getCoins()+"小饼干");
        aliAppPayInput.setSubject("充值小饼干");
        aliAppPayInput.setOutTradeNo(String.valueOf(id));
        aliAppPayInput.setTimeoutExpress("15m");
        float amount = rmbs/100f;
        BigDecimal b = new BigDecimal(amount);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        aliAppPayInput.setTotalAmount(String.valueOf(f1));

        ServiceStatusInfo<AliAppPayResult> serviceStatusInfo = this.alipayService.appPay(aliAppPayInput,1);
        if (!serviceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,serviceStatusInfo.getMsg(),null);
        }

        ChargeCoinAlipayResult result = new ChargeCoinAlipayResult();
        result.setBody(aliAppPayInput.getBody());
        result.setOutTradeNo(aliAppPayInput.getOutTradeNo());
        result.setSubject(aliAppPayInput.getSubject());
        result.setTimeoutExpress(aliAppPayInput.getTimeoutExpress());
        result.setTotalAmount(Float.valueOf(aliAppPayInput.getTotalAmount()));
        result.setOrderString(serviceStatusInfo.getData().getOrderString());
        return new ServiceStatusInfo<>(0,"OK",result);
    }

    /**
     * @param input 付款信息
     * @param userId 谁付款
     * @return 返回订单信息
     */
    @Transactional
    public ServiceStatusInfo<ChargeCoinAlipayResult> payOrder(PayOrderInput input, long userId){
        ProductOrderDetailModel productOrderDetailModel = this.orderService.getOrderById(input.getOrderId()).getData();
        if (productOrderDetailModel==null)return new ServiceStatusInfo<>(1,"没有此订单",null);
        int rmbs = productOrderDetailModel.getActualPayment();
        rmbs=productOrderDetailModel.getNum();//测试数据
        AliAppPayInput aliAppPayInput = new AliAppPayInput();
        aliAppPayInput.setBody("付款"+(productOrderDetailModel.getActualPayment()/100f)+"元");
        aliAppPayInput.setSubject("爪子订单付款");
        aliAppPayInput.setOutTradeNo(String.valueOf(input.getOrderId()));
        aliAppPayInput.setTimeoutExpress("15m");

        float amount = rmbs/100f;
        BigDecimal b = new BigDecimal(amount);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        aliAppPayInput.setTotalAmount(String.valueOf(f1));

        ServiceStatusInfo<AliAppPayResult> serviceStatusInfo = this.alipayService.appPay(aliAppPayInput,2);
        if (!serviceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,serviceStatusInfo.getMsg(),null);
        }

        ChargeCoinAlipayResult result = new ChargeCoinAlipayResult();
        result.setBody(aliAppPayInput.getBody());
        result.setOutTradeNo(aliAppPayInput.getOutTradeNo());
        result.setSubject(aliAppPayInput.getSubject());
        result.setTimeoutExpress(aliAppPayInput.getTimeoutExpress());
        result.setTotalAmount(Float.valueOf(aliAppPayInput.getTotalAmount()));
        result.setOrderString(serviceStatusInfo.getData().getOrderString());
        return new ServiceStatusInfo<>(0,"OK",result);
    }

    public ServiceStatusInfo<AliOrderQueryResult> orderQuery(AliOrderQueryInput input,int type) {
        ServiceStatusInfo<AliOrderQueryResult> serviceStatusInfo = this.alipayService.orderQuery(input);
        if (!serviceStatusInfo.isSuccess()) {
            logger.warn(serviceStatusInfo.getMsg());
            return serviceStatusInfo;
        }
        boolean isSuccess = serviceStatusInfo.getData().getTradeStatus().equals("TRADE_SUCCESS");
        if (type==1){
            processPayResult(serviceStatusInfo.getData().getOutTradeNo(),serviceStatusInfo.getData().getTradeNo(),isSuccess);
        }else if (type==2){
            orderPayResult(serviceStatusInfo.getData().getOutTradeNo(),serviceStatusInfo.getData().getTradeNo(),isSuccess,input.toString());
        }
        return serviceStatusInfo;
    }
    /**
     * @param input 退款信息
     * @return 返回退款信息
     */
    @Transactional
    public ServiceStatusInfo<AliAppRefundDto> refundOrder(PayRefundInput input,int refundAmount, String tradeNo){
        int rmbs = refundAmount;
        rmbs=1;//测试数据
        AliAppRefundInput aliAppRefundInput = new AliAppRefundInput();
        aliAppRefundInput.setOutRequestNo(String.valueOf(input.getOrderId()));
        aliAppRefundInput.setOutTradeNo(String.valueOf(input.getOrderId()));
        aliAppRefundInput.setRefundReason(input.getRefundReason());
        aliAppRefundInput.setTradeNo(tradeNo);

        float amount = rmbs/100f;
        BigDecimal b = new BigDecimal(amount);
        float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        aliAppRefundInput.setRefundAmount(String.valueOf(f1));

        ServiceStatusInfo<AliAppRefundDto> serviceStatusInfo = this.alipayService.appPayRefund(aliAppRefundInput);
        if (!serviceStatusInfo.isSuccess()) {
            return new ServiceStatusInfo<>(1,serviceStatusInfo.getMsg(),null);
        }
        this.orderService.updateOrderState(input.getOrderId(),tradeNo,"STATE_REFUNDING");

        return new ServiceStatusInfo<>(0,"OK",serviceStatusInfo.getData());
    }

    public ServiceStatusInfo<AliAppRefundQueryDto> orderRefundQuery(AliAppRefundQueryInput input) {
        ServiceStatusInfo<AliAppRefundQueryDto> serviceStatusInfo = this.alipayService.orderRefundQuery(input);
        if (!serviceStatusInfo.isSuccess()) {
            logger.warn(serviceStatusInfo.getMsg());
            return serviceStatusInfo;
        }
        logger.info("3333333333333333333333333");
        boolean isSuccess = serviceStatusInfo.getData().getCode().equals("10000");
        boolean isSuccess2=false;
        if (serviceStatusInfo.getData().getRefundAmount()!=null || (serviceStatusInfo.getData().getRefundAmount().length()!=0))
            isSuccess2 = true;
        if (!isSuccess){
            logger.info("支付宝退款查询错误:"+serviceStatusInfo.getData().getSubCode());
        }
        this.orderRefundResult(serviceStatusInfo.getData().getOutTradeNo(),serviceStatusInfo.getData().getTradeNo(),isSuccess2);
        return new ServiceStatusInfo<>(0,"",serviceStatusInfo.getData());
    }

    public ServiceStatusInfo<Object> paramsRsaCheckV1(Map<String,String> params,int type) {
        //TODO 安全性校验
        //TODO 异步处理
        logger.info("==支付宝支付回调信息==");
        logger.info(params.toString());
        logger.info("==支付宝支付回调信息==");
        ServiceStatusInfo<Object> serviceStatusInfo = this.alipayService.paramsRsaCheckV1(params);
        if(serviceStatusInfo.isSuccess()) {
            String outTradeNo = params.get("out_trade_no");
            String tradeNo = params.get("trade_no");
            boolean isSuccess = false;
            if (params.containsKey("trade_status")) {
                isSuccess = params.get("trade_status").equals("TRADE_SUCCESS");
            }
            if (type==1){
                processPayResult(outTradeNo,tradeNo, isSuccess);
            }else if (type==2){
                orderPayResult(outTradeNo,tradeNo, isSuccess,params.toString());
            }

        }
        return serviceStatusInfo;
    }
    public ServiceStatusInfo<Object> paramsRefundRsaCheckV1(Map<String,String> params) {
        //TODO 安全性校验
        //TODO 异步处理
        logger.info("==支付宝退款回调信息==");
        logger.info(params.toString());
        logger.info("==支付宝退款回调信息==");
        ServiceStatusInfo<Object> serviceStatusInfo = this.alipayService.paramsRsaCheckV1(params);
        if(serviceStatusInfo.isSuccess()) {
            String outTradeNo = params.get("out_trade_no");
            String tradeNo = params.get("trade_no");
            boolean isSuccess = false;
            if (params.containsKey("sub_code")) {
                isSuccess = params.get("sub_code").equals("ACQ.TRADE_HAS_SUCCESS");
            }
            this.orderRefundResult(outTradeNo,tradeNo, isSuccess);

        }
        return serviceStatusInfo;
    }

    public ServiceStatusInfo<AliTransferResult> transfer(AliTransferInput input) {
        return alipayService.transfer(input);
    }

    public ServiceStatusInfo<AliTransferQueryResult> transferQuery(AliTransferQueryInput input) {
        return alipayService.transferQuery(input);
    }

    public ServiceStatusInfo<AliAppAuthLoginResult> appAuthLoginSign() {
        return alipayService.appAuthLoginSign();
    }

    @Transactional
    protected void processPayResult(String outTradeNo, String tradeNo,boolean isSuccess) {
        if (!isSuccess) return;
        long id = Long.parseLong(outTradeNo);
        UserCoinDetailModifyInput coinDetailModifyInput = new UserCoinDetailModifyInput();
        coinDetailModifyInput.setId(id);
        coinDetailModifyInput.setType("PAY");
        coinDetailModifyInput.setStatus("SUCCESS");
        coinDetailModifyInput.setTradeNo(tradeNo);
        this.userAssetServiceImpl.updateUserCoinDetail(coinDetailModifyInput);
    }
    @Transactional
    protected void orderPayResult(String outTradeNo, String tradeNo,boolean isSuccess,String params) {
        if (!isSuccess) return;
        long id = Long.parseLong(outTradeNo);
        this.orderService.updateOrderPay(id,"ALIPAY",tradeNo,params);
    }
    @Transactional
    protected void orderRefundResult(String outTradeNo, String tradeNo,boolean isSuccess) {
        long id = Long.parseLong(outTradeNo);
        if (!isSuccess) {
            this.orderService.updateOrderState(id,tradeNo,"STATE_REFUND_FAILED");
        }else {
            //处理内部业务
            this.orderService.updateOrderState(id,tradeNo,"STATE_REFUND_SUCCESS");
        }
    }
}
