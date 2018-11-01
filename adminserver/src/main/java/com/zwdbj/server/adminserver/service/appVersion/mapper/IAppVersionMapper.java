package com.zwdbj.server.adminserver.service.appVersion.mapper;

import com.zwdbj.server.adminserver.service.appVersion.model.AdAppVersionInput;
import com.zwdbj.server.adminserver.service.appVersion.model.AppVersionDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IAppVersionMapper {


    @Insert("insert into core_appVersions(id,version,title,description,versionNum,platform,downloadUrl,upgradeType) " +
            "values(#{id},#{input.version},#{input.title},#{input.description},0,#{input.platform},#{input.downloadUrl},#{input.upgradeType})")
    Long newAppVersion(@Param("id")Long id,@Param("input") AdAppVersionInput input);

    @Delete("delete from core_appVersions where id=#{id}")
    Long delAppVersion(@Param("id")Long id);

    @Select("select * from core_appVersions order by createTime desc")
    List<AppVersionDto> searchVersionList();

    @Update("update core_appVersions set version=#{input.version},title=#{input.title}," +
            "description=#{input.description},platform=#{input.platform},downloadUrl=#{input.downloadUrl}," +
            "upgradeType=#{input.upgradeType} where id=#{id}")
    Long updateVersion(@Param("id")Long id,@Param("input")AdAppVersionInput input);
}
