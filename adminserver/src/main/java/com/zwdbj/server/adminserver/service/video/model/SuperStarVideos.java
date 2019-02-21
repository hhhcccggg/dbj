package com.zwdbj.server.adminserver.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "代言人视频信息")
public class SuperStarVideos {
    @ApiModelProperty(value = "视频id")
    private long videoId;
    @ApiModelProperty(value = "视频标题")
    private String title;
    @ApiModelProperty(value = "视频封面图")
    private String coverImageUrl;
    @ApiModelProperty(value = "视频url")
    private String videoUrl;
    @ApiModelProperty(value = "视频点赞数")
    private long heartCount;
    @ApiModelProperty(value = "视频评论数量")
    private long commentCount;
    @ApiModelProperty(value = "视频收到的打赏次数")
    private long tipCount;
    @ApiModelProperty(value = "视频浏览次数")
    private long playCount;
    @ApiModelProperty(value = "视频分享次数")
    private long shareCount;
    @ApiModelProperty(value = "是否关联店铺")
    private boolean isRelateStore;
    @ApiModelProperty(value = "视频发布时间")
    private Date createTime;
    @ApiModelProperty(value = "状态 0:正常1:审核中2:下架3:需要人工审核")
    private int status;
    @ApiModelProperty(value = "主题")
    private String tags;

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

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

    public long getTipCount() {
        return tipCount;
    }

    public void setTipCount(long tipCount) {
        this.tipCount = tipCount;
    }

    public long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(long playCount) {
        this.playCount = playCount;
    }

    public long getShareCount() {
        return shareCount;
    }

    public void setShareCount(long shareCount) {
        this.shareCount = shareCount;
    }

    public boolean isRelateStore() {
        return isRelateStore;
    }

    public void setRelateStore(boolean relateStore) {
        isRelateStore = relateStore;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
