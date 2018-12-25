package com.zwdbj.server.mobileapi.service.category.mapper;

import com.zwdbj.server.mobileapi.service.category.model.CategorySearchInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class CategorySqlProvider {
    public String search(Map paras) {
        CategorySearchInput input = (CategorySearchInput) paras.get("input");
        SQL sql = new SQL()
                .FROM("core_categories as m")
                .SELECT("*,(select count(*) from core_categories as subCate where subCate.parentId=m.id) as isHaveNextNode")
                .WHERE("type=#{input.type}")
                .WHERE("status=0")
                .WHERE("parentId=#{input.parentId}");
        return sql.toString();
    }
}
