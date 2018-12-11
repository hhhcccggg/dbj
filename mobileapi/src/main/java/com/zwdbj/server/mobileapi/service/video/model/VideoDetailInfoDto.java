package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModelProperty;

public class VideoDetailInfoDto extends VideoInfoDto {
    @ApiModelProperty(value = "视频关联的商品网页URL")
    String linkProductUrl;
    @ApiModelProperty(value = "视频分享标题")
    String shareTitle;
    @ApiModelProperty(value = "分享的内容")
    String shareContent;
    @ApiModelProperty(value = "分享URL")
    String shareUrl;
    @ApiModelProperty(value = "视频获得的打赏次数")
    long tipCount;

    public long getTipCount() {
        return tipCount;
    }

    public void setTipCount(long tipCount) {
        this.tipCount = tipCount;
    }

    public String getLinkProductUrl() {
        return linkProductUrl;
    }

    public void setLinkProductUrl(String linkProductUrl) {
        this.linkProductUrl = linkProductUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }
}
