package com.zwdbj.server.mobileapi.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分类(平台所有的分类)")
public class CategoryDto {
    @ApiModelProperty("id")
    long id;
    @ApiModelProperty("分类名")
    String name;
    @ApiModelProperty("是否还有子分类。isHaveNextNode==true?'该分类下有子分类':'该分类下没有子分类'")
    boolean isHaveNextNode;
    @ApiModelProperty("排序")
    int orderIndex;
    @ApiModelProperty("分类Id")
    int type;
    @ApiModelProperty("父节点")
    //默认为0
            long parentId;
    @ApiModelProperty("分类创建者")
    //如果是系统内置分类，此字段保持为null
            long userId;
    @ApiModelProperty("")
    String iconUrl;
    @ApiModelProperty("状态")
    //0：正常 1:下线
            int status;

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
