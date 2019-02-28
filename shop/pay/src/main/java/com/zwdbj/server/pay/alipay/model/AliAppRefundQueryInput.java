package com.zwdbj.server.pay.alipay.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("阿里支付查询退款的请求参数")
public class AliAppRefundQueryInput implements Serializable {

    private static final long serialVersionUID = 8957016375684062824L;
    @ApiModelProperty(value = "订单支付时传入的商户订单号,不能和 trade_no同时为空。rade_no,out_trade_no如果同时存在优先取trade_no")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    @ApiModelProperty(value = "支付宝交易号，和商户订单号不能同时为空。srade_no,out_trade_no如果同时存在优先取trade_no")
    @JSONField(name = "trade_no")
    private String tradeNo;
    @ApiModelProperty(value = "请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号")
    @JSONField(name = "out_request_no")
    private String outRequestNo;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutRequestNo() {
        return outRequestNo;
    }

    public void setOutRequestNo(String outRequestNo) {
        this.outRequestNo = outRequestNo;
    }
}
