package com.zwdbj.server.adminserver.service.shop.service.customerComments.mapper;

import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerCommentMapper {
    @Select("SELECT c.id,c.contentTxt,c.rate,c.heartCount,u.username,u.id as userId," +
            "c.createTime,c.resourceOwnerId,d.`name` as resourceOwnerName,ce.type,ce.dataId,ce.dataContent " +
            "from core_comments as c,core_comment_extraDatas as ce,core_users as u,shop_discountCoupons as d,shop_stores as s " +
            "where ce.commentId=c.id and d.id=c.resourceOwnerId and u.id=c.userId and c.reviewStatus='pass' " +
            "and c.isOwner=1 and d.storeId=s.id and s.legalSubjectId=#{legalSubjectId} and c.resourceTypeId=1  order by c.createTime")
    List<CommentInfo> commentList(@Param("legalSubjectId") long legalSubjectId);

    @Select("SELECT c.id as refCommentId,u.fullName as userName,u.id AS userId,c.createTime,c.contentTxt as refContentTxt " +
            "FROM core_comments AS c,core_users AS u " +
            "WHERE c.id =#{commentId} AND c.userId=u.id AND c.isOwner=0")
    RefComment commentReply(@Param("commentId") long commentId);

    @Insert("insert into core_comments(id,contentTxt,originContentTxt,userId,refCommentId,reviewStatus,isOwner,resourceOwnerId) " +
            "values(#{id},#{replyComment.contentTxt},#{replyComment.contentTxt},#{replyComment.userId}," +
            "#{replyComment.refCommentId},'reviewing',0,#{replyComment.resourceOwnerId})")
    Long replyComment(@Param("id") long id, @Param("replyComment") ReplyComment replyComment);

    @Update("update core_comments set isDeleted=1 and deleteTime=now() where id=#{commentId}")
    Long deleteComment(@Param("commentId") long commentId);

    @Select("SELECT count(*) as countComments,AVG(rate) as rate FROM core_comments as c " +
            "where c.resourceTypeId=1 and c.reviewStatus='pass' and c.resourceOwnerId in" +
            "(select d.id from shop_discountCoupons as d,shop_stores as s where d.storeId=s.id and s.legalSubjectId=#{legalSubjectId})")
    UserComments countComments(@Param("legalSubjectId") long legalSubjectId);

    @Select("SELECT c.id,c.contentTxt,c.rate,c.heartCount,u.username,u.id as userId," +
            "c.createTime,c.resourceOwnerId,d.`name` as resourceOwnerName,ce.type,ce.dataId,ce.dataContent " +
            "from core_comments as c,core_comment_extraDatas as ce,core_users as u,shop_discountCoupons as d,shop_stores as s " +
            "where ce.commentId=c.id and d.id=c.resourceOwnerId and u.id=c.userId and c.reviewStatus='pass' " +
            "and c.isOwner=1 and d.storeId=s.id and s.legalSubjectId=#{legalSubjectId} and c.resourceTypeId=1 and c.rate=#{rate} order by c.createTime")
    List<CommentInfo> commentRankList(@Param("legalSubjectId") long legalSubjectId, @Param("rate") float rate);

    @Select("SELECT c.rate, count(*) as counts from core_comments as c,shop_discountCoupons as d ,shop_stores as s " +
            "where c.resourceOwnerId=d.id and d.storeId=s.id and s.legalSubjectId=#{legalSubjectId} GROUP BY rate ORDER BY rate DESC")
    List<CommentRank> commentRank(long legalSubjectId);
}
