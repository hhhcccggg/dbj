package com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.service;

import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.DiscountCoupon;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.SearchDiscountCoupon;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface DiscountCouponService {

    /**
     * 用户领取优惠券
     * @param couponId
     * @return
     */
    ServiceStatusInfo<Long> userGetDiscountCoupon(long couponId);

    /**
     * 查询小程序我的优惠券
     * @return
     */
    ServiceStatusInfo<List<DiscountCouponModel>> selectUserDiscountCoupon(SearchDiscountCoupon searchDiscountCoupon);

    /**
     * 查询单个优惠券
     * @param couponId
     * @return
     */
    ServiceStatusInfo<DiscountCouponModel> selectDiscountCoupon(long couponId);

}
