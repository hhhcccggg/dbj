package com.zwdbj.server.mobileapi.service.appVersion.mapper;

import com.zwdbj.server.mobileapi.service.appVersion.model.AppVersionDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IAppVersionMapper {

    @Select("select * from core_appVersions where platform=#{platform} ORDER BY createTime DESC LIMIT 1")
    AppVersionDto getAppVersion(@Param("platform") int platform);
}
