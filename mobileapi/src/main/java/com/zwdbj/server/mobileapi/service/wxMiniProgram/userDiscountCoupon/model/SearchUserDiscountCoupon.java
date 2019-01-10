package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.common.DiscountType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@ApiModel(description = "查询优惠券")
public class SearchUserDiscountCoupon {

    @ApiModelProperty(value = "优惠的形式 CASH: 现金优惠 DISCOUNT:折扣")
    private DiscountType discountType;

    @Min(value = -1)
    @Max(value = 2)
    @ApiModelProperty("优惠券状态 -1全部 0未使用 1已使用 2已过期")
    private int state;

    @ApiModelProperty(value = "店铺ID" , hidden = true)
    private long storeId;

    @ApiModelProperty(value = "商家ID" , hidden = true)
    private long legalSubjectId;

    @ApiModelProperty(value = "用户ID" , hidden = true)
    private long userId;

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}