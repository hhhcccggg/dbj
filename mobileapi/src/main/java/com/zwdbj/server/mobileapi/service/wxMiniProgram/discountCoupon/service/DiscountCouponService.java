package com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;


public interface DiscountCouponService {

    /**
     * 用户领取优惠券
     * @param couponId
     * @return
     */
    ServiceStatusInfo<Long> userGetDiscountCoupon(long couponId);

    /**
     * 查询单个优惠券
     * @param couponId
     * @return
     */
    ServiceStatusInfo<DiscountCouponModel> selectDiscountCoupon(long couponId);

}
