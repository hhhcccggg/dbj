package com.zwdbj.server.mobileapi.service.favorite.service;

import com.zwdbj.server.mobileapi.service.favorite.mapper.IFavoriteMapper;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteInput;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteModel;
import com.zwdbj.server.mobileapi.service.favorite.model.SearchFavorite;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private IFavoriteMapper iFavoriteMapper;

    @Override
    public ServiceStatusInfo<Long> addFavorite(FavoriteInput favoriteInput) {
        try{
            long userId = JWTUtil.getCurrentId();
            favoriteInput.setUserId(userId);
            long result = iFavoriteMapper.addFavorite(UniqueIDCreater.generateID(), favoriteInput);
            if(result>0)
                return new ServiceStatusInfo<>(0,"",result);
            return new ServiceStatusInfo<>(1,"新增失败，影响行数"+result,null);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"新增失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteFavorite(long id) {
        try{
            long userId = JWTUtil.getCurrentId();
            long result = iFavoriteMapper.deleteFavoriteById(id,userId);
            if(result>0)
                return new ServiceStatusInfo<>(0,"",result);
            return new ServiceStatusInfo<>(1,"删除失败，影响行数"+result,null);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"删除失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<List<FavoriteModel>> searchFavorite(SearchFavorite searchFavorite) {
        try{
            long userId = JWTUtil.getCurrentId();
            searchFavorite.setUserId(userId);
            List<FavoriteModel> favoriteModels = iFavoriteMapper.searchFavorite(searchFavorite);
            return new ServiceStatusInfo<>(0,"",favoriteModels);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }
}
