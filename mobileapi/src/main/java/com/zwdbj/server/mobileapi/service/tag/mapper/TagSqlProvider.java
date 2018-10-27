package com.zwdbj.server.mobileapi.service.tag.mapper;


import com.zwdbj.server.mobileapi.service.tag.model.TagSearchInput;
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
}
