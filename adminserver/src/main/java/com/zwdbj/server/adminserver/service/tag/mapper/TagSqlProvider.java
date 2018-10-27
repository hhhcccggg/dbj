package com.zwdbj.server.adminserver.service.tag.mapper;

import com.zwdbj.server.adminserver.service.tag.model.AdVideoTagInput;
import com.zwdbj.server.adminserver.service.tag.model.TagSearchInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class TagSqlProvider {
    public String search(Map paras) {
        TagSearchInput input = (TagSearchInput)paras.get("input");
        SQL sql = new SQL()
                .FROM("core_tags")
                .SELECT("*")
                .ORDER_BY("recommendIndex desc");
        return sql.toString();
    }

    public String getVideoTagAd(Map params){
        AdVideoTagInput input = (AdVideoTagInput)params.get("input");
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("core_tags ");
        if (input.getIsDeleted()!=-1){
            sql.WHERE("isDeleted=#{input.isDeleted}");
        }
        if (input.getKeywords()!=null && input.getKeywords().length()>0) {
            sql.WHERE(String.format(" name like '%s'",("%"+input.getKeywords()+"%")));
        }
        sql.ORDER_BY("resNumber desc");
        return sql.toString();
    }
}
