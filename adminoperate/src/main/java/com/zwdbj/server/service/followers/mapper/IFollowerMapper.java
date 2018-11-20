package com.zwdbj.server.service.followers.mapper;

import com.zwdbj.server.service.followers.model.FollowersModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IFollowerMapper {

    @Select("select count(id) from core_followers where followerUserId=#{followerUserId} and userId=#{userId}")
    int followIsExit(@Param("followerUserId")long followerUserId,@Param("userId")long userId);

    @Select("select id,followerUserId,userId from core_followers where userId=#{userId}")
    List<FollowersModel> isExitFollow(@Param("userId")long userId);

    @Select("select id,followerUserId,userId from core_followers where followerUserId=#{followerUserId}")
    List<FollowersModel> isExitFocuses(@Param("followerUserId")long followerUserId);

    @Insert("insert into core_followers(id,followerUserId,userId,isManualData) values(#{id},#{followerUserId},#{userId},true)")
    int newMyFollower(@Param("id")long id,@Param("followerUserId")long followerUserId,@Param("userId")long userId);
}
