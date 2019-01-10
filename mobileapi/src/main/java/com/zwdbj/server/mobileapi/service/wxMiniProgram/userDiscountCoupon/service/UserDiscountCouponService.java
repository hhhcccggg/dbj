package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.SearchUserDiscountCoupon;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponOut;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface UserDiscountCouponService {

    /**
     * 查询用户拥有券的数量
     * @param userId
     * @param couponId
     * @return
     */
    ServiceStatusInfo<Long> selectUserIdPossessCouponCount(long userId, long couponId);

    /**
     * 查询用户的优惠券数量
     * @param searchUserDiscountCoupon
     * @return
     */
    ServiceStatusInfo<List<UserDiscountCouponOut>> selectUserDiscountCoupon(SearchUserDiscountCoupon searchUserDiscountCoupon);

    /**
     * 批量新增
     * @param userId
     * @param couponId
     * @param couponCount
     * @return
     */
    ServiceStatusInfo<Long> batchCreateUserDiscountCoupon(long userId , long couponId,int couponCount);

}
