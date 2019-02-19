package com.zwdbj.server.mobileapi.service.category.service;

import com.zwdbj.server.mobileapi.config.MainKeyType;
import com.zwdbj.server.mobileapi.service.category.mapper.ICategoryMapper;
import com.zwdbj.server.mobileapi.service.category.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {
    @Autowired
    ICategoryMapper categoryMapper;

    @Autowired
    RedisTemplate redisTemplate;

    public List<CategoryDto> search(CategorySearchInput input) {
        List<CategoryDto> dtos = this.categoryMapper.search(input);
        return dtos;
    }

    public ServiceStatusInfo<List<CategoryMainDto>> mainSelect(){
        try{
            List<CategoryMainDto> categoryMainDtos;
            if(redisTemplate.hasKey(MainKeyType.MAINCATEGORY)){
                categoryMainDtos = (List<CategoryMainDto>) redisTemplate.opsForValue().get(MainKeyType.MAINCATEGORY);
                return new ServiceStatusInfo<>(0,"",categoryMainDtos);
            }
            categoryMainDtos = this.categoryMapper.mainSelect(0);
            for (CategoryMainDto categoryMainDto: categoryMainDtos) {
                List<CategoryMainDto> list = categoryMapper.mainSelect(categoryMainDto.getId());
                if(list!=null){
                    categoryMainDto.setCategoryMainDtoList(list);
                }

            }
            redisTemplate.opsForValue().set(MainKeyType.MAINCATEGORY,categoryMainDtos);
            return new ServiceStatusInfo<>(0,"",categoryMainDtos);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,e.getMessage(),null);
        }
    }


    public ServiceStatusInfo<List<CategoryRecommendDto>> categoryRecommends(){
        try {
            List<CategoryRecommendDto> categoryRecommendDtos = this.categoryMapper.categoryRecommends();
            return new ServiceStatusInfo<>(0,"",categoryRecommendDtos);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,e.getMessage(),null);
        }
    }


}
