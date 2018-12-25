package com.zwdbj.server.adminserver.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改分类")
public class AdNewCategoryNameInput {
    @ApiModelProperty(value = "分类的名称")
    String name;

    @ApiModelProperty("iconUrl")
    String iconUrl;
    @ApiModelProperty("状态")
    int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
