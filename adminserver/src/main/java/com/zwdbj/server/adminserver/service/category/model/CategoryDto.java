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
