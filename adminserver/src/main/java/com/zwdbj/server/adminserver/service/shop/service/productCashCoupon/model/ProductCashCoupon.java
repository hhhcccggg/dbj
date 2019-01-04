package com.zwdbj.server.adminserver.service.shop.service.productCashCoupon.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "代金券扩展信息")
public class ProductCashCoupon {

    @ApiModelProperty(value = "id")
    Long id;

    boolean isDeleted;

    @ApiModelProperty(value = "代金券面值,用于抵扣金额的值")
    int couponValue;

    @ApiModelProperty(value = "节假日是否可用")
    boolean festivalCanUse;

    @ApiModelProperty(value = "使用说明")
    String useInfo;

    @ApiModelProperty(value = "生效类型")
    String validType;

    @ApiModelProperty(value = "PAY_SPEC_HOUR_VALIDED:付款后指定小时生效")
    int specHoursValid;

    @ApiModelProperty(value = "生效后多少天内有效")
    int validDays;

    @ApiModelProperty(value = "生效后指定时间范围内生效")
    Date validStartTime;

    @ApiModelProperty(value = "生效后指定时间范围内生效")
    Date validEndTime;

    Long productId;

    @ApiModelProperty(value = "存在规格有特殊定制?")
    long productSKUId;

    public ProductCashCoupon() {
    }

    public ProductCashCoupon(boolean festivalCanUse, String validType, int specHoursValid, int validDays, Date validStartTime, Date validEndTime, Long productId) {
        this.festivalCanUse = festivalCanUse;
        this.validType = validType;
        this.specHoursValid = specHoursValid;
        this.validDays = validDays;
        this.validStartTime = validStartTime;
        this.validEndTime = validEndTime;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
    }

    public boolean isFestivalCanUse() {
        return festivalCanUse;
    }

    public void setFestivalCanUse(boolean festivalCanUse) {
        this.festivalCanUse = festivalCanUse;
    }

    public String getUseInfo() {
        return useInfo;
    }

    public void setUseInfo(String useInfo) {
        this.useInfo = useInfo;
    }

    public String getValidType() {
        return validType;
    }

    public void setValidType(String validType) {
        this.validType = validType;
    }

    public int getSpecHoursValid() {
        return specHoursValid;
    }

    public void setSpecHoursValid(int specHoursValid) {
        this.specHoursValid = specHoursValid;
    }

    public int getValidDays() {
        return validDays;
    }

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }

    public Date getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(Date validStartTime) {
        this.validStartTime = validStartTime;
    }

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getProductSKUId() {
        return productSKUId;
    }

    public void setProductSKUId(long productSKUId) {
        this.productSKUId = productSKUId;
    }
}
