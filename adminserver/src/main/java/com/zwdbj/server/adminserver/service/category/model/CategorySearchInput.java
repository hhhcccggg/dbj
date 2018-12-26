package com.zwdbj.server.adminserver.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询分类")
public class CategorySearchInput {
    @ApiModelProperty(value = "0:宠物分类。更多类型以后扩展 分类,-1:全部,0:宠物,1:其他")
    int type;
    @ApiModelProperty(value = "查询此分类节点下的子分类，如果从根节点查询，此字段传0")
    long parentId;

    @ApiModelProperty(value = "状态 0:正常 1：下线")
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
