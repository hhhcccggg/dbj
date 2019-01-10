package com.zwdbj.server.mobileapi.service.shop.comments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "评论附带的视频信息")
public class CommentVideoInfo extends CommentInput {
    @ApiModelProperty(value = "封面图片")
    String coverImageUrl;
    @ApiModelProperty(value = "封面图尺寸宽")
    float coverImageWidth;
    @ApiModelProperty(value = "封面图尺寸高")
    float coverImageHeight;
    @ApiModelProperty(value = "视频第一帧，这里上传七牛上传后的key")
    String firstFrameUrl;
    float firstFrameWidth;
    float firstFrameHeight;
    @ApiModelProperty(value = "视频url,设置为七牛上传文件后的key")
    String videoUrl;

    @ApiModelProperty(value = "经度")
    float longitude;
    @ApiModelProperty(value = "纬度")
    float latitude;
    @ApiModelProperty(value = "地址")
    String address;

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public float getCoverImageWidth() {
        return coverImageWidth;
    }

    public void setCoverImageWidth(float coverImageWidth) {
        this.coverImageWidth = coverImageWidth;
    }

    public float getCoverImageHeight() {
        return coverImageHeight;
    }

    public void setCoverImageHeight(float coverImageHeight) {
        this.coverImageHeight = coverImageHeight;
    }

    public String getFirstFrameUrl() {
        return firstFrameUrl;
    }

    public void setFirstFrameUrl(String firstFrameUrl) {
        this.firstFrameUrl = firstFrameUrl;
    }

    public float getFirstFrameWidth() {
        return firstFrameWidth;
    }

    public void setFirstFrameWidth(float firstFrameWidth) {
        this.firstFrameWidth = firstFrameWidth;
    }

    public float getFirstFrameHeight() {
        return firstFrameHeight;
    }

    public void setFirstFrameHeight(float firstFrameHeight) {
        this.firstFrameHeight = firstFrameHeight;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
