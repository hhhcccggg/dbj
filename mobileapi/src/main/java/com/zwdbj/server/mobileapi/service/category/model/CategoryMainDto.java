package com.zwdbj.server.mobileapi.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "主页分类对象")
public class CategoryMainDto {

    @ApiModelProperty("分类ID")
    long id;
    @ApiModelProperty("分类名")
    String name;
    @ApiModelProperty("父级ID")
    String parentId;
    @ApiModelProperty(value = "iconUrl")
    String iconUrl;
    @ApiModelProperty(value = "排序")
    int orderIndex;
    @ApiModelProperty("子分类")
    List<CategoryMainDto> categoryMainDtoList;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public List<CategoryMainDto> getCategoryMainDtoList() {
        return categoryMainDtoList;
    }

    public void setCategoryMainDtoList(List<CategoryMainDto> categoryMainDtoList) {
        this.categoryMainDtoList = categoryMainDtoList;
    }
}
