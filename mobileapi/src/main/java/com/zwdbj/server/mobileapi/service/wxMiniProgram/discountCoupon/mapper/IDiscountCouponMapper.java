package com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.DiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.SearchDiscountCoupon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IDiscountCouponMapper {

    /**
     * 查询优惠券是否存在
     * @param couponId
     * @return
     */
    DiscountCouponModel selectByCoupon(long couponId);

    /**
     * 查询用户优惠券
     * @param searchDiscountCoupon
     * @return
     */
    List<DiscountCouponModel> selectUserDiscountCoupon(SearchDiscountCoupon searchDiscountCoupon);

    long createUserDiscountCoupon();

}
