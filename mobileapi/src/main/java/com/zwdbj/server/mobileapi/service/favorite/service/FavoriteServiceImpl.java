package com.zwdbj.server.mobileapi.service.favorite.service;

import com.zwdbj.server.mobileapi.service.favorite.common.TargetType;
import com.zwdbj.server.mobileapi.service.favorite.mapper.IFavoriteMapper;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteInput;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteModel;
import com.zwdbj.server.mobileapi.service.favorite.model.SearchFavorite;
import com.zwdbj.server.mobileapi.service.legalSubject.model.LegalSubjectModel;
import com.zwdbj.server.mobileapi.service.legalSubject.service.ILegalSubjectService;
import com.zwdbj.server.mobileapi.service.store.model.StoreModel;
import com.zwdbj.server.mobileapi.service.store.service.StoreService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service.ProductService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.model.ProductSKUs;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.service.ProductSKUsService;
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

    @Autowired
    private ProductService productServiceImpl;

    @Autowired
    private ILegalSubjectService legalSubjectServiceImpl;

    @Autowired
    private StoreService storeServiceImpl;

    @Autowired
    private ProductSKUsService productSKUsServiceImpl;

    @Override
    public ServiceStatusInfo<Long> addFavorite(FavoriteInput favoriteInput) {
        try{
            long userId = JWTUtil.getCurrentId();
            if(userId == 0){
                return new ServiceStatusInfo<>(1,"用户未登录",null);
            }
            if(favoriteInput.getTargetType()== TargetType.LAGALSUBJECT){
                LegalSubjectModel legalSubjectModel = legalSubjectServiceImpl.getLegalSubjectById(favoriteInput.getTargetId()).getData();
                if(legalSubjectModel == null)return new ServiceStatusInfo<>(1,"商家不存在",null);
                favoriteInput.setImageUrl(legalSubjectModel.getLogoUrl());
                favoriteInput.setTitle(legalSubjectModel.getName());
            }
            if(favoriteInput.getTargetType()== TargetType.PRODUCTSKU){
                ProductSKUs productSKUs = productSKUsServiceImpl.selectById(favoriteInput.getTargetId()).getData();
                if(productSKUs == null)return new ServiceStatusInfo<>(1,"商品不存在",null);
                ProductOut productOut = productServiceImpl.selectById(productSKUs.getProductId()).getData();
                if(productOut == null)return new ServiceStatusInfo<>(1,"商品不存在",null);
                favoriteInput.setImageUrl(productOut.getImageUrls());
                favoriteInput.setTitle(productOut.getName());
                favoriteInput.setPrice(productSKUs.getPromotionPrice());
            }
            if(favoriteInput.getTargetType()== TargetType.STORE){
                StoreModel storeModel = storeServiceImpl.selectById(favoriteInput.getTargetId()).getData();
                if(storeModel == null)return new ServiceStatusInfo<>(1,"店铺不存在",null);
                favoriteInput.setImageUrl(storeModel.getMainConverImage());
                favoriteInput.setTitle(storeModel.getName());
            }
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
            if(userId == 0){
                return new ServiceStatusInfo<>(1,"用户未登录",null);
            }
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
            if(userId == 0){
                return new ServiceStatusInfo<>(1,"用户未登录",null);
            }
            searchFavorite.setUserId(userId);
            List<FavoriteModel> favoriteModels = iFavoriteMapper.searchFavorite(searchFavorite);
            return new ServiceStatusInfo<>(0,"",favoriteModels);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }
}
