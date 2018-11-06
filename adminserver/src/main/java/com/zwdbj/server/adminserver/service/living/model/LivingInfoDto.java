package com.zwdbj.server.adminserver.service.living.model;

import com.zwdbj.server.utility.common.DateTimeFriendly;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("直播频道信息")
public class LivingInfoDto {
    long id;
    @ApiModelProperty(value = "主播")
    long userId;
    String userNickName;
    String userAvatarUrl;
    String title;
    String coverUrl;
    @ApiModelProperty("关联的宠物ID，多个是英文下,隔开")
    String linkPets;
    float longitude;
    float latitude;
    String address;
    int recommendIndex;
    @ApiModelProperty(value = "观看次数")
    long playCount;
    @ApiModelProperty(value = "留言/聊天室消息总数")
    long commentCount;
    @ApiModelProperty(value = "点赞数")
    long heartCount;
    @ApiModelProperty(value = "分享总数")
    long shareCount;
    @ApiModelProperty(value = "在线人数")
    long onlinePeopleCount;
    @ApiModelProperty(value = "拉流地址")
    String pullUrl;
    @ApiModelProperty(value = "HLS拉流地址")
    String hlsUrl;
    @ApiModelProperty(value = "直播时长(秒)")
    long liveingTotalTime;
    @ApiModelProperty(value = "获得的金币")
    long getCoins;
    @ApiModelProperty(value = "获得的好友")
    long getFriends;
    @ApiModelProperty(value = "当前是否在直播中")
    boolean isLiving;
    @ApiModelProperty(value = "关联的产品")
    int linkProductCount;
    @ApiModelProperty(value = "当前用户是否关注了主播")
    boolean isFollow;

    @ApiModelProperty(value = "创建的时间")
    protected Date createTime;
    @ApiModelProperty(value = "时间友好显示，比如：5分前，1小时前。")
    protected String createTimeFormat;

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

    public String getPullUrl() {
        return pullUrl;
    }

    public void setPullUrl(String pullUrl) {
        this.pullUrl = pullUrl;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public long getLiveingTotalTime() {
        return liveingTotalTime;
    }

    public void setLiveingTotalTime(long liveingTotalTime) {
        this.liveingTotalTime = liveingTotalTime;
    }

    public long getGetCoins() {
        return getCoins;
    }

    public void setGetCoins(long getCoins) {
        this.getCoins = getCoins;
    }

    public long getGetFriends() {
        return getFriends;
    }

    public void setGetFriends(long getFriends) {
        this.getFriends = getFriends;
    }

    public boolean getIsLiving() {
        return isLiving;
    }

    public void setIsLiving(boolean isLiving) {
        this.isLiving = isLiving;
    }

    public int getLinkProductCount() {
        return linkProductCount;
    }

    public void setLinkProductCount(int linkProductCount) {
        this.linkProductCount = linkProductCount;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getLinkPets() {
        return linkPets;
    }

    public void setLinkPets(String linkPets) {
        this.linkPets = linkPets;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRecommendIndex() {
        return recommendIndex;
    }

    public void setRecommendIndex(int recommendIndex) {
        this.recommendIndex = recommendIndex;
    }

    public long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(long playCount) {
        this.playCount = playCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(long heartCount) {
        this.heartCount = heartCount;
    }

    public long getShareCount() {
        return shareCount;
    }

    public void setShareCount(long shareCount) {
        this.shareCount = shareCount;
    }

    public long getOnlinePeopleCount() {
        return onlinePeopleCount;
    }

    public void setOnlinePeopleCount(long onlinePeopleCount) {
        this.onlinePeopleCount = onlinePeopleCount;
    }

    public boolean isLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.setCreateTimeFormat(DateTimeFriendly.friendlyShow(this.createTime));
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
    }
}
