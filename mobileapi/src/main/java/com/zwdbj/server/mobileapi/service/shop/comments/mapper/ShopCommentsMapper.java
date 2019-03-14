package com.zwdbj.server.mobileapi.service.shop.comments.mapper;

import com.zwdbj.server.mobileapi.service.shop.comments.model.CommentInput;
import com.zwdbj.server.mobileapi.service.shop.comments.model.ShopCommentsExtraDatas;
import com.zwdbj.server.mobileapi.service.shop.comments.model.UserComments;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopCommentsMapper {

    @Select("SELECT count(*) as countComments,round(AVG(rate),1) as rate FROM core_comments as c where c.resourceTypeId=1 " +
            "and c.reviewStatus='pass' and c.resourceOwnerId in(select id from shop_products where storeId=#{storeId})")
    UserComments CountComments(@Param("storeId") long storeId);

    @Select("SELECT u.id as userId ,u.userName,u.avatarUrl,c.contentTxt,c.heartCount,c.createTime,p.name as resourceName,c.resourceOwnerId,c.resourceTypeId,c.rate,ce.commentId,ce.type,ce.dataId,ce.dataContent" +
            "     FROM  (select * from core_comments WHERE resourceTypeId=1  or resourceTypeId=2 and isOwner=1) as c," +
            "core_comment_extraDatas as ce,core_users as u,shop_products as p" +
            "            WHERE   p.storeId=#{storeId} and c.resourceOwnerId =p.id and c.userId=u.id and ce.commentId=c.id  order by c.createTime desc")
    List<ShopCommentsExtraDatas> commentList(@Param("storeId") long storeId);

    //用户发布评论
    @Insert("insert into core_comments (id,userId,isOwner,contentTxt,resourceOwnerId,resourceTypeId,rate,reviewStatus,originContentTxt) " +
            "values(#{id},#{userId},1,#{input.contentTxt},#{input.resourceOwnerId},#{input.resourceTypeId},#{input.rate},'reviewing',#{input.contentTxt})")
    Long publishComment(@Param("id") long id, @Param("userId") long userId, @Param("input") CommentInput input);

    //插入评论额外资源
    @Insert("insert into core_comment_extraDatas (id,type,commentId,dataId,dataContent) " +
            "values(#{id},#{input.type},#{commentId},#{dataId},#{input.dataContent})")
    Long commentExtraDatas(@Param("id") long id, @Param("commentId") long commentId, @Param("dataId") long dataId, @Param("input") CommentInput input);


}
