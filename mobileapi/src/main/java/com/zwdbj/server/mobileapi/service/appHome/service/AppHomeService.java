package com.zwdbj.server.mobileapi.service.appHome.service;

import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerDto;
import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerInput;
import com.zwdbj.server.mobileapi.service.adBanner.service.AdBannerService;
import com.zwdbj.server.mobileapi.service.appHome.model.AppHomeInput;
import com.zwdbj.server.mobileapi.service.appHome.model.AppHomeResDto;
import com.zwdbj.server.mobileapi.service.category.model.CategoryMainDto;
import com.zwdbj.server.mobileapi.service.category.model.CategoryRecommendDto;
import com.zwdbj.server.mobileapi.service.category.service.CategoryService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductMainDto;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service.ProductService;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppHomeService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AdBannerService adBannerServiceImpl;
    @Autowired
    private ProductService productServiceImpl;

    public ServiceStatusInfo<AppHomeResDto> allHome(AppHomeInput input){
        try {
            AppHomeResDto dto = new AppHomeResDto();
            AdBannerInput adBannerInput1 = new AdBannerInput();
            adBannerInput1.setPlatform("ALL");
            adBannerInput1.setType("APP_O2O_HOME");
            List<AdBannerDto> adBannerDtos1 =  this.adBannerServiceImpl.searchAdBanner(adBannerInput1).getData();
            if (adBannerDtos1!=null)dto.setAdBannerDtos1(adBannerDtos1);
            AdBannerInput adBannerInput2 = new AdBannerInput();
            adBannerInput2.setPlatform("ALL");
            adBannerInput2.setType("COIN_TASK_APP");
            List<AdBannerDto> adBannerDtos2 =  this.adBannerServiceImpl.searchAdBanner(adBannerInput2).getData();
            if (adBannerDtos2!=null)dto.setAdBannerDtos2(adBannerDtos2);
            AdBannerInput adBannerInput3 = new AdBannerInput();
            adBannerInput3.setPlatform("ALL");
            adBannerInput3.setType("DISCOUNT_APP");
            List<AdBannerDto> adBannerDtos3 =  this.adBannerServiceImpl.searchAdBanner(adBannerInput3).getData();
            if (adBannerDtos3!=null)dto.setAdBannerDtos3(adBannerDtos3);
            CategoryMainDto categoryMainDtos = this.categoryService.mainSelect().getData();
            if (categoryMainDtos!=null)dto.setCategoryMainDtos(categoryMainDtos);
            List<ProductMainDto> productMainDtos = this.productServiceImpl.mainProduct().getData();
            if (productMainDtos!=null)dto.setProductMainDtos(productMainDtos);
            List<CategoryRecommendDto> categoryRecommendDtos = this.categoryService.categoryRecommends().getData();
            if (categoryRecommendDtos!=null)dto.setCategoryRecommendDtos(categoryRecommendDtos);
            return new ServiceStatusInfo<>(0,"",dto);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,e.getMessage(),null);
        }

    }
}
