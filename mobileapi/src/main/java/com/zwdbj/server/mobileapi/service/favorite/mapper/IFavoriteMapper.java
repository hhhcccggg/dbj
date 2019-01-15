package com.zwdbj.server.mobileapi.service.favorite.mapper;

import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteInput;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteModel;
import com.zwdbj.server.mobileapi.service.favorite.model.SearchFavorite;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IFavoriteMapper {

    /**
     * 新增
     * @param favoriteInput
     * @return
     */
    @Insert("INSERT INTO `shop_favorites` (" +
            "`id`,`targetId`,`targetType`,`userId`,`title`,`imageUrl`,`price`)" +
            "VALUES" +
            "(" +
            "#{id},#{favoriteInput.targetId},#{favoriteInput.targetType},#{favoriteInput.userId},#{favoriteInput.title},#{favoriteInput.imageUrl},#{favoriteInput.price});")
    long addFavorite(@Param("id") long id,@Param("favoriteInput") FavoriteInput favoriteInput);

    /**
     * 删除
     * @param id
     * @param userId
     * @return
     */
    @Update("UPDATE `shop_favorites` " +
            "SET " +
            " `isDeleted` = 1," +
            " `deleteTime` = now()" +
            " WHERE (`id` = #{id} and userId=#{userId})")
    long deleteFavoriteById(@Param("id")long id,@Param("userId") long userId);

    /**
     * 查询
     * @param searchFavorite
     * @return
     */
    @SelectProvider(type =FavoriteSqlProvider.class,method = "searchFavorite")
    List<FavoriteModel> searchFavorite(@Param("searchFavorite") SearchFavorite searchFavorite);
}