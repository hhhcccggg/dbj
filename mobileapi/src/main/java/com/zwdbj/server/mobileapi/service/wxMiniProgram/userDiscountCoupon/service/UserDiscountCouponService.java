package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponModel;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

public interface UserDiscountCouponService {

    /**
     * 新增
     * @param userDiscountCouponModel
     * @return
     */
    ServiceStatusInfo<Long> createUserDiscountCoupon(UserDiscountCouponModel userDiscountCouponModel);

    /**
     * 查询用户拥有券的数量
     * @param userId
     * @param couponId
     * @return
     */
    ServiceStatusInfo<Long> selectUserIdPossessCouponCount(long userId, long couponId);
}
