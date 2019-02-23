package com.zwdbj.server.mobileapi.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询分类")
public class CategorySearchInput {
    @ApiModelProperty(value = "0:宠物分类。1：线下商家服务分类2：商城产品分类")
    int type;
    @ApiModelProperty(value = "查询此分类节点下的子分类，如果从根节点查询，此字段传0")
    long parentId;


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
}
