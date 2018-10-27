package com.zwdbj.server.mobileapi.service.living.mapper;

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
}
