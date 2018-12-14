package com.zwdbj.server.pay.alipay.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 转账结果
 */
public class AliTransferResult implements Serializable {
    /**
     * 商户转账唯一订单号
     */
    @ApiModelProperty("商户转账唯一订单号")
    @JSONField(name = "out_biz_no")
    private String outBizNo;
    /**
     * 支付宝转账单据号，成功一定返回，失败可能不返回也可能返回
     */
    @ApiModelProperty("支付宝转账单据号，成功一定返回，失败可能不返回也可能返回")
    @JSONField(name = "order_id")
    private String orderId;
    /**
     * 支付时间：格式为yyyy-MM-dd HH:mm:ss，仅转账成功返回。
     */
    @ApiModelProperty("支付时间：格式为yyyy-MM-dd HH:mm:ss，仅转账成功返回")
    @JSONField(name = "pay_date")
    private String payDate;

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}
