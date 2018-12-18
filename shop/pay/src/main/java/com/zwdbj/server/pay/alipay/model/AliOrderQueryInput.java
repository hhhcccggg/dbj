package com.zwdbj.server.pay.alipay.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 订单查询请求参数
 */
@ApiModel("支付宝订单查询")
public class AliOrderQueryInput implements Serializable {
    /**
     * 商户订单号
     */
    @ApiModelProperty("商户订单号")
    @JSONField(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 支付宝交易号
     */
    @ApiModelProperty("支付宝交易号")
    @JSONField(name = "trade_no")
    private String tradeNo;

    @ApiModelProperty("可选")
    @JSONField(name = "org_pid")
    private String orgPid;

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

    public String getOrgPid() {
        return orgPid;
    }

    public void setOrgPid(String orgPid) {
        this.orgPid = orgPid;
    }
}
