package com.zwdbj.server.adminserver.service.shop.service.userDiscountCoupon.mapper;

import com.zwdbj.server.adminserver.service.shop.service.userDiscountCoupon.model.UserDiscountCouponModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDiscountCouponMapper {

    @Insert("INSERT INTO `shop_userdiscountcoupons` (" +
            "`id`,`couponId`,`userId`,`state`" +
            ")VALUES(" +
            "#{userDiscountCouponModel.id},#{userDiscountCouponModel.couponId}," +
            "#{userDiscountCouponModel.userId},#{userDiscountCouponModel.state});")
    long createUserDiscountCoupon(@Param("userDiscountCouponModel") UserDiscountCouponModel userDiscountCouponModel);

    /**
     * 批量新增
     * @param userDiscountCouponModels
     * @return
     */
    @Insert("<script>insert into shop_userdiscountcoupons(`id`,`couponId`,`userId`,`state`) values" +
            "<foreach item='userDiscountCoupon' index='index' collection='userDiscountCouponModels' open='(' separator=',' close=')'>" +
            "#{userDiscountCoupon.id},#{userDiscountCoupon.couponId},#{userDiscountCoupon.userId},#{userDiscountCoupon.state}"+
            "</foreach></script>")
    long batchCreateUserDiscountCoupon(@Param("userDiscountCouponModels") UserDiscountCouponModel[] userDiscountCouponModels);

    /**
     * 查询用户拥有该券的数量
     * @param userId
     * @param couponId
     * @return
     */
    @Select("select count(1) from shop_userdiscountcoupons where isDeleted=0 and userId = #{userId} and couponId=#{couponId}")
    long selectUserIdPossessCouponCount(@Param("userId")long userId,@Param("couponId") long couponId);
}
