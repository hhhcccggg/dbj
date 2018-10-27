package com.zwdbj.server.adminserver.service.comment.model;

import com.zwdbj.server.adminserver.utility.DateTimeFriendly;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CommentInfoDto {
    @ApiModelProperty(value = "评论ID")
    protected long id;

    @ApiModelProperty(value = "评论用户")
    protected long userId;
    @ApiModelProperty(value = "评论用户昵称")
    protected String nickName;
    @ApiModelProperty(value = "评论用户头像")
    protected String userAvatarUrl;
    @ApiModelProperty(value = "评论用户是否为视频发布者")
    protected  boolean isOwner;
    @ApiModelProperty(value = "评论的内容")
    protected String contentTxt;
    @ApiModelProperty(value = "引用评论Id")
    protected long refCommentId;
    @ApiModelProperty(value = "引用的评论内容.refCommentId>0,此字段有值。一对一回复评论，只是支持一层。")
    protected CommentInfoDto refComment;
    @ApiModelProperty(value = "原评论内容")
    String originContentTxt;
    @ApiModelProperty(value = "审核的状态")
    String reviewStatus;
    @ApiModelProperty(value = "评论点赞量")
    protected long heartCount;
    @ApiModelProperty(value = "当前用户是否对评论已点赞")
    protected boolean hasHeart;
    @ApiModelProperty(value = "被评论的对象，比如：视频ID")
    protected long resourceOwnerId;

    @ApiModelProperty(value = "评论发生的时间")
    protected Date createTime;
    @ApiModelProperty(value = "评论时间友好显示，比如：5分前，1小时前。")
    protected String createTimeFormat;

    @ApiModelProperty(value = "资源标题")
    String title;
    @ApiModelProperty(value = "资源发布者ID")
    String resourcePublishUserId;

    public String getOriginContentTxt() {
        return originContentTxt;
    }

    public void setOriginContentTxt(String originContentTxt) {
        this.originContentTxt = originContentTxt;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
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

    public CommentInfoDto getRefComment() {
        return refComment;
    }

    public void setRefComment(CommentInfoDto refComment) {
        this.refComment = refComment;
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

    public boolean isHasHeart() {
        return hasHeart;
    }

    public void setHasHeart(boolean hasHeart) {
        this.hasHeart = hasHeart;
    }
}
