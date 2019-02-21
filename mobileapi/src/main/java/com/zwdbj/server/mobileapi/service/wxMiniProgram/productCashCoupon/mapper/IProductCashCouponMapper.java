package com.zwdbj.server.mobileapi.service.wxMiniProgram.productCashCoupon.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCashCoupon.model.ProductCashCoupon;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IProductCashCouponMapper {

    /**
     * 根据productId查询
     * @param productId
     * @return
     */
    @Select("select * from shop_productCashCoupons where productId=#{productId} and isDeleted=0")
    ProductCashCoupon selectByProductId(@Param("productId") long productId);
}
