package com.zwdbj.server.mobileapi.service.category.mapper;

import com.zwdbj.server.mobileapi.service.category.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICategoryMapper {
    @SelectProvider(type = CategorySqlProvider.class,method = "search")
    List<CategoryDto> search(@Param("input") CategorySearchInput input);
}
