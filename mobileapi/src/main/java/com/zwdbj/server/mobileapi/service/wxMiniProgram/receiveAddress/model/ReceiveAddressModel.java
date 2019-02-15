package com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "收货人的地址相关")
public class ReceiveAddressModel {
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "收货人的id")
    private long userId;
    @ApiModelProperty(value = "收货人的名字")
    private String receiverName;
    @ApiModelProperty(value = "收货人固定电话")
    private String receiverPhone;
    @ApiModelProperty(value = "收货人手机号码")
    private String receiverMobile;
    @ApiModelProperty(value = "收货人地址的省份")
    private String reveiverState;
    @ApiModelProperty(value = "收货人地址的城市")
    private String receiverCity;
    @ApiModelProperty(value = "收货人地址的区县")
    private String receiverCountry;
    @ApiModelProperty(value = "详细地址")
    private String receiverAddress;
    @ApiModelProperty(value = "")
    private String receiverZip;
    @ApiModelProperty(value = "城市的编号")
    private long cityId;
    @ApiModelProperty(value = "城市的等级")
    private String cityLevel;
    @ApiModelProperty(value = "更新的时间")
    private Date updateTime;
    @ApiModelProperty(value = "是否为默认地址")
    private boolean defaultAddr;
    @ApiModelProperty(value = "收货人地址的街,道")
    private String receiverStreet;
    @ApiModelProperty(value = "省市区详细地址")
    private String detailedly;

    public String getReceiverStreet() {
        return receiverStreet;
    }

    public void setReceiverStreet(String receiverStreet) {
        this.receiverStreet = receiverStreet;
    }

    public String getDetailedly() {
        return reveiverState+receiverCity+receiverCountry+detailedly+receiverAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReveiverState() {
        return reveiverState;
    }

    public void setReveiverState(String reveiverState) {
        this.reveiverState = reveiverState;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverCountry() {
        return receiverCountry;
    }

    public void setReceiverCountry(String receiverCountry) {
        this.receiverCountry = receiverCountry;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(String cityLevel) {
        this.cityLevel = cityLevel;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(boolean defaultAddr) {
        this.defaultAddr = defaultAddr;
    }
}
