package com.zwdbj.server.adminserver.service.shop.service.shopdetail.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class ShopDetailSql {

    public String modifyStoreImage(Map map) {
        String imageType = (String) map.get("imageType");
        String imageUrl = (String) map.get("imageUrl");
        long legalSubjectId = (long) map.get("legalSubjectId");
        SQL sql = new SQL();
        sql.UPDATE("shop_stores");
        if ("logoUrl".equals(imageType)) {
            sql.SET("logoUrl='" + imageUrl + "'");
        } else if ("mainConverImage".equals(imageType)) {
            sql.SET("mainConverImage='" + imageUrl + "'");
        } else if ("coverImages".equals(imageType)) {
            sql.SET("coverImages='" + imageUrl + "'");
        } else {
            throw new RuntimeException("参数异常");
        }
        sql.WHERE("legalSubjectId=" + legalSubjectId, "isDeleted=0");
        return sql.toString();
    }
}
