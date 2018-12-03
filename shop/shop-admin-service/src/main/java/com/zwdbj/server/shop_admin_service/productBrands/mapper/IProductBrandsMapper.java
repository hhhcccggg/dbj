package com.zwdbj.server.shop_admin_service.productBrands.mapper;


import com.zwdbj.server.shop_admin_service.productBrands.model.ProductBrands;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IProductBrandsMapper {
    @Insert("insert into shop_productBrands (id,name,imageUrl,description,orderIndex)" +
            "values(#{id},#{productBrands.name},#{productBrands.imageUrl}," +
            "#{productBrands.description},#{productBrands.orderIndex})")
    Long createProductBrands(@Param("id") Long id, @Param("productBrands") ProductBrands productBrands);

    @Update("upate shop_productBrands set name=#{productBrands.name},"+
            "imageUrl=#{productBrands},description=#{productBrands.description},”" +
            "orderIndex=#{productBrands.orderIndex} where id=#{productBrands.id}")
    Long updateProductBrands(@Param("productBrands")ProductBrands productBrands);

    @Update("update shop_productBrands set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id")Long id);

    @Select("select * from shop_productBrands ")
    List<ProductBrands> selectAll();

}
