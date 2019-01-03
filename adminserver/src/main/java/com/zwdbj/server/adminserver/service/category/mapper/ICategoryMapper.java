package com.zwdbj.server.adminserver.service.category.mapper;

import com.zwdbj.server.adminserver.service.category.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICategoryMapper {
    @SelectProvider(type = CategorySqlProvider.class, method = "search")
    List<CategoryDto> search(@Param("input") CategorySearchInput input);

    @SelectProvider(type = CategorySqlProvider.class, method = "basicCompalinAd")
    List<AdBasicCategoryDto> basicCompalinAd(@Param("input") AdBasicCategoryInput input);

    @Insert("insert into core_categories(id,name,type,iconUrl,status,orderIndex)" +
            " values(#{id},#{input.name},#{input.type},#{input.iconUrl},#{input.status}," +
            "#{input.orderIndex})")
    Long addCategoryAd(@Param("id") Long id, @Param("input") AdNewCategoryInput input);

    @Insert("insert into core_categories(id,name,parentId,type,iconUrl,status,orderIndex) " +
            "values(#{id},#{input.name},#{parentId},#{input.type}," +
            "#{input.iconUrl},#{input.status},#{input.orderIndex})")
    Long addCategoryAd2(@Param("id") Long id, @Param("input") AdNewCategoryInput input, @Param("parentId") Long parentId);

    @Update("update core_categories set name=#{input.name},iconUrl=#{input.iconUrl},status=#{input.status} where id=#{id}")
    Long editCategoryAd(@Param("id") Long id, @Param("input") AdNewCategoryNameInput input);

    @Select("select * from core_categories where parentId=#{parentId}")
    List<AdBasicCategoryDto> categoryDetailsAd(@Param("parentId") Long id);

    @Delete("delete from core_categories where id=#{id}")
    Long delCategoryAd(@Param("id") Long id);
}
