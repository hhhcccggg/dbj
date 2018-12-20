package com.zwdbj.server.mobileapi.service.userDeviceTokens.mapper;

import com.zwdbj.server.mobileapi.service.userDeviceTokens.model.UserDeviceTokenDto;
import com.zwdbj.server.mobileapi.service.userDeviceTokens.model.UserDeviceTokensInput;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IUserDeviceTokensMapper {
    @Select("select count(id) from core_userDeviceTokens where userId=#{userId}and deviceType=#{deviceType} " +
            "and deviceToken=#{deviceToken}")
    int deviceTokenIsExist(@Param("userId")long userId,@Param("deviceType")String deviceType,@Param("deviceToken")String deviceToken);

    @Insert("insert into core_userDeviceTokens(id,userId,deviceToken,deviceType,deviceName) " +
            "values(#{id},#{input.userId},#{input.deviceToken},#{input.deviceType},#{input.deviceName})")
    int insertDeviceToken(@Param("id")Long id,@Param("input")UserDeviceTokensInput input);

    @Update("update core_userDeviceTokens set deviceName=#{input.deviceName},deviceToken=#{input.deviceToken}," +
            "deviceType=#{input.deviceType} where userId=#{input.userId}")
    int updateTokenByUserId(@Param("input")UserDeviceTokensInput input);
    @Select("select count(userId) from core_userDeviceTokens where userId=#{userId}")
    int findUserIdIsExist(@Param("userId")Long userId);

    @Delete("delete from core_userDeviceTokens where deviceType=#{deviceType} and deviceToken=#{deviceToken}")
    int deleteIt(@Param("deviceType")String deviceType,@Param("deviceToken")String deviceToken);

    @Select("select id,deviceToken,deviceType,deviceName from core_userDeviceTokens where userId=#{userId}")
    UserDeviceTokenDto getDeviceTokenByUserId(@Param("userId")Long userId);
}
