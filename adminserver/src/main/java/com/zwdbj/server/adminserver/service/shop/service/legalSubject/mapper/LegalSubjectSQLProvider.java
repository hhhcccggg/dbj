package com.zwdbj.server.adminserver.service.shop.service.legalSubject.mapper;

import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.LegalSubjectSearchInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class LegalSubjectSQLProvider {
    public String searchSql(Map params){
        LegalSubjectSearchInput input = (LegalSubjectSearchInput)params.get("input");
        SQL sql = new SQL().
                SELECT("*")
                .FROM("shop_legalSubjects")
                .WHERE("reviewed=1");
        if (input.getStatus()!=-1){
            sql.WHERE("status=#{input.status}");
        }
        if (input.getType()!=null && input.getType().length()>0){
            sql.WHERE("type=#{type}");
        }
        if (input.getKeyWords() !=null && input.getKeyWords().length()>0){
            sql.WHERE(String.format("name like '%s'", ("%" + input.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("contactPhone like '%s'", ("%" + input.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("contactName like '%s'", ("%" + input.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("leagalRepresentativeName like '%s'", ("%" + input.getKeyWords() + "%")));
        }
        if (input.getStartTime() !=null  && input.getStartTime().length()!=0 && input.getEndTime() !=null && input.getEndTime().length()!=0){
            sql.WHERE(String.format("createTime between '%s' and '%s'",input.getStartTime(),input.getEndTime()));
        }
        sql.ORDER_BY("createTime desc");
        return sql.toString();

    }
    public String searchUnReviewedSql(Map params){
        LegalSubjectSearchInput input = (LegalSubjectSearchInput)params.get("input");
        SQL sql = new SQL().
                SELECT("*")
                .FROM("shop_legalSubjects")
                .WHERE("reviewed=0");
        if (input.getStatus()!=-1){
            sql.WHERE("status=#{input.status}");
        }
        if (input.getType()!=null && input.getType().length()>0){
            sql.WHERE("type=#{type}");
        }
        if (input.getKeyWords() !=null && input.getKeyWords().length()>0){
            sql.WHERE(String.format("name like '%s'", ("%" + input.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("contactPhone like '%s'", ("%" + input.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("contactName like '%s'", ("%" + input.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("leagalRepresentativeName like '%s'", ("%" + input.getKeyWords() + "%")));
        }
        if (input.getStartTime() !=null  && input.getStartTime().length()!=0 && input.getEndTime() !=null && input.getEndTime().length()!=0){
            sql.WHERE(String.format("createTime between '%s' and '%s'",input.getStartTime(),input.getEndTime()));
        }
        sql.ORDER_BY("createTime desc");
        return sql.toString();

    }
}
