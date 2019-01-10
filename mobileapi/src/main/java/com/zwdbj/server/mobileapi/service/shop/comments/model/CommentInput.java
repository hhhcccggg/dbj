package com.zwdbj.server.mobileapi.service.shop.comments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户发布评价")
public class CommentInput {

    @ApiModelProperty(value = "评分")
    float rate;
    @ApiModelProperty(value = "被评论目标资源，如商品id")
    long resourceOwnerId;
    @ApiModelProperty(value = "评论内容")
    String contentTxt;
    @ApiModelProperty(value = "0:针对视频评论1:针对下线商家服务评论2：针对商家产品的评论")
    int resourceTypeId;
    @ApiModelProperty(value = "评论附带的资源类型 VIDEO:视频 IMAGE:图片 ")
    String type;
    @ApiModelProperty(value = "媒体资源内容，视频或图片url")
    String dataContent;

    public int getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(int resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public long getResourceOwnerId() {
        return resourceOwnerId;
    }

    public void setResourceOwnerId(long resourceOwnerId) {
        this.resourceOwnerId = resourceOwnerId;
    }


    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }
}
