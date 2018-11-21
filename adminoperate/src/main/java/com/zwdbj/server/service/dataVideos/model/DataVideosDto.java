package com.zwdbj.server.service.dataVideos.model;

public class DataVideosDto {
    private String id;
    private String title;
    private String coverImageUrl;
    private float coverImageWidth;
    private float coverImageHeight;
    private String firstFrameUrl;
    private float firstFrameWidth;
    private float firstFrameHeight;
    private String videoUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
