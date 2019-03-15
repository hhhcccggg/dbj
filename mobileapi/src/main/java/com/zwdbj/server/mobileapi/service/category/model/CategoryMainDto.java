package com.zwdbj.server.mobileapi.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "主页分类对象")
public class CategoryMainDto implements Serializable {

    @ApiModelProperty("一级分类")
    List<CategoryOut> categoryOneOut = new ArrayList<>();

    @ApiModelProperty("二级分类")
    List<CategoryOut> categoryTwoOut = new ArrayList<>();

    public List<CategoryOut> getCategoryOneOut() {
        return categoryOneOut;
    }

    public void setCategoryOneOut(List<CategoryOut> categoryOneOut) {
        this.categoryOneOut = categoryOneOut;
    }

    public List<CategoryOut> getCategoryTwoOut() {
        return categoryTwoOut;
    }

    public void setCategoryTwoOut(List<CategoryOut> categoryTwoOut) {
        this.categoryTwoOut = categoryTwoOut;
    }
}
