package com.zwdbj.server.adminserver.service.comment.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加评论
 */
@ApiModel(description = "添加/发布评论")
public class AddCommentInput {

    @ApiModelProperty(value = "被评论的资源，比如：视频等")
    long resId;

    @ApiModelProperty(value = "回复/引用的评论id，如果直接发布传0")
    long refCommentId;
    @ApiModelProperty(value = "评论的内容")
    String content;
    @ApiModelProperty(value = "当前用户是否为楼主&视频的创作者")
    Boolean isOwner;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getRefCommentId() {
        return refCommentId;
    }

    public void setRefCommentId(long refCommentId) {
        this.refCommentId = refCommentId;
    }

    public long getResId() {
        return resId;
    }

    public void setResId(long resId) {
        this.resId = resId;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }
}
