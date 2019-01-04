package com.zwdbj.server.shop_admin_service.service.productCashCoupon.mapper;

import com.zwdbj.server.shop_admin_service.service.productCashCoupon.model.ProductCashCoupon;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IProductCashCouponMapper {

    /**
     * 新增
     * @param id
     * @param productCashCoupon
     * @return
     */
    @Insert("INSERT INTO `dbj_server_shop`.`shop_productcashcoupons` (" +
            "`id`,`couponValue`,`festivalCanUse`,`useInfo`,`validType`,`specHoursValid`,`validDays`,`validStartTime`,`validEndTime`," +
            "`productId`,`productSKUId`)" +
            "VALUES" +
            "(#{id},#{productCashCoupon.couponValue},#{productCashCoupon.festivalCanUse},#{productCashCoupon.useInfo},#{productCashCoupon.validType},#{productCashCoupon.specHoursValid},#{productCashCoupon.validDays},#{productCashCoupon.validStartTime},#{productCashCoupon.validEndTime},#{productCashCoupon.productId},#{productCashCoupon.productSKUId})")
    Long createProductCashCoupon(@Param("id") long id , @Param("productCashCoupon") ProductCashCoupon productCashCoupon);


    /**
     * 修改
     * @param productCashCoupon
     * @return
     */
    Long updateProductCashCoupon(ProductCashCoupon productCashCoupon);

    /**
     * 根据productId查询
     * @param productId
     * @return
     */
    @Select("select * from shop_productcashcoupons where productId=#{productId}")
    ProductCashCoupon selectByProductId(@Param("productId") long productId);
}
