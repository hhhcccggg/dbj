package com.zwdbj.server.adminserver.service.userDeviceTokens.mapper;

import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokensInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IUserDeviceTokensMapper {
    @Select("select count(deviceToken) from core_userDeviceTokens where deviceType=#{deviceType} and deviceToken=#{deviceToken}")
    int deviceTokenIsExist(@Param("deviceType")String deviceType,@Param("deviceToken")String deviceToken);

    @Insert("insert into core_userDeviceTokens(id,userId,deviceToken,deviceType,deviceName) " +
            "values(#{id},#{input.userId},#{input.deviceToken},#{input.deviceType},#{input.deviceName})")
    int insertDeviceToken(@Param("id")Long id,@Param("input")AdUserDeviceTokensInput input);

    @Update("update core_userDeviceTokens set deviceName=#{input.deviceName},deviceToken=#{input.deviceToken},deviceType=#{input.deviceType} where userId=#{input.userId}")
    int updateTokenByUserId(@Param("input")AdUserDeviceTokensInput input);
    @Select("select count(userId) from core_userDeviceTokens where userId=#{userId}")
    int findUserIdIsExist(@Param("userId")Long userId);

    @Delete("delete from core_userDeviceTokens where deviceType=#{deviceType} and deviceToken=#{deviceToken}")
    int deleteIt(@Param("deviceType")String deviceType,@Param("deviceToken")String deviceToken);

    @Select("select id,deviceToken,deviceType,deviceName from core_userDeviceTokens where userId=#{userId}")
    AdUserDeviceTokenDto getDeviceTokenByUserId(@Param("userId")Long userId);

    @Select("select * from core_userDeviceTokens order by createTime desc")
    List<AdDeviceTokenDto> getTokenList();

    @Delete("delete from core_userDeviceTokens where id=#{id}")
    Long delTokenById(@Param("id")Long id);
}
