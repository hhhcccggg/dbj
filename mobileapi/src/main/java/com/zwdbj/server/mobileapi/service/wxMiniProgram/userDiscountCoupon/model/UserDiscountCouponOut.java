package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.common.DiscountType;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.common.UserDiscountCouponState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "用户领取优惠券的model")
public class UserDiscountCouponOut {

    @ApiModelProperty("id")
    private long id;

    @ApiModelProperty("优惠券状态")
    private UserDiscountCouponState state;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "发放的数量")
    private long couponCount;

    @ApiModelProperty(value = "优惠的形式 CASH: 现金优惠 DISCOUNT:折扣")
    private DiscountType discountType;

    @ApiModelProperty(value = "优惠的值")
    private long discountValue;

    @ApiModelProperty(value = "限制条件，满{limitMoney}金额后才可以使用 0:没有限制")
    private long limitMoney;

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

    @ApiModelProperty(value = "店铺ID")
    private long storeId;

    @ApiModelProperty(value = "商家ID")
    private long legalSubjectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserDiscountCouponState getState() {
        return state;
    }

    public void setState(UserDiscountCouponState state) {
        this.state = state;
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