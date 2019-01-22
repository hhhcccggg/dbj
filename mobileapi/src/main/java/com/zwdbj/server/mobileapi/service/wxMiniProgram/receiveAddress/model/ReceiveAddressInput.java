package com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(description = "收货地址的字段")
public class ReceiveAddressInput {

    @ApiModelProperty(value = "id")
    private long id;

    @ApiModelProperty(value = "收货人的id",hidden = true)
    private long userId;

    @NotNull
    @ApiModelProperty(value = "收货人的名字")
    private String receiverName;

    @ApiModelProperty(value = "收货人固定电话")
    private String receiverPhone;

    @NotNull
    @ApiModelProperty(value = "收货人手机号码")
    private String receiverMobile;

    @NotNull
    @ApiModelProperty(value = "收货人地址的省份")
    private String reveiverState;

    @NotNull
    @ApiModelProperty(value = "收货人地址的城市")
    private String receiverCity;

    @NotNull
    @ApiModelProperty(value = "收货人地址的区县")
    private String receiverCountry;

    @NotNull
    @ApiModelProperty(value = "详细地址")
    private String receiverAddress;

    @ApiModelProperty(value = "")
    private String receiverZip;

    @ApiModelProperty(value = "城市的编号")
    private long cityId;

    @ApiModelProperty(value = "城市的等级")
    private String cityLevel;

    @ApiModelProperty(value = "是否为默认地址")
    private boolean defaultAddr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(boolean defaultAddr) {
        this.defaultAddr = defaultAddr;
    }
}
