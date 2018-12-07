package com.zwdbj.server.mobileapi.service.userCoinTypes.mapper;

import com.zwdbj.server.mobileapi.service.userCoinTypes.model.UserCoinTypeModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IUserCoinTypeMapper {
    @Select("select count(id) from core_userCoinTypes where userId=#{userId}")
    int userCoinTypeIsExist(@Param("userId")long userId,@Param("type")String type);
    @Select("select * from core_userCoinTypes where userId=#{userId} and type=#{type}")
    UserCoinTypeModel getUserCoinType(@Param("userId")long userId,@Param("type")String type);
    @Insert("insert into core_userCoinTypes(id,type,coins,userId,) " +
            "values(#{id},#{type},0,#{userId})")
    int greatUserCoinType(@Param("id")long id,@Param("userId")long userId,@Param("type")String type);

}
