package com.zwdbj.server.adminserver.service.complain.mapper;

import com.zwdbj.server.adminserver.service.complain.model.AdFindComplainInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ComplainSqlProvider {
    public String basicComplainAd(Map params){
        AdFindComplainInput input = (AdFindComplainInput)params.get("input");
        SQL sql = new SQL()
                .SELECT("r.title,r.isOpen,r.description,r.type,count(c.reasonId) a ")
                .FROM("core_complainReasons as r")
                .LEFT_OUTER_JOIN("core_complains as c on c.reasonId=r.id")
                .WHERE("r.type=#{resTypeId}");

        if (input.getStatus()!=-1){
            sql.WHERE("r.isOpen=#{input.status}");
        }
        if (input.getKeywords()!=null && input.getKeywords().length()>0) {
            sql.WHERE(String.format("r.title like '%s'",("%"+input.getKeywords()+"%")));
        }
        sql.GROUP_BY("r.id ").ORDER_BY("a desc");
        return sql.toString();
    }
}
