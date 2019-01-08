package com.zwdbj.server.adminserver.service.shop.service.productOrder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "后台订单查询的输入字段")
public class ProductOrderInput {
    @ApiModelProperty(value = "输入的查询关键字， 订单号/联系人姓名/联系人手机号")
    String keyWords;
    @ApiModelProperty(value = "输入的查询起始时间")
    Date startTime;
    @ApiModelProperty(value = "输入的查询结束时间")
    Date endTime;
    @ApiModelProperty(value = "订单的状态 " +
            "STATE_WAIT_BUYER_PAY（交易创建，等待买家付款）" +
            "STATE_BUYER_PAYED (买家已付款) " +
            "STATE_SELLER_DELIVERIED（卖家已发货，等待买家收货) " +
            "STATE_BUYER_DELIVERIED（买家已确认收货) STATE_REFUNDING（退款中)" +
            "STATE_REFUND_SUCCESS（退款成功)" +
            "STATE_UNUSED (未使用，虚拟商品有效)" +
            "STATE_USED (已使用，虚拟商品有效)" +
            "STATE_SUCCESS（交易成功)" +
            "STATE_CLOSED(交易关闭)")
    String status;
    @ApiModelProperty(value = "支付类型，BANK:银行卡 WECHAT:微信 ALIPAY:支付宝 NONE:小程序兑换")
    String paymentType;

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
