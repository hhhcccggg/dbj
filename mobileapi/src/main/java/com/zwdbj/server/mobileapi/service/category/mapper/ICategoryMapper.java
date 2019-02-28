package com.zwdbj.server.mobileapi.service.category.mapper;

import com.zwdbj.server.mobileapi.service.category.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICategoryMapper {
    @SelectProvider(type = CategorySqlProvider.class,method = "search")
    List<CategoryDto> search(@Param("input") CategorySearchInput input);


    @Select("select id," +
            "`name`," +
            "parentId," +
            "iconUrl," +
            "orderIndex from core_categories where  isDeleted=0 and status=0 and `type`=1" +
            " order by orderIndex desc ")
    List<CategoryOut> mainSelect();

    @Select("select id,name from core_categories where isDeleted=0 and status=0 and `type`=1 and parentId=0")
    List<CategoryRecommendDto> categoryRecommends();

    @Select("select `name` from core_categories where status=0 and isDeleted=0 and id in " +
            "(select serviceScopeId from o2o_offlineStoreServiceScopes where storeId=#{storeId} and isDeleted=0)")
    List<String> getScopeServices(@Param("storeId") long storeId);
}
