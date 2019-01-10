package com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model.DiscountCouponModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IDiscountCouponMapper {

    /**
     * 查询优惠券是否存在
     * @param couponId
     * @return
     */
    @Select("select * from `shop_discountcoupons` where id=#{couponId}")
    DiscountCouponModel selectByCoupon(@Param("couponId") long couponId);

    /**
     * 减少数量
     * @param couponCount
     * @return
     */
    @Update("UPDATE `shop_discountcoupons` SET  `couponCount` = `couponCount` - #{couponCount} where id = #{id} " +
            "and isDeleted=0  and `couponCount`-#{couponCount}>=0")
    long reduceCouponCount(@Param("id") long id , @Param("couponCount") int couponCount);

}
