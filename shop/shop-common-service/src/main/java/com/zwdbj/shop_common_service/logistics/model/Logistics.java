package com.zwdbj.shop_common_service.logistics.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ApiModel
public class Logistics implements Serializable {
    @ApiModelProperty(value = "快递单号")
    String number;
    @ApiModelProperty(value = "快递公司")
    String type;
    @ApiModelProperty(value = "快递流水")
    List list;
    @ApiModelProperty(value = "快递状态")//  1.在途中 2.正在派件 3.已签收 4.派送失败
    String deliverystatus;
    @ApiModelProperty(value = "快递是否签收")//1.是否签收
    String issign;
    @ApiModelProperty(value = "快递公司名称")
    String expName;
    @ApiModelProperty(value = "快递公司网站")
    String expSite;
    @ApiModelProperty(value = "快递公司电话")
    String expPhone;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }


    public String getDeliverystatus() {
        return deliverystatus;
    }

    public void setDeliverystatus(String deliverystatus) {
        this.deliverystatus = deliverystatus;
    }

    public String getIssign() {
        return issign;
    }

    public void setIssign(String issign) {
        this.issign = issign;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpSite() {
        return expSite;
    }

    public void setExpSite(String expSite) {
        this.expSite = expSite;
    }

    public String getExpPhone() {
        return expPhone;
    }

    public void setExpPhone(String expPhone) {
        this.expPhone = expPhone;
    }

    @Override
    public String toString() {
        return "Logistics{" +
                "number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", list=" + list +
                ", deliverystatus='" + deliverystatus + '\'' +
                ", issign='" + issign + '\'' +
                ", expName='" + expName + '\'' +
                ", expSite='" + expSite + '\'' +
                ", expPhone='" + expPhone + '\'' +
                '}';
    }
}
