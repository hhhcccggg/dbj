package com.zwdbj.server.mobileapi.service.complain.mapper;

import com.zwdbj.server.mobileapi.service.complain.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IComplainMapper {
    @Select("select * from core_complainReasons where isOpen=true and type=#{input.type}")
    List<ComplainReasonListInfoDto> list(@Param("input") ComplainReasonListInput input);
    @Insert("insert into core_complains(" +
            "id," +
            "fromTypeId," +
            "fromUserId," +
            "toResId," +
            "toResTypeId," +
            "reasonId," +
            "description," +
            "snapshotUrl) values(" +
            "#{complainInfo.id}," +
            "#{complainInfo.fromTypeId}," +
            "#{complainInfo.fromUserId}," +
            "#{complainInfo.toResId}," +
            "#{complainInfo.toResTypeId}," +
            "#{complainInfo.reasonId}," +
            "#{complainInfo.description}," +
            "#{complainInfo.snapshotKey})")
    long pubComplain(@Param("complainInfo") PubComplainInfo complainInfo);
}
