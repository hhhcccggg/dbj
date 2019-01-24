package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.SearchUserDiscountCoupon;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponOut;
import com.zwdbj.server.pay.settlement.protocol.Coupon;
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

    /**
     * 根据商铺店铺订单价格,查询可用优惠券
     * @param storeId
     * @param legalSubjectId
     * @param price
     * @return
     */
    ServiceStatusInfo<List<Coupon>> getVaildCoupon(long storeId, long legalSubjectId, long price);

    /**
     * 根据商铺店铺订单价格优惠券id是否可用
     * @param storeId
     * @param legalSubjectId
     * @param price
     * @return
     */
    ServiceStatusInfo<Coupon> getVaildCouponById(long storeId, long legalSubjectId, long price,long id);

    /**
     * 使用优惠券
     * @param userId
     * @param id
     * @return
     */
    ServiceStatusInfo<Long> updateUserDiscountCouponState(long userId,long id);
}
