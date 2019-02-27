package com.zwdbj.server.adminserver.service.shop.service.userDiscountCoupon.service;

import com.zwdbj.server.adminserver.service.shop.service.userDiscountCoupon.model.UserDiscountCouponModel;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

public interface UserDiscountCouponService {

    /**
     * 新增
     * @param userDiscountCouponModel
     * @return
     */
    ServiceStatusInfo<Long> createUserDiscountCoupon(UserDiscountCouponModel userDiscountCouponModel);

    /**
     * 批量新增
     * @param userId
     * @param couponId
     * @param couponCount
     * @return
     */
    ServiceStatusInfo<Long> batchCreateUserDiscountCoupon(long userId , long couponId,int couponCount);

    /**
     * 查询用户拥有券的数量
     * @param userId
     * @param couponId
     * @return
     */
    ServiceStatusInfo<Long> selectUserIdPossessCouponCount(long userId , long couponId);
}
