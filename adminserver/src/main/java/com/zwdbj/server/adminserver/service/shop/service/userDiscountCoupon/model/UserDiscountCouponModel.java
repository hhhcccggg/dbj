package com.zwdbj.server.adminserver.service.shop.service.userDiscountCoupon.model;

import com.zwdbj.server.adminserver.service.shop.service.userDiscountCoupon.common.UserDiscountCouponState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户领取的优惠券")
public class UserDiscountCouponModel {

    @ApiModelProperty("id")
    private long id;
    @ApiModelProperty("优惠券ID")
    private long couponId;
    @ApiModelProperty("用户ID")
    private long userId;
    @ApiModelProperty("优惠券状态")
    private UserDiscountCouponState state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UserDiscountCouponState getState() {
        return state;
    }

    public void setState(UserDiscountCouponState state) {
        this.state = state;
    }
}
