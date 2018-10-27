package com.zwdbj.server.adminserver.service.category.mapper;

import com.zwdbj.server.adminserver.service.category.model.AdBasicCategoryInput;
import com.zwdbj.server.adminserver.service.category.model.CategorySearchInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class CategorySqlProvider {
    public String search(Map paras) {
        CategorySearchInput input = (CategorySearchInput)paras.get("input");
        SQL sql = new SQL()
                .FROM("core_categories as m")
                .SELECT("*,(select count(*) from core_categories as subCate where subCate.parentId=m.id) as isHaveNextNode")
                .WHERE("type=#{input.type}")
                .WHERE("parentId=#{input.parentId}");
        return sql.toString();
    }

    public String basicCompalinAd(Map params){
        AdBasicCategoryInput input = (AdBasicCategoryInput)params.get("input");
        SQL sql = new SQL()
                .SELECT("*,(select count(*) from core_categories as g where g.parentId=m.id) as sonCount")
                .FROM("core_categories as m")
                .WHERE("m.parentId=0");
        if (input.getIsDeleted()!=-1) {
            sql.WHERE("isDeleted=#{input.isDeleted}");
        }
        if (input.getType() !=-1){
            sql.WHERE("type=#{input.type}");
        }
        if (input.getKeywords()!=null  && input.getKeywords().length()>0){
            sql.WHERE(String.format("name like '%s'",("%"+input.getKeywords()+"%")));
        }
        return sql.toString();

    }
}
