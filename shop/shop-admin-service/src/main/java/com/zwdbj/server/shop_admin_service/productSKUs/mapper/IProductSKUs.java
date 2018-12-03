package com.zwdbj.server.shop_admin_service.productSKUs.mapper;

import com.zwdbj.server.shop_admin_service.productSKUs.model.ProductSKUs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


public interface IProductSKUs {
    @Insert("insert into shop_productSKUs (id," +
            "skuNumber,productId,originalPrice,promotionPrice,inventory,sales,attrs,weigth)" +
            "values(#{id},#{productSKUs.skuNumber}," +
            "#{productSKUs.productId},#{productSKUs.originalPrice},#{productSKUs.promotionPrice}," +
            "#{productSKUs.inventory},#{productSKUs.sales},#{productSKUs.sales}," +
            "#{productSKUs.attrs},#{productSKUs.weight})")
    Long createProductSKUs(@Param("id") long id, @Param("productSKUs") ProductSKUs productSKUs);

    @Update("update  shop_productSKUs set isDeleted=#{productSKUs.isDeleted},deleteTime=#{productSKUs.deleteTime} where id=#{productSKUs.id}")
    Long deleteById(@Param("productSKUs")ProductSKUs productSKUs);

    @Update("update shop_productSKUs set skuNumber=#{productSKUs.skuNumber},"+
    "productId=#{productSKUs.productId},originalPrice=#{productSKUs.originalPrice},"+
    "promotionPrice=#{productSKUs.promotionPrice},inventory=#{productSKUs.inventory},"+
    "sales=#{productSKUs.sales},attrs=#{productSKUs.attrs},weight=#{productSKUs.weight}")
    Long updateProductSKUs(@Param("ProductSKUs")ProductSKUs productSKUs);

    @Select("select * from shop_productSKus")
    List<ProductSKUs> selectAll();
}
