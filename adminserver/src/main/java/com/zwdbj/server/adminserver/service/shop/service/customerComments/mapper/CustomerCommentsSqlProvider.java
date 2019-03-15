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
                        "c.createTime,c.resourceOwnerId,ce.type,ce.dataId,ce.dataContent")
                .FROM("core_comments as c")
                .LEFT_OUTER_JOIN("core_comment_extraDatas ce ON ce.commentId=c.id ")
                .LEFT_OUTER_JOIN("core_users u ON u.id=c.userId")
                .WHERE("c.resourceOwnerId in (select p.id from shop_products as p," +
                        "shop_stores as s where p.storeId=s.id and s.legalSubjectId="+legalSubjectId+")");
        if (searchInfo.getUsername()!=null&&searchInfo.getUsername().length()>0) {
            sql.WHERE("c.username like "+"%"+searchInfo.getUsername()+"%");
        }
        if(searchInfo.getStartDate()!=null){
            sql.WHERE("c.createTime>=+"+searchInfo.getStartDate());
        }
        if(searchInfo.getEndDate()!=null){
            sql.WHERE("c.createTime<="+searchInfo.getEndDate());
        }
        sql.ORDER_BY("createTime desc");
        return sql.toString();
    }
}
