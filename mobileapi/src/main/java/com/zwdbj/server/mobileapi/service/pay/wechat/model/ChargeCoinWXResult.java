package com.zwdbj.server.mobileapi.service.pay.wechat.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
@ApiModel("金币充值结果，包括了预付订单等信息")
public class ChargeCoinWXResult implements Serializable {
    @ApiModelProperty("预支付交易会话ID")
    private String prepayId;
    @ApiModelProperty("扩展字段，暂填写固定值Sign=WXPay")
    private String packageN;
    @ApiModelProperty("随机字符串")
    private String nonceStr;
    @ApiModelProperty("时间戳")
    private String timestamp;
    @ApiModelProperty("签名")
    private String sign;
    @ApiModelProperty("订单号")
    private String outTradeNo;

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageN() {
        return packageN;
    }

    public void setPackageN(String packageN) {
        this.packageN = packageN;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
