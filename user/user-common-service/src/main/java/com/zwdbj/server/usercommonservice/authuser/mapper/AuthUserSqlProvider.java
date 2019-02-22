package com.zwdbj.server.usercommonservice.authuser.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class AuthUserSqlProvider {
    public String findRoles(Map paras) {
        String id = (String)paras.get("id");
        long tenantId = (Long)paras.get("tenantId");
        SQL sql = new SQL()
                .SELECT("ur.rolename")
                .FROM("core_userRoles as ur")
                .WHERE("ur.userId=#{id}");
        if (tenantId<=0) {
            sql.AND().WHERE("ur.tenantId is NULL");
        } else {
            sql.AND().WHERE("ur.tenantId=#{tenantId}");
        }
        return sql.toString();
    }
    public String findPermissions(Map paras) {
        String rolenames = (String)paras.get("rolenames");
        long tenantId = (Long)paras.get("tenantId");
        SQL sql = new SQL()
                .SELECT("rp.permissionName")
                .FROM("core_rolePermissions as rp")
                .WHERE("rp.roleName in (#{rolenames})");
        if (tenantId<=0) {
            sql.AND().WHERE("rp.tenantId is NULL");
        } else {
            sql.AND().WHERE("rp.tenantId=#{tenantId}");
        }
        return sql.toString();
    }
}
