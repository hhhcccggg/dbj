package com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.model.ProductSKUs;
import org.apache.ibatis.annotations.*;


@Mapper
public interface IProductSKUsMapper {

    @Select("select * from shop_productSKUs where id=#{id} and isDeleted=0 ")
    ProductSKUs selectById(@Param("id") Long id);

    @Select("select * from shop_productSKUs where productId=#{productId} and isDeleted=0 limit 1")
    ProductSKUs selectByProductId(@Param("productId") Long productId);
}
