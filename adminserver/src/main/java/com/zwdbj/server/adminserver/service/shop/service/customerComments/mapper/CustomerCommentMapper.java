package com.zwdbj.server.adminserver.service.shop.service.customerComments.mapper;

import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerCommentMapper {
    @SelectProvider(type = CustomerCommentsSqlProvider.class, method = "getCustomerComments")
    List<CommentInfo> commentList(@Param("legalSubjectId") long legalSubjectId,@Param("searchInfo") SearchInfo searchInfo);

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

    @Select("SELECT count(*) as countComments,round(AVG(rate),1) as rate FROM core_comments as c where c.resourceTypeId=1 " +
            "and c.isOwner=1 and c.reviewStatus='pass' and c.resourceOwnerId in (select p.id from shop_products as p," +
            "shop_stores as s where p.storeId=s.id and s.legalSubjectId=#{legalSubjectId})")
    UserComments countComments(@Param("legalSubjectId") long legalSubjectId);

    @Select("SELECT c.id,c.contentTxt,c.rate,c.heartCount,u.username,u.id as userId,c.createTime,c.resourceOwnerId," +
            "ce.type,ce.dataId,ce.dataContent from core_comments as c left join core_comment_extraDatas as ce " +
            "on ce.commentId=c.id left join core_users as u on u.id=c.userId where c.reviewStatus='pass' and c.isOwner=1  " +
            "and c.resourceTypeId=1 and c.resourceOwnerId in (select p.id from shop_products as p,shop_stores as s " +
            "where p.storeId=s.id and s.legalSubjectId=#{legalSubjectId}) and c.rate=#{rate} order by c.createTime")
    List<CommentInfo> commentRankList(@Param("legalSubjectId") long legalSubjectId, @Param("rate") float rate);

    @Select("SELECT c.rate, count(*) as counts from core_comments as c,shop_products as p ,shop_stores as s " +
            "where c.resourceOwnerId=p.id and p.storeId=s.id and s.legalSubjectId=#{legalSubjectId} " +
            "and c.resourceTypeId=1 and c.isOwner=1 and c.reviewStatus='pass' GROUP BY rate ORDER BY rate DESC")
    List<CommentRank> commentRank(long legalSubjectId);
}
