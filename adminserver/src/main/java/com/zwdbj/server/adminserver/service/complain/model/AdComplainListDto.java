package com.zwdbj.server.adminserver.service.complain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户举报的基本选项")
public class AdComplainListDto {

    @ApiModelProperty(value = "举报原因")
    String title;
    @ApiModelProperty(value = "举报原因的状态,0:正常,1:停用")
    int isOpen;
    @ApiModelProperty(value = "举报原因的描述")
    String description;
    @ApiModelProperty(value = "举报原因的分类")
    int type;
    @ApiModelProperty("使用的次数")
    int count;


    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
