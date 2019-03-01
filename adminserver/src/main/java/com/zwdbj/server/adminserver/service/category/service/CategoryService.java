package com.zwdbj.server.adminserver.service.category.service;

import com.zwdbj.server.adminserver.config.MainKeyType;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreService;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.category.mapper.ICategoryMapper;
import com.zwdbj.server.adminserver.service.category.model.*;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    ICategoryMapper categoryMapper;
    @Autowired
    QiniuService qiniuService;
    @Autowired
    RedisTemplate redisTemplate;

    public List<CategoryDto> search(CategorySearchInput input) {
        List<CategoryDto> dtos = this.categoryMapper.search(input);
        return dtos;
    }

    public List<AdBasicCategoryDto> basicCompalinAd(AdBasicCategoryInput input) {
        List<AdBasicCategoryDto> categoryDtos = this.categoryMapper.basicCompalinAd(input);
        return categoryDtos;
    }

    @Transactional
    public ServiceStatusInfo<Long> addCategoryAd(Long parentId, AdNewCategoryInput input) {
        //TODO 检查title是否在系统存在

        Long id = UniqueIDCreater.generateID();

        Long result = 0L;
        try {
            if (input.getIconUrl() != null && input.getIconUrl().length() != 0)
                input.setIconUrl(qiniuService.url(input.getIconUrl()));
            if (parentId == null) {
                result = this.categoryMapper.addCategoryAd(id, input);
            } else if (parentId != null) {
                result = this.categoryMapper.addCategoryAd2(id, input, parentId);
            }
            //清空首页缓存
            redisTemplate.delete(MainKeyType.MAINCATEGORY);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建失败" + e.getMessage(), result);
        }
    }


    @Transactional
    public ServiceStatusInfo<Long> editCategoryAd(Long id, AdNewCategoryNameInput input) {
        //TODO 检查title是否在系统存在
        Long result = 0L;
        try {
            if (input.getIconUrl() != null && input.getIconUrl().length() != 0)
                input.setIconUrl(qiniuService.url(input.getIconUrl()));
            result = this.categoryMapper.editCategoryAd(id, input);
            redisTemplate.delete(MainKeyType.MAINCATEGORY);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改失败" + e.getMessage(), result);
        }
    }

    public List<AdBasicCategoryDto> categoryDetailsAd(Long id) {
        List<AdBasicCategoryDto> categoryDtos = this.categoryMapper.categoryDetailsAd(id);
        return categoryDtos;
    }

    @Transactional
    public ServiceStatusInfo<Long> delCategoryAd(Long id) {
        Long result = 0L;
        try {
            result = this.categoryMapper.delCategoryAd(id);
            redisTemplate.delete(MainKeyType.MAINCATEGORY);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改失败" + e.getMessage(), result);
        }
    }


    public ServiceStatusInfo<List<StoreServiceCategory>> searchCategory(List<Long> list) {
        List<StoreServiceCategory> result = new ArrayList<>();
        try {
            for (Long id : list) {
                StoreServiceCategory storeServiceCategory = categoryMapper.searchCategory(id);
                if (storeServiceCategory != null) {
                    result.add(storeServiceCategory);
                }

            }
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询分类失败" + e.getMessage(), null);
        }
    }

    public ServiceStatusInfo<List<StoreServiceCategory>> allExtraService() {
        try {
            List<StoreServiceCategory> result = categoryMapper.allExtraService();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询所有商家额外服务分类失败" + e.getMessage(), null);
        }
    }

    public ServiceStatusInfo<List<StoreServiceCategory>> allServiceScopes() {
        try {
            List<StoreServiceCategory> result = categoryMapper.allServiceScopes();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询所有商家服务分类失败" + e.getMessage(), null);
        }
    }
}
