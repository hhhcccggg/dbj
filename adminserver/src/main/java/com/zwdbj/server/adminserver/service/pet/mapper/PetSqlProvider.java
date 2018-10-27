package com.zwdbj.server.adminserver.service.pet.mapper;

import com.zwdbj.server.adminserver.service.pet.model.UpdatePetModelInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class PetSqlProvider {
    public String updateInfo(Map params) {
        UpdatePetModelInput input = (UpdatePetModelInput)params.get("input");
        SQL sql = new SQL()
                .UPDATE("core_pets");
        if (input.getAvatar()!=null&&input.getAvatar().length()>0) {
            sql.SET("`avatar`=#{input.avatar}");
        }
        sql.SET("`nickName`=#{input.nickName}")
                .SET("`birthday`=#{input.birthday}")
                .SET("birthday=#{input.birthday}")
                .SET("`sex`=#{input.sex}")
                .SET("`categoryId`=#{input.categoryId}");
        sql.WHERE("`id`=#{input.id}");
        return sql.toString();
    }
}
