package com.zwdbj.server.adminserver.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "查询基本分类信息")
public class AdBasicCategoryInput {
    @ApiModelProperty(value = "状态 -1:全部,0:正常,1:删除")
    int isDeleted;
    @ApiModelProperty(value = "搜索关键字")
    String keywords;
    @ApiModelProperty(value = "分类,-1:全部,0:宠物,1:其他")
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
