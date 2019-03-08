package com.zwdbj.server.mobileapi.service.favorite.service;

import com.zwdbj.server.mobileapi.service.category.service.CategoryService;
import com.zwdbj.server.mobileapi.service.favorite.common.TargetType;
import com.zwdbj.server.mobileapi.service.favorite.mapper.IFavoriteMapper;
import com.zwdbj.server.mobileapi.service.favorite.model.FavoriteDto;
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
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private CategoryService categoryService;

    @Autowired
    private ProductSKUsService productSKUsServiceImpl;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ServiceStatusInfo<FavoriteDto> favorite(FavoriteInput input) {
        try {
            long userId = JWTUtil.getCurrentId();
            if (userId == 0) {
                return new ServiceStatusInfo<>(1, "用户未登录", null);
            }
            StoreModel storeModel = storeServiceImpl.selectById(input.getTargetId()).getData();
            if (storeModel == null) return new ServiceStatusInfo<>(1, "店铺不存在", null);
            FavoriteDto dto = new FavoriteDto();
            dto.setTargetId(input.getTargetId());
            int count = this.iFavoriteMapper.isFavorite(userId,input.getTargetId(),"STORE");
            if (count!=0 && input.isFavorite()){
                dto.setFavorite(true);
                return new ServiceStatusInfo<>(1, "已经收藏此商家"  , dto);
            }
            if (count!=0 && !input.isFavorite()){
                this.iFavoriteMapper.deleteFavoriteByUserIdByTargetId(input.getTargetId(),userId,"STORE");
                dto.setFavorite(false);
                return new ServiceStatusInfo<>(0, "取消收藏此商家成功"  , dto);
            }
            if (input.isFavorite()){
                long id= UniqueIDCreater.generateID();
                iFavoriteMapper.addFavorite(id, input.getTargetId(),userId,storeModel.getName(),storeModel.getMainConverImage());
                dto.setFavorite(true);
                return new ServiceStatusInfo<>(0, "收藏此商家成功"  , dto);
            }else {
                return new ServiceStatusInfo<>(1, "取消收藏此商家失败"  , null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceStatusInfo<>(1, "收藏出现异常:" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteFavorite(long id) {
        try {
            long userId = JWTUtil.getCurrentId();
            if (userId == 0) {
                return new ServiceStatusInfo<>(1, "用户未登录", null);
            }
            long result = iFavoriteMapper.deleteFavoriteById(id, userId);
            if (result > 0)
                return new ServiceStatusInfo<>(0, "", result);
            return new ServiceStatusInfo<>(1, "删除失败，影响行数" + result, null);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<FavoriteModel>> searchFavorite(SearchFavorite searchFavorite,long userId) {
        try {
            searchFavorite.setUserId(userId);
            List<FavoriteModel> favoriteModels = iFavoriteMapper.searchFavorite(searchFavorite);
            if(searchFavorite.getTargetType() == TargetType.STORE){
                for (FavoriteModel favoriteModel : favoriteModels){
                    List<String> list = categoryService.getScopeServices(favoriteModel.getTargetId()).getData();
                    if(list != null && list.size()>0)
                        favoriteModel.setScopeServices(String.join("、",list));
                }
            }
            return new ServiceStatusInfo<>(0, "", favoriteModels);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    @Override
    public int getUserFavoriteNum(long userId) {
        int result = this.iFavoriteMapper.getUserFavoriteNum(userId);
        this.stringRedisTemplate.opsForValue().set("userFavorite" + userId, String.valueOf(result), 3, TimeUnit.MINUTES);
        return result;
    }

    @Override
    public int isFavorite(long userId, long targetId, String targetType) {
        int result = 0;
        result = this.iFavoriteMapper.isFavorite(userId, targetId, targetType);
        return result;
    }

}
