package com.zwdbj.server.mobileapi.service.category.service;

import com.zwdbj.server.mobileapi.service.category.mapper.ICategoryMapper;
import com.zwdbj.server.mobileapi.service.category.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类管理api接口调整(接口兼容)
 * 所有的分类信息都在一张表里？
 * 分类--宠物，商品，店铺分类--
 * api层只能查询分类信息？
 * 接口兼容？确保以后增加分类以后现在的业务依旧能够支持？
 *
 *
 */
@Service
public class CategoryService {
    @Autowired
    ICategoryMapper categoryMapper;

    public List<CategoryDto> search(CategorySearchInput input) {
        List<CategoryDto> dtos = this.categoryMapper.search(input);
        return dtos;
    }



}
