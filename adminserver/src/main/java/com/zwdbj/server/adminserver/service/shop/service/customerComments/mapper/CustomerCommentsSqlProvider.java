package com.zwdbj.server.adminserver.service.shop.service.customerComments.mapper;

import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.SearchInfo;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class CustomerCommentsSqlProvider {
    public String getCustomerComments(Map params) {
        Long legalSubjectId = (Long) params.get("legalSubjectId");
        SearchInfo searchInfo =(SearchInfo) params.get("searchInfo");
        SQL sql = new SQL()
                .SELECT("c.id,c.contentTxt,c.rate,c.heartCount,u.username,u.id as userId," +
                        "c.createTime,c.resourceOwnerId,ce.type,ce.dataId,ce.dataContent,v.coverImageUrl")
                .FROM("core_comments as c")
                .LEFT_OUTER_JOIN("core_comment_extraDatas ce ON ce.commentId=c.id ")
                .LEFT_OUTER_JOIN("core_users u ON u.id=c.userId")
                .LEFT_OUTER_JOIN("core_videos v ON v.id=ce.dataId")
                .WHERE("c.resourceOwnerId in (select p.id from shop_products as p," +
                        "shop_stores as s where p.storeId=s.id and s.legalSubjectId=#{legalSubjectId})")
                .WHERE("c.reviewStatus='pass'")
                .WHERE("c.isOwner=1")
                .WHERE("c.resourceTypeId=1");
        if (searchInfo.getUsername()!=null&&searchInfo.getUsername().length()>0) {
            sql.WHERE("c.username like '%#{searchInfo.username}%'");
        }
        if(searchInfo.getStartDate()!=null){
            sql.WHERE("c.createTime>='#{searchInfo.startDate}'");
        }
        if(searchInfo.getEndDate()!=null){
            sql.WHERE("c.createTime<='#{searchInfo.endDate}'");
        }
        sql.ORDER_BY("c.createTime desc");
        return sql.toString();
    }
}
