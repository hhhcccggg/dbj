package com.zwdbj.server.shop_admin_service.productSKUs.mapper;

import com.zwdbj.server.shop_admin_service.productSKUs.service.ProductSKUs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;



public interface IProductSKUs {
    @Insert("insert into shop_productSKUs (id," +
            "skuNumber,productId,originalPrice,promotionPrice,inventory,sales,attrs,weigth)" +
            "values(#{id},#{productSKUs.skuNumber}," +
            "#{productSKUs.productId},#{productSKUs.originalPrice},#{productSKUs.promotionPrice}," +
            "#{productSKUs.inventory},#{productSKUs.sales},#{productSKUs.sales}," +
            "#{productSKUs.attrs},#{productSKUs.weight})")
    Long createProductSKUs(@Param("id") long id, @Param("productSKUs") ProductSKUs productSKUs);


}
