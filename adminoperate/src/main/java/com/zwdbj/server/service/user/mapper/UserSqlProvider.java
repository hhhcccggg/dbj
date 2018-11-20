package com.zwdbj.server.service.user.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class UserSqlProvider {
    public String updateField(Map paramas) {
        Long id = (Long)paramas.get("id");
        String fields = (String)paramas.get("fields");
        SQL sql = new SQL()
                .UPDATE("core_users")
                .SET(fields)
                .WHERE("id=#{id}");
        return sql.toString();
    }

}
