package com.zwdbj.server.mobileapi.service.pay.Refund.service;

import com.zwdbj.server.mobileapi.service.pay.Refund.model.PayRefundInput;
import com.zwdbj.server.mobileapi.service.pay.alipay.service.AlipayBizService;
import com.zwdbj.server.mobileapi.service.pay.wechat.service.WXPayService;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderDetailModel;
import com.zwdbj.server.mobileapi.service.shop.order.service.OrderService;
import com.zwdbj.server.pay.alipay.model.AliAppRefundDto;
import com.zwdbj.server.pay.alipay.model.AliAppRefundQueryDto;
import com.zwdbj.server.pay.alipay.model.AliAppRefundQueryInput;
import com.zwdbj.server.pay.wechat.wechatpay.model.RefundOrderDto;
import com.zwdbj.server.pay.wechat.wechatpay.model.RefundQueryInput;
import com.zwdbj.server.pay.wechat.wechatpay.model.RefundQueryResultDto;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayRefundService {
    @Autowired
    private AlipayBizService alipayBizService;
    @Autowired
    private WXPayService weChatService;
    @Autowired
    private OrderService orderService;
    public ServiceStatusInfo<Object> refundOrder(PayRefundInput input){
        ProductOrderDetailModel model = this.orderService.getOrderById(input.getOrderId()).getData();
        if (model==null)return new ServiceStatusInfo<>(1,"没有此订单",null);
        long userId = JWTUtil.getCurrentId();
        if (userId<=0)return new ServiceStatusInfo<>(1,"请重新登录",null);
        if (userId!=model.getUserId())return new ServiceStatusInfo<>(1,"请重新登录",null);
        int refundAmount = model.getActualPayment();
        String tradeNo = model.getThirdPaymentTradeNo();
        if (tradeNo==null || tradeNo.length()==0)new ServiceStatusInfo<>(1,"此订单没有支付",null);
        if (model.getPaymentType().equals("WECHAT") && (model.getStatus().equals("STATE_UNUSED") || model.getStatus().equals("STATE_REFUND_FAILED"))){
            RefundOrderDto dto = this.weChatService.refundOrder(input,refundAmount,tradeNo).getData();
            return new ServiceStatusInfo<>(0,"微信退款成功", dto);
        }else if (model.getPaymentType().equals("ALIPAY") && (model.getStatus().equals("STATE_UNUSED") || model.getStatus().equals("STATE_REFUND_FAILED"))){
            AliAppRefundDto dto = this.alipayBizService.refundOrder(input,refundAmount,tradeNo).getData();
            return new ServiceStatusInfo<>(0,"支付宝退款成功", dto);
        }else {
            return new ServiceStatusInfo<>(1,"此订单没有支付",null);
        }

    }

    public ServiceStatusInfo<Object> refundOrderQuery(long orderId){
        ProductOrderDetailModel model = this.orderService.getOrderById(orderId).getData();
        if (model==null)return new ServiceStatusInfo<>(1,"没有此订单",null);
        long userId = JWTUtil.getCurrentId();
        if (userId<=0)return new ServiceStatusInfo<>(1,"请重新登录",null);
        if (model.getPaymentType().equals("WECHAT")){
            RefundQueryInput input = new RefundQueryInput();
            input.setTransactionId(model.getThirdPaymentTradeNo());
            input.setType("WECHAT");
            RefundQueryResultDto dto = this.weChatService.refundOrderQuery(input).getData();
            new ServiceStatusInfo<>(0,"查询微信退款成功", dto);
        }else if (model.getPaymentType().equals("ALIPAY")){
            AliAppRefundQueryInput input = new AliAppRefundQueryInput();
            input.setOutTradeNo(String.valueOf(orderId));
            input.setTradeNo(model.getThirdPaymentTradeNo());
            input.setOutRequestNo(String.valueOf(orderId));
            AliAppRefundQueryDto dto = this.alipayBizService.orderRefundQuery(input).getData();
            new ServiceStatusInfo<>(0,"查询支付宝退款成功", dto);
        }else {
            return new ServiceStatusInfo<>(1,"此订单没有支付",null);
        }
        return null;
    }


}
