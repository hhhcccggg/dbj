package com.zwdbj.server.adminserver.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分类(平台所有的分类)")
public class CategoryDto {
        @ApiModelProperty("分类ID")
        long id;
        @ApiModelProperty("分类名")
        String name;
        @ApiModelProperty("是否还有子分类。isHaveNextNode==true?'该分类下有子分类':'该分类下没有子分类'")
        boolean isHaveNextNode;
        @ApiModelProperty("iconUrl")
        String iconUrl;
        @ApiModelProperty("状态,0:正常 1：下线")
        int status;
        @ApiModelProperty("排序")
        int orderIndex;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIconUrl() {
        return iconUrl;
    }


    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public boolean isHaveNextNode() {
        return isHaveNextNode;
    }

    public void setHaveNextNode(boolean haveNextNode) {
        isHaveNextNode = haveNextNode;
    }
}
