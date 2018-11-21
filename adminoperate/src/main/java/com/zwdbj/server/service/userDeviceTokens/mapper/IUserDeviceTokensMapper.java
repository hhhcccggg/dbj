package com.zwdbj.server.service.userDeviceTokens.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserDeviceTokensMapper {

    @Insert("insert into core_userDeviceTokens(id,userId,deviceToken,deviceType,isManualData) " +
            "values(#{id},#{userId},#{deviceToken},#{deviceType},true)")
    int newDeviceToken(@Param("id")long id,@Param("userId")long userId,@Param("deviceToken")String deviceToken,@Param("deviceType")String deviceType);

}
