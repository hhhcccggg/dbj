package com.zwdbj.server.shop_admin_service.service.productBrands.mapper;


import com.zwdbj.server.shop_admin_service.service.productBrands.model.ProductBrands;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IProductBrandsMapper {
    @Insert("insert into shop_productBrands (id,name,imageUrl,description,orderIndex)" +
            "values(#{id},#{productBrands.name},#{productBrands.imageUrl}," +
            "#{productBrands.description},#{productBrands.orderIndex})")
    Long createProductBrands(@Param("id") Long id, @Param("productBrands") ProductBrands productBrands);

    @Update("upate shop_productBrands set name=#{productBrands.name}," +
            "imageUrl=#{productBrands.imageUrl},description=#{productBrands.description},”" +
            "orderIndex=#{productBrands.orderIndex} where id=#{productBrands.id}")
    Long updateProductBrands(@Param("productBrands") ProductBrands productBrands);

    @Update("update shop_productBrands set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Select("select * from shop_productBrands where isDeleted=0 order by createTime")
    List<ProductBrands> selectAll();

}
