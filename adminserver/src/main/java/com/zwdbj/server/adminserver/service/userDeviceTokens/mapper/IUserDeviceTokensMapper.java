package com.zwdbj.server.adminserver.service.userDeviceTokens.mapper;

import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokensInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IUserDeviceTokensMapper {

    @Select("select id,deviceToken,deviceType,deviceName from core_userDeviceTokens where userId=#{userId}")
    AdUserDeviceTokenDto getDeviceTokenByUserId(@Param("userId")Long userId);

    @Select("select * from core_userDeviceTokens order by createTime desc")
    List<AdDeviceTokenDto> getTokenList();

    @Delete("delete from core_userDeviceTokens where id=#{id}")
    Long delTokenById(@Param("id")Long id);
}
