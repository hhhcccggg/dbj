package com.zwdbj.server.adminserver.service.category.service;

import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.category.mapper.ICategoryMapper;
import com.zwdbj.server.adminserver.service.category.model.*;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
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
            input.setIconUrl(qiniuService.url(input.getIconUrl()));
            if (parentId == null) {
                result = this.categoryMapper.addCategoryAd(id, input);
            } else if (parentId != null) {
                result = this.categoryMapper.addCategoryAd2(id, input, parentId);
            }

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
            result = this.categoryMapper.editCategoryAd(id, input);
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
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改失败" + e.getMessage(), result);
        }
    }

    public ServiceStatusInfo<List<StoreServiceCategory>> searchCategory(List<String> list) {
        List<StoreServiceCategory> result = new ArrayList<>();
        try {
            for (String id : list) {
                StoreServiceCategory storeServiceCategory = categoryMapper.searchCategory(Long.parseLong(id));
                result.add(storeServiceCategory);
            }
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询分类失败" + e.getMessage(), null);
        }
    }

    public ServiceStatusInfo<List<StoreServiceCategory>> allExtraService() {
        return null;
    }
}
