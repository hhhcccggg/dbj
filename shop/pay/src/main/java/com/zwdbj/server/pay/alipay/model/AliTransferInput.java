package com.zwdbj.server.pay.alipay.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 支付宝账号之间转账参数
 */
public class AliTransferInput implements Serializable {
    /**
     * 商户转账唯一订单号
     */
    @ApiModelProperty("商户转账唯一订单号")
    @JSONField(name = "out_biz_no")
    private String outBizNo;
    /**
     * 收款方账户类型。可取值：
     * 1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。
     * 2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。
     */
    @ApiModelProperty("收款方账户类型。可取值： \n" +
            "1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。 \n" +
            "2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。")
    @JSONField(name = "payee_type")
    private String payeeType;
    /**
     * 收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。
     */
    @ApiModelProperty("收款方账户。与payee_type配合使用。付款方和收款方不能是同一个账户。")
    @JSONField(name = "payee_account")
    private String payeeAccount;
    /**
     * 转账金额，单位：元。
     * 只支持2位小数，小数点前最大支持13位，金额必须大于等于0.1元。
     * 最大转账金额以实际签约的限额为准。
     */
    @ApiModelProperty("转账金额，单位：元。 \n" +
            "只支持2位小数，小数点前最大支持13位，金额必须大于等于0.1元。 \n" +
            "最大转账金额以实际签约的限额为准。")
    @JSONField(name = "amount")
    private String amount;
    /**
     * 转账备注
     */
    @ApiModelProperty("转账备注")
    @JSONField(name = "remark")
    private String remark;

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getPayeeType() {
        return payeeType;
    }

    public void setPayeeType(String payeeType) {
        this.payeeType = payeeType;
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
