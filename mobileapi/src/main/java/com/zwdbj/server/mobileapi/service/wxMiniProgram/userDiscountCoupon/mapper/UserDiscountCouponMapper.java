package com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.common.UserDiscountCouponState;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.SearchUserDiscountCoupon;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.model.UserDiscountCouponOut;
import com.zwdbj.server.pay.settlement.protocol.Coupon;
import org.apache.ibatis.annotations.*;

import java.util.List;

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

    /**
     * 查询用户优惠券
     * @param searchUserDiscountCoupon
     * @return
     */
    @SelectProvider(type = UserDiscountCouponSqlProvider.class , method = "selectUserDiscountCoupon")
    List<UserDiscountCouponOut> selectUserDiscountCoupon(@Param("searchUserDiscountCoupon") SearchUserDiscountCoupon searchUserDiscountCoupon);

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

    @Select("SELECT " +
            "dc.id, " +
            "dc.`name`, " +
            "dc.discountType, " +
            "dc.limitMoney, " +
            "dc.discountValue " +
            "FROM " +
            "shop_discountCoupons AS dc , " +
            "shop_userDiscountCoupons AS udc " +
            "WHERE " +
            "dc.storeId=#{storeId} AND " +
            "dc.legalSubjectId = #{legalSubjectId} AND " +
            "dc.isDeleted = 0 AND " +
            "(dc.validStartTime is null or dc.validStartTime<now()) AND " +
            "(dc.validEndTime is null or dc.validEndTime>now()) AND " +
            "(dc.limitMoney=0 or dc.limitMoney <= #{price}) AND " +
            "udc.userId=#{userId} AND " +
            "udc.couponId = dc.id AND " +
            "udc.isDeleted = 0 AND " +
            "udc.state = 'UNUSED'")
    List<Coupon> getVaildCoupon(@Param("storeId")long storeId, @Param("legalSubjectId")long legalSubjectId, @Param("price")long price,@Param("userId")long userId);

    @Select("SELECT " +
            "dc.id, " +
            "dc.`name`, " +
            "dc.discountType, " +
            "dc.limitMoney, " +
            "dc.discountValue " +
            "FROM " +
            "shop_discountCoupons AS dc , " +
            "shop_userDiscountCoupons AS udc " +
            "WHERE " +
            "dc.storeId=#{storeId} AND " +
            "dc.legalSubjectId = #{legalSubjectId} AND " +
            "dc.isDeleted = 0 AND " +
            "(dc.validStartTime is null or dc.validStartTime<now()) AND " +
            "(dc.validEndTime is null or dc.validEndTime>now()) AND " +
            "(dc.limitMoney=0 or dc.limitMoney <= #{price}) AND " +
            "udc.userId=#{userId} AND " +
            "udc.couponId = dc.id AND udc.couponId=#{id} AND " +
            "udc.isDeleted = 0 AND " +
            "udc.state = 'UNUSED'")
    Coupon getVaildCouponById(@Param("storeId") long storeId, @Param("legalSubjectId")long legalSubjectId, @Param("price")long price,@Param("userId")long userId, @Param("id")long id);

    /**
     * 查询没有使用的该优惠券数量
     * @param userId
     * @param couponId
     * @return
     */
    @Select("SELECT count(0) " +
            "FROM " +
            "shop_userDiscountCoupons , " +
            "shop_discountCoupons " +
            "WHERE " +
            "shop_userDiscountCoupons.couponId = shop_discountCoupons.id AND " +
            "shop_discountCoupons.id=#{couponId} AND " +
            "shop_userDiscountCoupons.userId=#{userId} AND " +
            "shop_userDiscountCoupons.state='UNUSED' AND " +
            "shop_userDiscountCoupons.isDeleted=0 AND " +
            "shop_discountCoupons.isDeleted=0")
    long getUserCouponCount(@Param("userId") long userId,@Param("couponId") long couponId);

    /**
     * 使用优惠券
     * @param userId
     * @param couponId
     * @return
     */
    @Update("UPDATE `shop_userDiscountCoupons` " +
            "SET  " +
            " `state` = 'USED' " +
            "WHERE " +
            "`couponId` = #{couponId}  and `userId` =#{userId} and state='UNUSED' and isDeleted=0  limit 1 ")
    long useUserCouponCount(@Param("userId") long userId,@Param("couponId") long couponId);
}
