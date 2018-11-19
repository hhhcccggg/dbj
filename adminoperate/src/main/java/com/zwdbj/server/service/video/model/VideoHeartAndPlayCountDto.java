package com.zwdbj.server.service.video.model;

public class VideoHeartAndPlayCountDto {
    private Long id;
    private Long userId;
    private Long playCount;
    private Long heartCount;
    private Long shareCount;
    private Long commentCount;

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Long getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(Long heartCount) {
        this.heartCount = heartCount;
    }

    public Long getShareCount() {
        return shareCount;
    }

    public void setShareCount(Long shareCount) {
        this.shareCount = shareCount;
    }
}
