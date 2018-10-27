package com.zwdbj.server.adminserver.service.video.model;


import java.util.Date;
public class AdVideoVerityInfoDto {
    private long id;
    private Date createTime;
    private String title;
    private String coverImageUrl;
    private double coverImageWidth;
    private double coverImageHeight;
    private String videoUrl;
    private String tags;
    private double longitude;
    private double latitude;
    private boolean isHiddenLocation;
    private int status;
    private String rejectMsg;
    private int recommendIndex;
    private long userId;
    private long musicId;
    private int linkProductCount;
    private String address;
    private double firstFrameHeight;
    private String firstFrameUrl;
    private double firstFrameWidth;

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

    public double getCoverImageWidth() {
        return coverImageWidth;
    }

    public void setCoverImageWidth(double coverImageWidth) {
        this.coverImageWidth = coverImageWidth;
    }

    public double getCoverImageHeight() {
        return coverImageHeight;
    }

    public void setCoverImageHeight(double coverImageHeight) {
        this.coverImageHeight = coverImageHeight;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isHiddenLocation() {
        return isHiddenLocation;
    }

    public void setHiddenLocation(boolean hiddenLocation) {
        isHiddenLocation = hiddenLocation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public int getRecommendIndex() {
        return recommendIndex;
    }

    public void setRecommendIndex(int recommendIndex) {
        this.recommendIndex = recommendIndex;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public int getLinkProductCount() {
        return linkProductCount;
    }

    public void setLinkProductCount(int linkProductCount) {
        this.linkProductCount = linkProductCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getFirstFrameHeight() {
        return firstFrameHeight;
    }

    public void setFirstFrameHeight(double firstFrameHeight) {
        this.firstFrameHeight = firstFrameHeight;
    }

    public String getFirstFrameUrl() {
        return firstFrameUrl;
    }

    public void setFirstFrameUrl(String firstFrameUrl) {
        this.firstFrameUrl = firstFrameUrl;
    }

    public double getFirstFrameWidth() {
        return firstFrameWidth;
    }

    public void setFirstFrameWidth(double firstFrameWidth) {
        this.firstFrameWidth = firstFrameWidth;
    }
}
