package com.zwdbj.server.mobileapi.service.favorite.service;

import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteDto;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteInput;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteModel;
import com.zwdbj.server.mobileapi.service.favorite.model.SearchFavorite;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface FavoriteService {

    /**
     * 添加收藏
     *
     * @param favoriteInput
     * @return
     */
    ServiceStatusInfo<FavoriteDto> favorite(FavoriteInput favoriteInput);

    /**
     * 删除收藏
     *
     * @param id
     * @return
     */
    ServiceStatusInfo<Long> deleteFavorite(long id);

    /**
     * 分页查询
     *
     * @param searchFavorite
     * @return
     */
    ServiceStatusInfo<List<FavoriteModel>> searchFavorite(SearchFavorite searchFavorite);

    /**
     * 查询我的收藏数量
     */
    int getUserFavoriteNum(long userId);

    int isFavorite(long userId, long targetId, String targetType);

}
