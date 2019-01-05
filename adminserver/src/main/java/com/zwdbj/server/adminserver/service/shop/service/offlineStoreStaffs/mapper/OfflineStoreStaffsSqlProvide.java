package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.mapper;

import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.Map;

public class OfflineStoreStaffsSqlProvide {

    public String cancelRepresent(Map map){
        long[] id = (long[]) map.get("id");
        long tenantId= (long) map.get("tenantId");
        SQL sql = new SQL().UPDATE("o2o_offlineStoreStaffs").SET("isDeleted=1").SET("deleteTime=now()");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < id.length; i++) {
            stringBuffer.append("userId="+id[i]);
            if(i+1 != id.length)stringBuffer.append(" or ");
        }
        sql.WHERE(stringBuffer.toString()).WHERE("storeId="+tenantId).WHERE("isDeleted=0");
        return sql.toString();
    }

    public String setRepresent(Map map){
        long[] id = (long[]) map.get("id");
        long tenantId= (long) map.get("tenantId");
        StringBuffer sql = new StringBuffer("INSERT INTO o2o_offlineStoreStaffs(id,storeId,userId)VALUES");

        for (int i = 0; i < id.length; i++) {
            sql.append("("+UniqueIDCreater.generateID()+","+tenantId+","+id[i]+")");
            if (i < id.length - 1) {
                sql.append(",");
            }
        }
        return sql.toString();
    }
}
