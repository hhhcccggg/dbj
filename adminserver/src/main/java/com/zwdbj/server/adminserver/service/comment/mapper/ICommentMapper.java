package com.zwdbj.server.adminserver.service.comment.mapper;

import com.zwdbj.server.adminserver.service.comment.model.AddCommentModel;
import com.zwdbj.server.adminserver.service.comment.model.CommentInfoDto;
import com.zwdbj.server.adminserver.service.comment.model.CommentReviewDto;
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
            "cmt.originContentTxt as originContentTxt," +
            "cmt.reviewStatus as reviewStatus," +
            "cmt.createTime as createTime, " +
            "video.title as title, " +
            "video.userId as resourcePublishUserId, " +
            "(select count(*) from core_hearts as hsHeart where hsHeart.userId=#{curUserId} and hsHeart.resourceOwnerId=cmt.id) as hasHeart " +
            " from core_comments as cmt inner join core_videos as video on cmt.resourceOwnerId = video.id " +
            "inner join core_users as user on cmt.userId=user.id " +
            "where cmt.resourceOwnerId=#{resId} order by cmt.createTime desc")
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
            "video.userId as resourcePublishUserId," +
            "(select count(*) from core_hearts as hsHeart where hsHeart.userId=#{curUserId} and hsHeart.resourceOwnerId=cmt.id) as hasHeart  " +
            " from core_comments as cmt inner join core_videos as video on cmt.resourceOwnerId = video.id " +
            "inner join core_users as user on cmt.userId=user.id " +
            "where cmt.id=#{id}")
    CommentInfoDto getCommentDto(@Param("id") long id,@Param("curUserId") long curUserId);

    @Delete("delete from core_comments where id=#{id}")
    long delete(@Param("id") long id);

    @Insert("INSERT INTO `core_comments` (`id`, `userId`, `isOwner`," +
            "`contentTxt`,`resourceOwnerId`, `refCommentId`) VALUES " +
            "(#{model.id}," +
            "#{model.userId}," +
            "#{model.isOwner}," +
            "#{model.content}," +
            "#{model.resId}," +
            "#{model.refCommentId})")
    long add(@Param("model") AddCommentModel model);

    @Update("update core_comments set reviewStatus='block',contentTxt='******',reviewedResult='评论涉嫌违规' where id=#{id}")
    Long screeningComment(@Param("id")Long id);

    @Select("select id,contentTxt from core_comments where reviewStatus='reviewing'")
    List<CommentReviewDto> findCommentReviewing();

    @Update("update core_comments set reviewStatus='pass' where id=#{id}")
    int passComment(@Param("id")Long id);
    @Update("update core_comments set contentTxt='******',reviewedResult='评论涉嫌违规',reviewStatus=#{reviewStatus} where id=#{id}")
    int blockComment(@Param("id")Long id,@Param("reviewStatus")String reviewStatus);

    @Insert("insert into core_comments(id,userId,contentTxt,resourceOwnerId,reviewStatus,originContentTxt) " +
            "values(#{id},#{userId},#{contentTxt},#{resourceOwnerId},'pass',#{contentTxt})")
    int greatComment(@Param("id") Long id,@Param("userId") Long userId,@Param("contentTxt") String contentTxt,@Param("resourceOwnerId") Long resourceOwnerId);

}
