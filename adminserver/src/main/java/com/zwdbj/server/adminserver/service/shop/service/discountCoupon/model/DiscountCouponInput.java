package com.zwdbj.server.adminserver.service.shop.service.discountCoupon.model;

import com.zwdbj.server.adminserver.service.shop.service.discountCoupon.common.DiscountType;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class DiscountCouponInput {
    @ApiModelProperty(value = "id")
    private long id;

    @NotNull
    @ApiModelProperty(value = "名字")
    private String name;

    @Min(0)
    @ApiModelProperty(value = "发放的数量")
    private long couponCount;

    @NotNull
    @ApiModelProperty(value = "优惠的形式 CASH: 现金优惠 DISCOUNT:折扣")
    private DiscountType discountType;

    @Min(1)
    @ApiModelProperty(value = "优惠的值")
    private long discountValue;

    @Min(0)
    @ApiModelProperty(value = "限制条件，满{limitMoney}金额后才可以使用 0:没有限制")
    private long limitMoney;

    @Min(0)
    @ApiModelProperty(value = "每一个人限领数量 0:没有限制")
    private long limitGetPerPerson;

    @ApiModelProperty(value = "使用说明")
    private String useInfo;

    @ApiModelProperty(value = "仅支持原价购买的商品才能使用")
    private boolean onlySupportOriginProduct;

    @ApiModelProperty(value = "生效时间段")
    private Date validStartTime;

    @ApiModelProperty(value = "生效时间段")
    private Date validEndTime;

    @ApiModelProperty(value = "店铺ID" , hidden = true)
    private long storeId;

    @ApiModelProperty(value = "商家ID", hidden = true)
    private long legalSubjectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(long couponCount) {
        this.couponCount = couponCount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public long getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(long discountValue) {
        this.discountValue = discountValue;
    }

    public long getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(long limitMoney) {
        this.limitMoney = limitMoney;
    }

    public long getLimitGetPerPerson() {
        return limitGetPerPerson;
    }

    public void setLimitGetPerPerson(long limitGetPerPerson) {
        this.limitGetPerPerson = limitGetPerPerson;
    }

    public String getUseInfo() {
        return useInfo;
    }

    public void setUseInfo(String useInfo) {
        this.useInfo = useInfo;
    }

    public boolean isOnlySupportOriginProduct() {
        return onlySupportOriginProduct;
    }

    public void setOnlySupportOriginProduct(boolean onlySupportOriginProduct) {
        this.onlySupportOriginProduct = onlySupportOriginProduct;
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

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }

}
