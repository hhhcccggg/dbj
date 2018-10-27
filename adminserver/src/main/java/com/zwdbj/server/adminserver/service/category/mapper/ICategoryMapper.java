package com.zwdbj.server.adminserver.service.category.mapper;

import com.zwdbj.server.adminserver.service.category.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICategoryMapper {
    @SelectProvider(type = CategorySqlProvider.class,method = "search")
    List<CategoryDto> search(@Param("input") CategorySearchInput input);

    @SelectProvider(type = CategorySqlProvider.class,method = "basicCompalinAd")
    List<AdBasicCategoryDto> basicCompalinAd(@Param("input")AdBasicCategoryInput input);

    @Insert("insert into core_categories(id,name) values(#{id},#{input.name})")
    Long addCategoryAd(@Param("id")Long id, @Param("input")AdNewCategoryInput input);
    @Insert("insert into core_categories(id,name,parentId) values(#{id},#{input.name},#{parentId})")
    Long addCategoryAd2(@Param("id")Long id, @Param("input")AdNewCategoryInput input,@Param("parentId")Long parentId);
    @Update("update core_categories set name=#{input.name} where id=#{id}")
    Long editCategoryAd(@Param("id")Long id,@Param("input")AdNewCategoryInput input);
    @Select("select * from core_categories where parentId=#{parentId}")
    List<AdBasicCategoryDto> categoryDetailsAd(@Param("parentId")Long id);
    @Delete("delete from core_categories where id=#{id}")
    Long delCategoryAd(@Param("id")Long id);
}
