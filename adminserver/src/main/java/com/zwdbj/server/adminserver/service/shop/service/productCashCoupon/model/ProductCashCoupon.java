package com.zwdbj.server.adminserver.service.shop.service.productCashCoupon.model;

import com.zwdbj.server.adminserver.service.shop.service.products.common.ValidType;
import com.zwdbj.server.adminserver.service.shop.service.products.model.CreateProducts;
import com.zwdbj.server.adminserver.service.shop.service.products.model.UpdateProducts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "代金券扩展信息")
public class ProductCashCoupon {

    @ApiModelProperty(value = "id")
    private Long id;

    private boolean isDeleted;

    @ApiModelProperty(value = "代金券面值,用于抵扣金额的值")
    private int couponValue;

    @ApiModelProperty(value = "节假日是否可用")
    private boolean festivalCanUse;

    @ApiModelProperty(value = "使用说明")
    private String useInfo;

    @ApiModelProperty(value = "生效类型")
    private ValidType validType;

    @ApiModelProperty(value = "PAY_SPEC_HOUR_VALIDED:付款后指定小时生效")
    private int specHoursValid;

    @ApiModelProperty(value = "生效后多少天内有效")
    private int validDays;

    @ApiModelProperty(value = "生效后指定时间范围内生效")
    private Date validStartTime;

    @ApiModelProperty(value = "生效后指定时间范围内生效")
    private Date validEndTime;

    private Long productId;

    @ApiModelProperty(value = "存在规格有特殊定制?")
    private long productSKUId;

    @ApiModelProperty(value = "预约信息")
    private String appointment;

    @ApiModelProperty(value = "是否与其他优惠券共用")
    private boolean stackUse;

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public boolean isStackUse() {
        return stackUse;
    }

    public void setStackUse(boolean stackUse) {
        this.stackUse = stackUse;
    }

    public ProductCashCoupon() {
    }

    public ProductCashCoupon(CreateProducts createProducts, Long productId) {
        this.festivalCanUse = createProducts.isFestivalCanUse();
        this.validType = createProducts.getValidType();
        this.specHoursValid = createProducts.getSpecHoursValid();
        this.validDays = createProducts.getValidDays();
        this.validStartTime = createProducts.getValidStartTime();
        this.validEndTime = createProducts.getValidEndTime();
        this.appointment = createProducts.getAppointment();
        this.stackUse = createProducts.isStackUse();
        this.productId = productId;
    }
    public ProductCashCoupon(UpdateProducts updateProducts, Long productId) {
        this.festivalCanUse = updateProducts.isFestivalCanUse();
        this.validType = updateProducts.getValidType();
        this.specHoursValid = updateProducts.getSpecHoursValid();
        this.validDays = updateProducts.getValidDays();
        this.validStartTime = updateProducts.getValidStartTime();
        this.validEndTime = updateProducts.getValidEndTime();
        this.appointment = updateProducts.getAppointment();
        this.stackUse = updateProducts.isStackUse();
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

    public ValidType getValidType() {
        return validType;
    }

    public void setValidType(ValidType validType) {
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
