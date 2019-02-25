package com.zwdbj.server.adminserver.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "新建分类输入字段")
public class AdNewCategoryInput {
    @ApiModelProperty(value = "分类的名称")
    String name;
    //0:宠物分类
    @ApiModelProperty(value = "分类类型 分类,-1:全部,0:宠物分类，1：线下商家服务分类2：商城产品分类")
    int type;
    @ApiModelProperty("iconUrl")
    String iconUrl;
    @ApiModelProperty("状态 0:正常 1：下线")
    int status;
    @ApiModelProperty("排序")
    int orderIndex;
    @ApiModelProperty("父节点，默认为0")
    long parentId;

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
