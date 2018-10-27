package com.zwdbj.server.mobileapi.service.user.mapper;

import com.zwdbj.server.mobileapi.service.user.model.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class UserSqlProvider {
    private final String TBL_NAME = "core_users";

    public String updateField(Map paramas) {
        Long id = (Long)paramas.get("id");
        String fields = (String)paramas.get("fields");
        SQL sql = new SQL()
                .UPDATE("core_users")
                .SET(fields)
                .WHERE("id=#{id}");
        return sql.toString();
    }


    public String updateInfo(Map params) {
        UpdateUserInfoInput input = (UpdateUserInfoInput)params.get("input");
        long userId = (long)params.get("userId");
        SQL sql = new SQL()
                .UPDATE(TBL_NAME);
        if (input.getAvatarKey()!=null&&input.getAvatarKey().length()>0) {
            sql.SET("avatarUrl=#{input.avatarKey}");
        }
        sql.SET("nickName=#{input.nickName}")
        .SET("sex=#{input.sex}")
        .SET("birthday=#{input.birthday}")
        .SET("address=#{input.city}")
        .SET("longitude=#{input.longitude}")
        .SET("latitude=#{input.latitude}")
        .SET("occupationId=#{input.occupationId}")
        .SET("loveStatusId=#{input.loveStatusId}");
        sql.WHERE("id=#{userId}");
        return sql.toString();
    }
}
