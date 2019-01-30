package com.zwdbj.server.mobileapi.service.comment.mapper;

import com.zwdbj.server.mobileapi.service.comment.model.AddCommentModel;
import com.zwdbj.server.mobileapi.service.comment.model.CommentInfoDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ICommentMapper {

    @Select("select " +
            "cmt.id as id," +
            "cmt.userId as userId," +
            "user.nickName as nickName," +
            "user.avatarUrl as userAvatarUrl," +
            "cmt.isOwner as isOwner," +
            "cmt.contentTxt as contentTxt," +
            "cmt.refCommentId as refCommentId," +
            "cmt.heartCount as heartCount," +
            "cmt.resourceOwnerId as resourceOwnerId," +
            "cmt.createTime as createTime, " +
            "video.title as title, " +
            "video.userId as resourcePublishUserId, " +
            "(select count(*) from core_hearts as hsHeart where hsHeart.userId=#{curUserId} and hsHeart.resourceOwnerId=cmt.id) as hasHeart " +
            " from core_comments as cmt inner join core_videos as video on cmt.resourceOwnerId = video.id " +
            "inner join core_users as user on cmt.userId=user.id " +
            "where cmt.resourceOwnerId=#{resId} order by cmt.heartCount desc,cmt.createTime desc")
    List<CommentInfoDto> list(@Param("resId") long resId,@Param("curUserId") long curUserId);
    @Select("select " +
            "cmt.id as id," +
            "cmt.userId as userId," +
            "user.nickName as nickName," +
            "user.avatarUrl as userAvatarUrl," +
            "cmt.isOwner as isOwner," +
            "cmt.contentTxt as contentTxt," +
            "cmt.refCommentId as refCommentId," +
            "cmt.heartCount as heartCount," +
            "cmt.resourceOwnerId as resourceOwnerId," +
            "cmt.createTime as createTime, " +
            "video.title as title, " +
            "video.userId as resourcePublishUserId, " +
            "(select count(*) from core_hearts as hsHeart where hsHeart.userId=#{curUserId} and hsHeart.resourceOwnerId=cmt.id) as hasHeart " +
            " from core_comments as cmt inner join core_videos as video on cmt.resourceOwnerId = video.id " +
            "inner join core_users as user on cmt.userId=user.id " +
            "where video.status=0 and video.userId=#{userId} order by cmt.createTime desc")
    List<CommentInfoDto> myAllComments(@Param("userId") long userId,@Param("curUserId") long curUserId);
    @Select("select " +
            "cmt.id as id," +
            "cmt.userId as userId," +
            "user.nickName as nickName," +
            "user.avatarUrl as userAvatarUrl," +
            "cmt.isOwner as isOwner," +
            "cmt.contentTxt as contentTxt," +
            "cmt.refCommentId as refCommentId," +
            "cmt.heartCount as heartCount," +
            "cmt.resourceOwnerId as resourceOwnerId," +
            "cmt.createTime as createTime, " +
            "video.title as title, " +
            "video.userId as resourcePublishUserId," +
            "(select count(*) from core_hearts as hsHeart where hsHeart.userId=#{curUserId} and hsHeart.resourceOwnerId=cmt.id) as hasHeart  " +
            " from core_comments as cmt inner join core_videos as video on cmt.resourceOwnerId = video.id " +
            "inner join core_users as user on cmt.userId=user.id " +
            "where cmt.id=#{id}")
    CommentInfoDto getCommentDto(@Param("id") long id,@Param("curUserId") long curUserId);

    @Update("update core_comments set heartCount=heartCount+(#{num}) where id=#{id}")
    long addHeart(@Param("id") long id,@Param("num") int num);
    @Delete("delete from core_comments where id=#{id}")
    long delete(@Param("id") long id);

    @Insert("INSERT INTO `core_comments` (`id`, `userId`, `isOwner`," +
            "`contentTxt`,`resourceOwnerId`, `refCommentId`,reviewStatus,originContentTxt) VALUES " +
            "(#{model.id}," +
            "#{model.userId}," +
            "#{model.isOwner}," +
            "#{model.content}," +
            "#{model.resId}," +
            "#{model.refCommentId}," +
            "'reviewing'," +
            "#{model.content})")
    long add(@Param("model") AddCommentModel model);

    @Delete("delete from core_comments where resourceOwnerId=#{resourceOwnerId}")
    Long deleteVideoComments(@Param("resourceOwnerId")Long resourceOwnerId);

    @Select("select count(id) from core_comments where userId=#{userId} and to_days(createTime)=to_days(now())")
    int isFirstPublicComment(@Param("userId")long userId);

    @Select("select count(id) from core_comments where resourceOwnerId=#{resourceOwnerId} and isDeleted=0")
    long findCommentNumById(@Param("resourceOwnerId")long resourceOwnerId);
}
