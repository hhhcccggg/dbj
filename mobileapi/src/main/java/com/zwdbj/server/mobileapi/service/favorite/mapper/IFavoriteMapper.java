package com.zwdbj.server.mobileapi.service.favorite.mapper;

import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteDto;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteInput;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteModel;
import com.zwdbj.server.mobileapi.service.favorite.model.SearchFavorite;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IFavoriteMapper {

    /**
     * 新增
     * @return
     */
    @Insert("INSERT INTO `shop_Favorites` (" +
            "`id`,`targetId`,`targetType`,`userId`,`title`,`imageUrl`,`price`)" +
            "VALUES(#{id},#{targetId},'STORE',#{userId},#{title},#{imageUrl},0)")
    long addFavorite(@Param("id") long id,@Param("targetId") long  targetId,@Param("userId")long userId,@Param("title")String name,@Param("imageUrl")String imageUrl);

    @Select("select * from shop_Favorites where userId=#{userId} and targetId=#{targetId} and isDeleted=0")
    FavoriteModel findFavoriteByUserIdByStoreId(@Param("targetId")long targetId,@Param("userId")long userId);

    /**
     * 删除
     * @param id
     * @param userId
     * @return
     */
    @Delete("delete from  `shop_Favorites` " +
            " WHERE (`id` = #{id} and userId=#{userId})")
    long deleteFavoriteById(@Param("id")long id,@Param("userId") long userId);

    /**
     * 查询
     * @param searchFavorite
     * @return
     */
    @SelectProvider(type =FavoriteSqlProvider.class,method = "searchFavorite")
    List<FavoriteModel> searchFavorite(@Param("searchFavorite") SearchFavorite searchFavorite);

    /**
     * 查询用户的收藏数量
     */

    @Select("select count(id) from shop_Favorites where userId=#{userId} and isDeleted=0")
    int getUserFavoriteNum(@Param("userId")long userId);

    /**
     * 查询用户是否收藏
     */
    @Select("select count(*) from shop_Favorites where userId=#{userId} and targetId=#{targetId} and targetType=#{targetType}")
    int isFavorite(@Param("userId")long userId,@Param("targetId")long targetId,@Param("targetType")String targetType );

    /**
     * 取消收藏
     * @return
     */
    @Delete("delete  from `shop_Favorites` " +
            " WHERE userId=#{userId} and targetType = #{targetType} and targetId = #{targetId} ")
    long deleteFavoriteByUserIdByTargetId(@Param("targetId")long  targetId,@Param("userId")long usrId,@Param("targetType")String targetType);
}
