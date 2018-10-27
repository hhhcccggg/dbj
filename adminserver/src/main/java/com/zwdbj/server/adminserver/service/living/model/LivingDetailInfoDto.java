package com.zwdbj.server.adminserver.service.living.model;

import io.swagger.annotations.ApiModelProperty;

public class LivingDetailInfoDto extends LivingInfoDto {
    @ApiModelProperty(value = "关联的商品网页URL")
    String linkProductUrl;
    @ApiModelProperty(value = "主播添加商品URL")
    String addProductUrl;
    @ApiModelProperty(value = "视频分享标题")
    String shareTitle;
    @ApiModelProperty(value = "分享的内容")
    String shareContent;
    @ApiModelProperty(value = "分享URL")
    String shareUrl;
    @ApiModelProperty(value = "该直播聊天室的ID")
    String chatRoomId;
    @ApiModelProperty(value = "推送地址,如果当前不是主播的话，此字段是null")
    String pushUrl;

    public String getAddProductUrl() {
        return addProductUrl;
    }

    public void setAddProductUrl(String addProductUrl) {
        this.addProductUrl = addProductUrl;
    }

    @ApiModelProperty(value = "聊天室编号")



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

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public String getPushUrl() {
        return pushUrl;
    }

    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }

}
