package com.zwdbj.server.shop_admin_service.service.productCashCoupon.mapper;

import com.zwdbj.server.shop_admin_service.service.productCashCoupon.model.ProductCashCoupon;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IProductCashCouponMapper {

    /**
     * 新增
     * @param id
     * @param productCashCoupon
     * @return
     */
    @Insert("INSERT INTO `shop_productcashcoupons` (" +
            "`id`,`couponValue`,`festivalCanUse`,`useInfo`,`validType`,`specHoursValid`,`validDays`,`validStartTime`,`validEndTime`," +
            "`productId`,`productSKUId`)" +
            "VALUES" +
            "(#{id},#{productCashCoupon.couponValue},#{productCashCoupon.festivalCanUse},#{productCashCoupon.useInfo},#{productCashCoupon.validType},#{productCashCoupon.specHoursValid},#{productCashCoupon.validDays},#{productCashCoupon.validStartTime},#{productCashCoupon.validEndTime},#{productCashCoupon.productId},#{productCashCoupon.productSKUId})")
    Long createProductCashCoupon(@Param("id") long id , @Param("productCashCoupon") ProductCashCoupon productCashCoupon);


    /**
     * 根据productId修改
     * @param productCashCoupon
     * @return
     */
    @Update("UPDATE `shop_productcashcoupons`" +
            "SET " +
            " `couponValue` = #{productCashCoupon.couponValue}," +
            " `festivalCanUse` =  #{productCashCoupon.festivalCanUse}," +
            " `useInfo` =  #{productCashCoupon.useInfo}," +
            " `validType` =  #{productCashCoupon.validType}," +
            " `specHoursValid` =  #{productCashCoupon.specHoursValid}," +
            " `validDays` =  #{productCashCoupon.validDays}," +
            " `validStartTime` =  #{productCashCoupon.validStartTime}," +
            " `validEndTime` =  #{productCashCoupon.validEndTime}," +
            "WHERE" +
            "(`productId` = #{productCashCoupon.productId});")
    Long updateByProductIdByProductCashCoupon(@Param("productCashCoupon") ProductCashCoupon productCashCoupon);

    /**
     * 根据productId查询
     * @param productId
     * @return
     */
    @Select("select * from shop_productcashcoupons where productId=#{productId}")
    ProductCashCoupon selectByProductId(@Param("productId") long productId);
}
