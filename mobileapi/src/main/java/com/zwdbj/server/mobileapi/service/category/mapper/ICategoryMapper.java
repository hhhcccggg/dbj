package com.zwdbj.server.mobileapi.service.category.mapper;

import com.zwdbj.server.mobileapi.service.category.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICategoryMapper {
    @SelectProvider(type = CategorySqlProvider.class,method = "search")
    List<CategoryDto> search(@Param("input") CategorySearchInput input);


    @Select("<script>select id," +
            "`name`," +
            "parentId," +
            "iconUrl," +
            "orderIndex from core_categories where  isDeleted=0 and status=0 and `type`=1" +
            "<if test='parentId!=0'>" +
                "and parentId=#{parentId}" +
            "</if>" +
            " order by orderIndex desc " +
            "<if test='parentId==0'>" +
            "limit 5" +
            "</if>" +
            "</script>")
    List<CategoryOut> mainSelect(@Param("parentId") long parentId);

    @Select("select id,name from core_categories where isDeleted=0 and status=0 and `type`=1 and parentId=0")
    List<CategoryRecommendDto> categoryRecommends();
}
