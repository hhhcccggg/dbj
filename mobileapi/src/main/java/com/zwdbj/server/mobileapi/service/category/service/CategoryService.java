package com.zwdbj.server.mobileapi.service.category.service;

import com.zwdbj.server.mobileapi.config.MainKeyType;
import com.zwdbj.server.mobileapi.service.category.mapper.ICategoryMapper;
import com.zwdbj.server.mobileapi.service.category.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ServiceStatusInfo<CategoryMainDto> mainSelect(){
        try{
            CategoryMainDto categoryMainDto = new CategoryMainDto();
            if(redisTemplate.hasKey(MainKeyType.MAINCATEGORY)){
               try{
                   categoryMainDto = (CategoryMainDto) redisTemplate.opsForValue().get(MainKeyType.MAINCATEGORY);
                   return new ServiceStatusInfo<>(0,"",categoryMainDto);
               }catch (Exception e){}
            }
            List<CategoryOut> categoryOuts = this.categoryMapper.mainSelect(0);
            if(categoryOuts != null && categoryOuts.size()>0){
                categoryMainDto.setCategoryOneOut(categoryOuts);
            }
            List<CategoryOut> list = new ArrayList<>();
            for (CategoryOut categoryOut: categoryMainDto.getCategoryOneOut()) {
                list.addAll(categoryMapper.mainSelect(categoryOut.getId()));
            }
            if(list != null && list.size()>0){
                categoryMainDto.setCategoryTwoOut(list);
            }
            redisTemplate.opsForValue().set(MainKeyType.MAINCATEGORY,categoryMainDto);
            return new ServiceStatusInfo<>(0,"",categoryMainDto);
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
