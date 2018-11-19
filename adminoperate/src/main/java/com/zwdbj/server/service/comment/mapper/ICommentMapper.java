package com.zwdbj.server.service.comment.mapper;

import org.apache.ibatis.annotations.*;


@Mapper
public interface ICommentMapper {
    @Insert("insert into core_comments(id,userId,contentTxt,resourceOwnerId,reviewStatus,originContentTxt) " +
            "values(#{id},#{userId},#{contentTxt},#{resourceOwnerId},'pass',#{contentTxt})")
    int greatComment(@Param("id") Long id,@Param("userId") Long userId,@Param("contentTxt") String contentTxt,@Param("resourceOwnerId") Long resourceOwnerId);
}
