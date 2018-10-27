package com.zwdbj.server.adminserver.service.user.model;

public class UserIdAndFollowersDto {
    private Long userId;
    private Long totalFans;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(Long totalFans) {
        this.totalFans = totalFans;
    }
}
