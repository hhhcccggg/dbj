package com.zwdbj.server.adminserver.service.complain.model;

public class UserComplainDto {
    private Long toResId;
    private int complainCount;

    public Long getToResId() {
        return toResId;
    }

    public void setToResId(Long toResId) {
        this.toResId = toResId;
    }

    public int getComplainCount() {
        return complainCount;
    }

    public void setComplainCount(int complainCount) {
        this.complainCount = complainCount;
    }
}
