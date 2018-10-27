package com.zwdbj.server.adminserver.service.messageCenter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "消息未读情况")
public class MessageUnReadDto {
    @ApiModelProperty(value = "点赞消息未读情况")
    protected long heartCount;
    @ApiModelProperty(value = "评论消息未读情况")
    protected long commentCount;
    @ApiModelProperty(value = "粉丝消息未读情况")
    protected long followerCount;
    @ApiModelProperty(value = "系统消息未读情况")
    protected long systemCount;

    public long getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(long followerCount) {
        this.followerCount = followerCount;
    }

    public long getSystemCount() {
        return systemCount;
    }

    public void setSystemCount(long systemCount) {
        this.systemCount = systemCount;
    }
}
