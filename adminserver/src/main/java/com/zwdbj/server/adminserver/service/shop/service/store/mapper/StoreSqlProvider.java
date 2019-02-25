package com.zwdbj.server.adminserver.service.shop.service.store.mapper;

import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreSearchInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class StoreSqlProvider {

    public String searchStore(Map param){
        StoreSearchInput input = (StoreSearchInput)param.get("input");
        SQL sql = new SQL().SELECT("s.*")
                .FROM("shop_stores s")
                .WHERE("s.isDeleted=0");
        if (input.getReviewed()!=0){
            if (input.getReviewed()==1){
                sql.WHERE("s.reviewed=true");
            }else if (input.getReviewed()==2){
                sql.WHERE("s.reviewed=false");
            }
        }
        if (input.getStopService()!=0){
            if (input.getStopService()==1){
                sql.WHERE("s.stopService=true");
            }else if (input.getStopService()==2){
                sql.WHERE("s.stopService=false");
            }
        }
        if (input.getStatus()!=-1){
            sql.WHERE("s.`status`=#{input.status}");
        }
        if (input.getKeyWords()!=null && input.getKeyWords().length()>0){
            sql.WHERE(String.format("s.name like '%s'",("%"+input.getKeyWords()+"%")));
        }
        sql.ORDER_BY("s.createTime desc");
        return sql.toString();
    }
}
