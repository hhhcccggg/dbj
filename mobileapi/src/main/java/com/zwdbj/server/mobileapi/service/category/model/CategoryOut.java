package com.zwdbj.server.mobileapi.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "分类")
public class CategoryOut implements Serializable {
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
}
