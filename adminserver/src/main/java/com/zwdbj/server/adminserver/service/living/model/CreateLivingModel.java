package com.zwdbj.server.adminserver.service.living.model;

public class CreateLivingModel extends CreateLivingInput {
    long id;
    long userId;
    String rtmpPublishUrl;
    String rtmpPlayUrl;
    String hlsPlayUrl;

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

    public String getRtmpPublishUrl() {
        return rtmpPublishUrl;
    }

    public void setRtmpPublishUrl(String rtmpPublishUrl) {
        this.rtmpPublishUrl = rtmpPublishUrl;
    }

    public String getRtmpPlayUrl() {
        return rtmpPlayUrl;
    }

    public void setRtmpPlayUrl(String rtmpPlayUrl) {
        this.rtmpPlayUrl = rtmpPlayUrl;
    }

    public String getHlsPlayUrl() {
        return hlsPlayUrl;
    }

    public void setHlsPlayUrl(String hlsPlayUrl) {
        this.hlsPlayUrl = hlsPlayUrl;
    }
}
