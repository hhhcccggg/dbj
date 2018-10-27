package com.zwdbj.server.adminserver.service.comment.model;

import com.zwdbj.server.adminserver.utility.DateTimeFriendly;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "后台评论输出字段")
public class AdCommentInfoDto {
    @ApiModelProperty(value = "评论ID")
    protected long id;
    @ApiModelProperty(value = "评论发生的时间")
    protected Date createTime;
    @ApiModelProperty(value = "评论用户")
    protected long userId;
    @ApiModelProperty(value = "评论用户昵称")
    protected String nickName;
    @ApiModelProperty(value = "评论用户是否为视频发布者")
    protected  boolean isOwner;
    @ApiModelProperty(value = "评论的内容")
    protected String contentTxt;
    @ApiModelProperty(value = "引用评论Id")
    protected long refCommentId;
    @ApiModelProperty(value = "评论点赞量")
    protected long heartCount;
    @ApiModelProperty(value = "被评论的对象，比如：视频ID")
    protected long resourceOwnerId;
    @ApiModelProperty(value = "被评论的对象的类型，比如：视频,直播")
    protected long resourceTypeId;
    @ApiModelProperty(value = "评论时间友好显示，比如：5分前，1小时前。")
    protected String createTimeFormat;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.setCreateTimeFormat(DateTimeFriendly.friendlyShow(this.createTime));
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }

    public long getRefCommentId() {
        return refCommentId;
    }

    public void setRefCommentId(long refCommentId) {
        this.refCommentId = refCommentId;
    }

    public long getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    public long getResourceOwnerId() {
        return resourceOwnerId;
    }

    public void setResourceOwnerId(long resourceOwnerId) {
        this.resourceOwnerId = resourceOwnerId;
    }

    public long getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(long resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }
}
