package com.zwdbj.server.mobileapi.service.heart.mapper;

import com.zwdbj.server.mobileapi.service.heart.model.HeartModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IHeartMapper {
    @Insert("insert into core_hearts(id,userId,resourceOwnerId,resourceTypeId) values(" +
            "#{id},#{userId},#{resourceOwnerId},#{type})")
    long heart(@Param("id") long id, @Param("userId") long userId, @Param("resourceOwnerId") long resourceOwnerId, @Param("type") int type);

    @Delete("delete from core_hearts where userId=#{userId} and resourceOwnerId=#{resourceOwnerId}")
    long unHeart(@Param("userId") long userId,@Param("resourceOwnerId") long resourceOwnerId);

    @Select("select * from core_hearts where userId=#{userId} and resourceOwnerId=#{resourceOwnerId}")
    HeartModel findHeart(@Param("userId") long userId, @Param("resourceOwnerId") long resourceOwnerId);

    @Delete("delete from core_hearts where resourceOwnerId=#{resourceOwnerId}")
    Long deleteVideoHeart(@Param("resourceOwnerId")Long resourceOwnerId);

    @Select("select count(id) from core_hearts where userId=#{userId} and to_days(createTime)=to_days(now()) ")
    int isFirstHeart(@Param("userId")long userId);

    @Select("select count(id) from core_hearts where resourceOwnerId=#{resourceOwnerId} and resourceTypeId=#{resourceTypeId} and isDeleted=0 ")
    long getHeartCountByResourceOwnerId(@Param("resourceOwnerId") long petId,@Param("resourceTypeId")int type);
}
