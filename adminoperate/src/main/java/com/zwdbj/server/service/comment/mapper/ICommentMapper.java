package com.zwdbj.server.service.comment.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ICommentMapper {
    @Insert("insert into core_comments(id,userId,contentTxt,resourceOwnerId,resourceTypeId,reviewStatus,originContentTxt,isManualData) values(#{id},#{userId},#{contentTxt},#{resourceOwnerId},0,'pass',#{contentTxt},true)")
    int greatComment(@Param("id") Long id,@Param("userId") Long userId,@Param("contentTxt") String contentTxt,@Param("resourceOwnerId") Long resourceOwnerId);

    @Select("select id from core_comments where resourceOwnerId=#{resourceOwnerId} and isManualData=true order by createTime")
    List<Long> getVideoCommentIds(@Param("resourceOwnerId")long resourceOwnerId);

    @Update("update core_comments set heartCount=#{heartCount} where id=#{id}")
    int updateheart(@Param("id")long id,@Param("heartCount")long heartCount);
}
