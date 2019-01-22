package com.zwdbj.server.mobileapi.service.favorite.mapper;

import com.zwdbj.server.mobileapi.service.favorite.model.SearchFavorite;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class FavoriteSqlProvider {

    public String searchFavorite(Map map){
        SearchFavorite searchFavorite = (SearchFavorite) map.get("searchFavorite");
        SQL sql = new SQL().SELECT("id","targetId","targetType","title","imageUrl","price");
        sql.FROM("shop_Favorites");
        if(searchFavorite.getTargetType() != null){
            sql.WHERE("targetType=#{searchFavorite.targetType}") ;
        }
        if(searchFavorite.getUserId()>0){
            sql.WHERE("userId=#{searchFavorite.userId}") ;
        }
        sql.ORDER_BY("createTime desc");
        return sql.toString();
    }
}
