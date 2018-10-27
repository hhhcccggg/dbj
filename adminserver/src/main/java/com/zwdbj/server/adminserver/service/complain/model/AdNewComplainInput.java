package com.zwdbj.server.adminserver.service.complain.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "新建举报选项字段")
public class AdNewComplainInput {
    @ApiModelProperty(value = "新加的举报title")
    String title;
    @ApiModelProperty(value = "新加的举报描述")
    String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
