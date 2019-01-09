package com.zwdbj.server.mobileapi.service.shop.comments.mapper;

import com.zwdbj.server.mobileapi.service.shop.comments.model.ShopCommentsExtraDatas;
import com.zwdbj.server.mobileapi.service.shop.comments.model.UserComments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopCommentsMapper {

    @Select("SELECT count(*) as countComments,AVG(rate) as rate FROM core_comments as c " +
            "where c.resourceTypeId=1 and c.reviewStatus='pass' and c.resourceOwnerId in(select id from shop_discountCoupons where storeId=1 )")
    UserComments CountComments(@Param("storeId") long storeId);

    @Select("SELECT u.id as userId ,u.userName,u.avatarUrl,c.contentTxt,c.heartCount,c.createTime,d.name as resourceName," +
            "c.resourceOwnerId,c.resourceTypeId,c.rate,ce.commentId,ce.type,ce.dataId,ce.dataContent " +
            "FROM core_comments as c,core_comment_extraDatas as ce,core_users as u,shop_discountCoupons as d " +
            "WHERE c.resourceOwnerId =d.id and c.userId=u.id and ce.commentId=c.id and d.storeId=1 and c.resourceTypeId=1")
    List<ShopCommentsExtraDatas> commentList(@Param("storeId") long storeId);


}
