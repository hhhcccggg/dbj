package com.zwdbj.server.shopservice.productSKU.mapper;

import com.zwdbj.server.shopservice.productSKU.service.ProductSKUs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface IProductSKUs {
    @Insert("insert into shop_productSKUs (id,createTime,isDelete,deleteTime,isManualData,"+
    "skuNumber,productId,originalPrice,promotionPrice,inventory,sales,attrs,weigth)"+
            "values(#{id},#{productSKUs.createTime},#{productSKUs.isDeleted},"+
            "#{productSKUs.deleteTime},#{productSKUs.isManualData},#{productSKUs.skuNumber},"+
            "#{productSKUs.productId},#{productSKUs.originalPrice},#{productSKUs.promotionPrice},"+
            "#{productSKUs.inventory},#{productSKUs.sales},#{productSKUs.sales},"+
            "#{productSKUs.attrs},#{productSKUs.weight})")
    Long createProductSKUs(@Param("id")long id, @Param("productSKUs")ProductSKUs productSKUs);








}
