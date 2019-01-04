package com.zwdbj.server.shop_admin_service.service.productSKUs.mapper;

import com.zwdbj.server.shop_admin_service.service.productSKUs.model.ProductSKUs;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IProductSKUsMapper {
    @Insert("insert into shop_productSKUs (id," +
            "skuNumber,productId,originalPrice,promotionPrice,inventory,sales,attrs,weight)" +
            "values(#{id},#{productSKUs.skuNumber}," +
            "#{productSKUs.productId},#{productSKUs.originalPrice},#{productSKUs.promotionPrice}," +
            "#{productSKUs.inventory},#{productSKUs.sales}," +
            "#{productSKUs.attrs},#{productSKUs.weight})")
    Long createProductSKUs(@Param("id") long id, @Param("productSKUs") ProductSKUs productSKUs);

    @Update("update  shop_productSKUs set isDeleted=1,deleteTime=now() where id=#{id} ")
    Long deleteById(@Param("id") Long id);

    @Update("update  shop_productSKUs set isDeleted=1,deleteTime=now() where productId=#{productId} ")
    Long deleteByProductId(@Param("productId") Long productId);

    @Update("update shop_productSKUs set skuNumber=#{productSKUs.skuNumber}," +
            "productId=#{productSKUs.productId},originalPrice=#{productSKUs.originalPrice}," +
            "promotionPrice=#{productSKUs.promotionPrice},inventory=#{productSKUs.inventory}," +
            "sales=#{productSKUs.sales},attrs=#{productSKUs.attrs},weight=#{productSKUs.weight} where id=#{productSKUs.id}")
    Long updateProductSKUs(@Param("productSKUs") ProductSKUs productSKUs);

    @Select("select * from shop_productskus where isDeleted=0 order by createTime")
    List<ProductSKUs> selectAll();

    @Select("select * from shop_productskus where id=#{id}")
    ProductSKUs selectById(@Param("id")Long id);

    @Select("select * from shop_productskus where productId=#{productId}")
    ProductSKUs selectByProductId(@Param("productId")Long productId);
}
