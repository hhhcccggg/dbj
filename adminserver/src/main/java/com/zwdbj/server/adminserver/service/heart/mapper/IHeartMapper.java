package com.zwdbj.server.adminserver.service.heart.mapper;

import com.zwdbj.server.adminserver.service.heart.model.HeartModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IHeartMapper {
    @Select("select * from core_hearts where userId=#{userId} and resourceOwnerId=#{resourceOwnerId}")
    HeartModel findHeart(@Param("userId") long userId, @Param("resourceOwnerId") long resourceOwnerId);
}
