package com.zwdbj.server.mobileapi.service.appHome.model;

import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerDto;
import com.zwdbj.server.mobileapi.service.category.model.CategoryMainDto;
import com.zwdbj.server.mobileapi.service.category.model.CategoryRecommendDto;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductMainDto;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class AppHomeResDto implements Serializable {
    @ApiModelProperty(value = "app首页运营banner返回值")
    private List<AdBannerDto> adBannerDtos1;
    @ApiModelProperty(value = "app首页金币任务banner返回值")
    private List<AdBannerDto> adBannerDtos2;
    @ApiModelProperty(value = "app首页优惠折扣banner返回值")
    private List<AdBannerDto> adBannerDtos3;
    @ApiModelProperty(value = "APP首页分类返回值")
    private CategoryMainDto categoryMainDto;
    @ApiModelProperty(value = "APP首页兑换商城返回值")
    private List<ProductMainDto> productMainDtos;
    @ApiModelProperty(value = "APP首页为你推荐的返回分类")
    private List<CategoryRecommendDto> categoryRecommendDtos;

    public List<CategoryRecommendDto> getCategoryRecommendDtos() {
        return categoryRecommendDtos;
    }

    public void setCategoryRecommendDtos(List<CategoryRecommendDto> categoryRecommendDtos) {
        this.categoryRecommendDtos = categoryRecommendDtos;
    }

    public List<AdBannerDto> getAdBannerDtos1() {
        return adBannerDtos1;
    }

    public void setAdBannerDtos1(List<AdBannerDto> adBannerDtos1) {
        this.adBannerDtos1 = adBannerDtos1;
    }

    public List<AdBannerDto> getAdBannerDtos2() {
        return adBannerDtos2;
    }

    public void setAdBannerDtos2(List<AdBannerDto> adBannerDtos2) {
        this.adBannerDtos2 = adBannerDtos2;
    }

    public List<AdBannerDto> getAdBannerDtos3() {
        return adBannerDtos3;
    }

    public void setAdBannerDtos3(List<AdBannerDto> adBannerDtos3) {
        this.adBannerDtos3 = adBannerDtos3;
    }

    public CategoryMainDto getCategoryMainDtos() {
        return categoryMainDto;
    }

    public void setCategoryMainDtos(CategoryMainDto categoryMainDtos) {
        this.categoryMainDto = categoryMainDtos;
    }

    public List<ProductMainDto> getProductMainDtos() {
        return productMainDtos;
    }

    public void setProductMainDtos(List<ProductMainDto> productMainDtos) {
        this.productMainDtos = productMainDtos;
    }
}
