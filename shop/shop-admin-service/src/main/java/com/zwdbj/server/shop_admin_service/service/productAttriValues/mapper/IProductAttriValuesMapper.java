package com.zwdbj.server.shop_admin_service.service.productAttriValues.mapper;

import com.zwdbj.server.shop_admin_service.service.productAttriValues.model.ProductAttriValues;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IProductAttriValuesMapper {
    @Insert("insert into shop_productAttriValues (id,attiValue,productAttriId) values(" +
            "#{id},#{productAttriValues.attiValue},#{productAttriValues.productAttriId})")
    Long createProductAttriValues(@Param("id") Long id, @Param("productAttriValues") ProductAttriValues productAttriValues);

    @Update("update shop_productAttriValues set attiValue=#{productAttriValues.attiValue}," +
            "productAttriId=#{productAttriValues.productAttriId} where id=#{productAttriValues.id}")
    Long updateProductAttriValues(@Param("productAttriValues") ProductAttriValues productAttriValues);

    @Update("update shop_productAttriValues set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Select("select * from shop_productAttriValues where isDeleted=0 order by createTime")
    List<ProductAttriValues> select();
}










