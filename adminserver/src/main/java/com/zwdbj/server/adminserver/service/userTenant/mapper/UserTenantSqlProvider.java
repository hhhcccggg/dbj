package com.zwdbj.server.adminserver.service.userTenant.mapper;

import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantSearchInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class UserTenantSqlProvider {
    public String getUserTenants(Map params){
        UserTenantSearchInput input = (UserTenantSearchInput)params.get("input");
        SQL sql = new SQL()
                .SELECT("ut.*,u.nickName,u.phone ")
                .FROM("core_user_tenants ut")
                .LEFT_OUTER_JOIN("core_users u on u.tenantId=ut.id")
                .WHERE("u.isSuper=true");
        if (input.getKeyWords() !=null && input.getKeyWords().length()>0 ){
            sql.WHERE(String.format("ut.name like '%s'", ("%" + input.getKeyWords() + "%")));
        }
        if (input.getStartTime() !=null  && input.getStartTime().length()!=0 && input.getEndTime() !=null && input.getEndTime().length()!=0){
            sql.WHERE(String.format("ut.createTime between '%s' and '%s'",input.getStartTime(),input.getEndTime()));
        }
        sql.ORDER_BY("ut.createTime desc");
        return sql.toString();
    }
}
