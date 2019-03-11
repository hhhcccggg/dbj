package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "代言人作品列表个人信息")
public class SuperStarDto {
    @ApiModelProperty(value = "userId")
    private long usedId;
    @ApiModelProperty(value = "姓名")
    private String fullName;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "头像")
    private String avatarUrl;
    @ApiModelProperty(value = "作品数")
    private int videos;
    @ApiModelProperty(value = "累计获赞数")
    private int totalHearts;
    @ApiModelProperty(value = "粉丝数")
    private int totalFans;
    @ApiModelProperty(value = "宠物数量")
    private int pets;
    @ApiModelProperty(value = "累计评论数")
    private int commentCounts;
    @ApiModelProperty(value = "资质认证")
    String qualification;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getUsedId() {
        return usedId;
    }

    public void setUsedId(long usedId) {
        this.usedId = usedId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public int getTotalHearts() {
        return totalHearts;
    }

    public void setTotalHearts(int totalHearts) {
        this.totalHearts = totalHearts;
    }

    public int getTotalFans() {
        return totalFans;
    }

    public void setTotalFans(int totalFans) {
        this.totalFans = totalFans;
    }

    public int getPets() {
        return pets;
    }

    public void setPets(int pets) {
        this.pets = pets;
    }

    public int getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
