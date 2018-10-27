package com.zwdbj.server.mobileapi.service.category.service;

import com.zwdbj.server.mobileapi.service.category.mapper.ICategoryMapper;
import com.zwdbj.server.mobileapi.service.category.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    ICategoryMapper categoryMapper;
    public List<CategoryDto> search(CategorySearchInput input) {
        List<CategoryDto> dtos = this.categoryMapper.search(input);
        return dtos;
    }
}
