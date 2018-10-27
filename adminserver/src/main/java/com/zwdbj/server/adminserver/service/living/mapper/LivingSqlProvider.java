package com.zwdbj.server.adminserver.service.living.mapper;

import com.zwdbj.server.adminserver.service.living.model.AdHistoryLivingInput;
import com.zwdbj.server.adminserver.service.living.model.AdTodayLivingInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class LivingSqlProvider {
    public String updateField(Map params) {
        Long id = (Long) params.get("id");
        String fields = (String) params.get("fields");
        SQL sql = new SQL()
                .UPDATE("core_livings")
                .SET(fields)
                .WHERE("id=#{id}");
        return sql.toString();
    }

    public String todayLivingAd(Map params) {
        AdTodayLivingInput input = (AdTodayLivingInput) params.get("input");
        SQL sql = new SQL()
                .SELECT("l.*,u.nickName userNickName")
                .FROM("core_livings l")
                .WHERE("date(l.createTime)= curDate()")
                .INNER_JOIN("core_users u on l.userId=u.id");
        try {
            if (input.getIsLiving()!=-1) {
                sql.WHERE("l.isLiving=#{input.isLiving}");
            }
            if (input.getStatus()!=-1){
                sql.WHERE("l.status=#{input.status}");
            }
            if (input.getKeywords() != null && input.getKeywords().length() > 0) {
                sql.WHERE(String.format("l.userId like '%s'", ("%" + input.getKeywords() + "%")))
                        .OR()
                        .WHERE(String.format("u.phone like '%s'", ("%" + input.getKeywords() + "%")))
                        .OR()
                        .WHERE(String.format("u.nickName like '%s'", ("%" + input.getKeywords() + "%")));
            }
            sql.ORDER_BY("l.createTime desc");
        } catch (RuntimeException e) {
            throw new RuntimeException("请输入正确的id");
        }
        return sql.toString();
    }

    public String historyLiving(Map params) {
        AdHistoryLivingInput input = (AdHistoryLivingInput) params.get("input");
        SQL sql = new SQL()
                .SELECT("l.*,u.nickName")
                .FROM("core_livings l")
                .WHERE("date(l.createTime)!=curDate()")
                .INNER_JOIN("core_users u on l.userId=u.id");
        try {
            if (input.getKeywords() != null && input.getKeywords().length() > 0) {
                sql.WHERE(String.format("l.userId like '%s'", ("%" + input.getKeywords() + "%")))
                        .OR()
                        .WHERE(String.format("u.phone like '%s'", ("%" + input.getKeywords() + "%")))
                        .OR()
                        .WHERE(String.format("u.nickName like '%s'", ("%" + input.getKeywords() + "%")));
            }
            sql.ORDER_BY("l.createTime desc");
        } catch (RuntimeException e) {
            throw new RuntimeException("请输入正确的id");
        }
        return sql.toString();
    }

    public String doLivingComplainAd(Map params) {
        Long id = (Long) params.get("id");
        //AdDoLivingInput input = (AdDoLivingInput) params.get("input");
        SQL sql = new SQL()
                .UPDATE("core_livings")
                .SET("status=1,rejectMsg=#{input.rejectMsg},isLiving=false")
                .WHERE("id=#{id}");
        return sql.toString();
    }
}
