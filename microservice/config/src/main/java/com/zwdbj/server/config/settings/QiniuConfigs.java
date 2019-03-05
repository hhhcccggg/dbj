package com.zwdbj.server.config.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.qiniu")
public class QiniuConfigs {
    private String accessKey;
    private String accessSecrect;
    private String bucketName;
    private String bucketDomain;
    /**
     * 上传token过期时间：秒
     */
    private int uptokenExpiretime;
    private String liveHubName;
    private String liveRtmpPublishDomain;
    private String liveRtmpPlayDomain;
    private String liveHlsPlayDomain;
    /**
     * 直播推流连接过期时间
     */
    private int livePublishExpiretime;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecrect() {
        return accessSecrect;
    }

    public void setAccessSecrect(String accessSecrect) {
        this.accessSecrect = accessSecrect;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketDomain() {
        return bucketDomain;
    }

    public void setBucketDomain(String bucketDomain) {
        this.bucketDomain = bucketDomain;
    }

    public int getUptokenExpiretime() {
        return uptokenExpiretime;
    }

    public void setUptokenExpiretime(int uptokenExpiretime) {
        this.uptokenExpiretime = uptokenExpiretime;
    }

    public String getLiveHubName() {
        return liveHubName;
    }

    public void setLiveHubName(String liveHubName) {
        this.liveHubName = liveHubName;
    }

    public String getLiveRtmpPublishDomain() {
        return liveRtmpPublishDomain;
    }

    public void setLiveRtmpPublishDomain(String liveRtmpPublishDomain) {
        this.liveRtmpPublishDomain = liveRtmpPublishDomain;
    }

    public String getLiveRtmpPlayDomain() {
        return liveRtmpPlayDomain;
    }

    public void setLiveRtmpPlayDomain(String liveRtmpPlayDomain) {
        this.liveRtmpPlayDomain = liveRtmpPlayDomain;
    }

    public String getLiveHlsPlayDomain() {
        return liveHlsPlayDomain;
    }

    public void setLiveHlsPlayDomain(String liveHlsPlayDomain) {
        this.liveHlsPlayDomain = liveHlsPlayDomain;
    }

    public int getLivePublishExpiretime() {
        return livePublishExpiretime;
    }

    public void setLivePublishExpiretime(int livePublishExpiretime) {
        this.livePublishExpiretime = livePublishExpiretime;
    }
}
