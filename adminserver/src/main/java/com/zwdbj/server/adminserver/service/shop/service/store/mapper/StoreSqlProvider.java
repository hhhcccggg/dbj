package com.zwdbj.server.adminserver.service.shop.service.store.mapper;

import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSearchInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class StoreSqlProvider {

    public String searchStore(Map param){
        StoreSearchInput input = (StoreSearchInput)param.get("input");
        SQL sql = new SQL().SELECT("*")
                .FROM("shop_stores s")
                .WHERE("isDeleted=0");
        if (input.getReviewed()!=0){
            if (input.getReviewed()==1){
                sql.WHERE("reviewed=true");
            }else if (input.getReviewed()==2){
                sql.WHERE("reviewed=false");
            }
        }
        if (input.getStopService()!=0){
            if (input.getStopService()==1){
                sql.WHERE("stopService=true");
            }else if (input.getStopService()==2){
                sql.WHERE("stopService=false");
            }
        }
        if (input.getStatus()!=-1){
            sql.WHERE("`status`=#{input.status}");
        }
        if (input.getKeyWords()!=null && input.getKeyWords().length()>0){
            sql.WHERE(String.format("title like '%s'",("%"+input.getKeyWords()+"%")));
        }
        sql.ORDER_BY("createTime desc");
        return sql.toString();
    }
}
