package com.zwdbj.server.mobileapi.service.favorite.mapper;

import com.zwdbj.server.mobileapi.service.favorite.model.SearchFavorite;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class FavoriteSqlProvider {

    public String searchFavorite(Map map){
        SearchFavorite searchFavorite = (SearchFavorite) map.get("searchFavorite");
        SQL sql = new SQL().SELECT("id","targetId","targetType","title","imageUrl","price",
                "(select ifnull(count(id),0) from shop_Favorites f where f.isDeleted=0 and  " +
                        "f.targetType=sf.targetType and f.targetId = sf.targetId) as amount");
        sql.FROM("shop_Favorites as sf");
        if(searchFavorite.getTargetType() != null){
            sql.WHERE("targetType=#{searchFavorite.targetType}") ;
        }
        if(searchFavorite.getUserId()>0){
            sql.WHERE("userId=#{searchFavorite.userId}") ;
        }
        sql.WHERE("isDeleted=0");
        sql.ORDER_BY("createTime desc");
        return sql.toString();
    }
}
