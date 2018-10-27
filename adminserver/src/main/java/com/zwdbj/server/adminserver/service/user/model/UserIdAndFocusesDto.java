package com.zwdbj.server.adminserver.service.user.model;

public class UserIdAndFocusesDto {
    private Long followerUserId;
    private Long totalMyFocuses;

    public Long getFollowerUserId() {
        return followerUserId;
    }

    public void setFollowerUserId(Long followerUserId) {
        this.followerUserId = followerUserId;
    }

    public Long getTotalMyFocuses() {
        return totalMyFocuses;
    }

    public void setTotalMyFocuses(Long totalMyFocuses) {
        this.totalMyFocuses = totalMyFocuses;
    }
}
