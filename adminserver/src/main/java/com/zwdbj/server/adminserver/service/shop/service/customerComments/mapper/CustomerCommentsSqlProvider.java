package com.zwdbj.server.adminserver.service.shop.service.customerComments.mapper;

import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.SearchInfo;
import org.apache.ibatis.jdbc.SQL;

public class CustomerCommentsSqlProvider {
    public String getCustomerComments(SearchInfo info) {
        SQL sql = new SQL()
                .SELECT("core_comments c");
        sql.LEFT_OUTER_JOIN("core_comment_extraDatas ce on ce.commentId=c.id ", "core_users u ON u.id=c.userId");
        if (info.getUsername()!=null&&info.getUsername().length()>0) {
            sql.SET("`c.username` like "+"%"+"#{info.username}"+"%");
        }
        if(info.getStartDate()!=null){
            sql.SET("`c.createTime`>={info.startDate}");
        }
        if(info.getEndDate()!=null){
            sql.SET("`c.createTime`<={info.endDate}");
        }
        sql.WHERE("`c.resourceOwnerId` in (select `p.id` from shop_products as p," +
                "shop_stores as s where `p.storeId`=`s.id` and `s.legalSubjectId`=#{legalSubjectId}) ");
        return sql.toString();
    }
}
