package com.zwdbj.server.shop_admin_service.ProductAttris.mapper;


import com.zwdbj.server.shop_admin_service.ProductAttris.model.ProductAttris;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IProductAttrisMapper {

    @Insert("insert into shop_productAttris (id,name,identifyId,categoryId,parentId,groupId)" +
            "values(#{id},#{productAttris.name},#{productAttris.identifyId}," +
            "#{productAttris.categoryId},#{productAttris.parentId},#{productAttris.groupId})")
    Long createProductAttris(@Param("id") Long id, @Param("productAttris") ProductAttris productAttris);

    @Update("update shop_productAttris set isDeleted=1,deleteTime=now() where id=#{id}")
    Long deleteById(@Param("id") Long id);

    @Update("update shop_productAttris set name=#{productAttris.name}," +
            "identifyId=#{productAttris.identifyId},categoryId=#{productAttris.categoryId}," +
            "parentId=#{productAttris.parentId},groupId=#{productAttris.groupId} where id=#{productAttris.id}")
    Long updateProductAttris(@Param("productAttris") ProductAttris productAttris);

    @Select("select * from shop_productAttris where isDeleted=0 order by createTime")
    List<ProductAttris> selectAll();

}
