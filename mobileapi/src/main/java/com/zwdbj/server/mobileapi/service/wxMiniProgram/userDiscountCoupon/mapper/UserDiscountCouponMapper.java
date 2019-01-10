package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDiscountCouponMapper {

    @Insert("INSERT INTO `shop_userdiscountcoupons` (" +
            "`id`,`couponId`,`userId`,`state`" +
            ")VALUES(" +
            "#{userDiscountCouponModel.id},#{userDiscountCouponModel.couponId}," +
            "#{userDiscountCouponModel.userId},#{userDiscountCouponModel.state});")
    long createUserDiscountCoupon(@Param("userDiscountCouponModel") UserDiscountCouponModel userDiscountCouponModel);

    /**
     * 查询用户拥有该券的数量
     * @param userId
     * @param couponId
     * @return
     */
    @Select("select count(1) from shop_userdiscountcoupons where isDeleted=0 and userId = #{userId} and couponId=#{couponId}")
    long selectUserIdPossessCouponCount(@Param("userId") long userId, @Param("couponId") long couponId);
}
