package com.zwdbj.server.mobileapi.service.shop.comments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "评论关联的媒体资源")
public class ShopCommentsExtraDatas extends ShopComments {
    @ApiModelProperty(value = "评论id")
    long commentId;
    @ApiModelProperty(value = "资源类型 VIDEO:视频 IMAGE:图片")
    String type;

    @ApiModelProperty(value = "资源id")
    long dataId;
    @ApiModelProperty(value = "媒体资源内容，如果dataId=0,则读取此字段的值")
    String dataContent;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }
}
