package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model.SearchStaffInfo;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class OfflineStoreStaffsSqlProvide {

    public String cancelRepresent(Map map) {
        long[] id = (long[]) map.get("id");
        long storeId = (long) map.get("storeId");
        SQL sql = new SQL().UPDATE("o2o_offlineStoreStaffs").SET("isDeleted=1").SET("deleteTime=now()");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < id.length; i++) {
            stringBuffer.append("userId=" + id[i]);
            if (i + 1 != id.length) stringBuffer.append(" or ");
        }
        sql.WHERE(stringBuffer.toString()).WHERE("storeId=" + storeId).WHERE("isDeleted=0");
        return sql.toString();
    }

    public String setRepresent(Map map) {
        long[] id = (long[]) map.get("id");
        long storeId = (long) map.get("storeId");
        StringBuffer sql = new StringBuffer("INSERT INTO o2o_offlineStoreStaffs(id,storeId,userId)VALUES");

        for (int i = 0; i < id.length; i++) {
            sql.append("(" + UniqueIDCreater.generateID() + "," + storeId + "," + id[i] + ")");
            if (i < id.length - 1) {
                sql.append(",");
            }
        }
        return sql.toString();
    }

    public String getSuperStarDetail(Map map) {
        String search = (String) map.get("search");
        String rank = (String) map.get("rank");
        String sort = (String) map.get("sort");
        long legalSubjectId = (long) map.get("legalSubjectId");
        StringBuffer sql = new StringBuffer("select u.id as userId,u.fullName,u.avatarUrl,u.phone,(select count(*) from core_videos where userId=u.id) as videos, " +
                "u.totalHearts,u.totalFans,u.isLocked,(select count(*) from core_pets where userId=u.id) as pets " +
                "from core_users as u,core_user_tenants as t,o2o_offlineStoreStaffs as oss  where u.tenantId=t.id and u.id=oss.userId  and t.legalSubjectId=" + legalSubjectId +
                " and u.isDeleted=0 and t.isDeleted=0 and oss.isDeleted=0 ");
        if (!"".equals(search.trim()) && search != null) {
            sql.append(" and (u.nickName like '%" + search + "%' or u.phone like '%" + search + "%')");
        }
        if ("videos".equals(rank) || "totalHearts".equals(rank) || "totalFans".equals(rank)) {
            sql.append(" order by u." + rank);
        } else {
            sql.append(" order by u.fullName");
        }
        if ("asc".equals(sort)) {
            sql.append(" " + sort);
        } else {
            sql.append(" desc");
        }
        return sql.toString();

    }

    public String searchStaffs(Map map) {
        SearchStaffInfo searchStaffInfo = (SearchStaffInfo) map.get("searchStaffInfo");
        long storeId = (long)map.get("storeId");
        String search = searchStaffInfo.getSearch();
        long legalSubjectId = (long) map.get("legalSubjectId");
        StringBuffer stringBuffer = new StringBuffer("SELECT u.fullName,u.phone,u.id,u.createTime,u.tenantId,u.notes FROM core_users as u, " +
                "( SELECT id from core_user_tenants  where isDeleted=0 and legalSubjectId=" + legalSubjectId + " ) as t " +
                "WHERE u.tenantId=t.id and u.isDeleted=0");
        if (search != null && !"".equals(search.trim())){
            stringBuffer.append(" and u.fullName like '%" + search + "%' or u.phone like '%" + search + "%'");
        }

        if(searchStaffInfo.getRange() == 1){
            stringBuffer.append(" HAVING (select count(1) from o2o_offlineStoreStaffs where isDeleted=0 and userId=u.id and storeId="+storeId+") = 0");
        }
        stringBuffer.append(" order by u.fullName");
        return new StringBuffer("select * from ("+stringBuffer.toString()+") as cst").toString();
    }

    public String searchSuperStars(Map map) {
        String search = (String) map.get("search");
        long storeId = (long) map.get("storeId");
        StringBuffer stringBuffer = new StringBuffer("SELECT u.fullName,u.phone,u.id,u.createTime,u.tenantId,u.notes from core_users as u,(select userId from o2o_offlineStoreStaffs where storeId=" + storeId + " and isDeleted=0 ) as s " +
                " where u.id=s.userId  and u.isDeleted=0");
        if ("".equals(search) || search == null) {
            stringBuffer.append(" order by u.fullName");

        } else {
            stringBuffer.append(" and u.fullName like '%" + search + "%' or u.phone like '%" + search + "%' order by u.fullName");
        }

        return stringBuffer.toString();
    }
}
