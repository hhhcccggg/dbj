package com.zwdbj.server.adminserver.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "发布短视频字段")
public class VideoPublishInput {
    @ApiModelProperty(value = "描述信息")
    private String title;
    @ApiModelProperty(value = "封面图片，这里设置为七牛上传文件后的Key")
    private String coverImageKey;
    @ApiModelProperty(value = "封面图尺寸宽")
    float coverImageWidth;
    @ApiModelProperty(value = "封面图尺寸高")
    float coverImageHeight;
    @ApiModelProperty(value = "短视频，这里设置为七牛上传文件后的Key")
    private String videoKey;
    @ApiModelProperty(value = "关联的宠物ID，多个宠物ID使用英文下,分割")
    private String linkPets;
    @ApiModelProperty(value = "关联的标签，多个标签名使用英文下,分割")
    private String tags;
    @ApiModelProperty(value = "经度")
    private float longitude;
    @ApiModelProperty(value = "纬度")
    private float latitude;
    @ApiModelProperty(value = "地址")
    String address;
    @ApiModelProperty(value = "视频使用的背景音乐，如果没有传0")
    private long musicId;
    @ApiModelProperty(value = "是否隐藏位置信息，隐藏以后就不会出现在附近列表")
    private boolean isHiddenLocation;

    // 第一帧图片
    @ApiModelProperty(value = "视频第一帧，这里传七牛上传后的KEY")
    String firstFrameUrl;
    float firstFrameWidth;
    float firstFrameHeight;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImageKey() {
        return coverImageKey;
    }

    public void setCoverImageKey(String coverImageKey) {
        this.coverImageKey = coverImageKey;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public String getLinkPets() {
        return linkPets;
    }

    public void setLinkPets(String linkPets) {
        this.linkPets = linkPets;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public boolean isHiddenLocation() {
        return isHiddenLocation;
    }

    public void setHiddenLocation(boolean hiddenLocation) {
        isHiddenLocation = hiddenLocation;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
