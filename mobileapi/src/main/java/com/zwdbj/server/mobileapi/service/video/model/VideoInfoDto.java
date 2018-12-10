package com.zwdbj.server.mobileapi.service.video.model;

import com.zwdbj.server.mobileapi.service.pet.model.PetModelDto;
import com.zwdbj.server.utility.common.DateTimeFriendly;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(description = "视频数据")
public class VideoInfoDto {
    @ApiModelProperty(value = "视频ID")
    long id;
    @ApiModelProperty(value = "视频描述")
    String title;
    @ApiModelProperty(value = "视频封面图")
    String coverImageUrl;
    @ApiModelProperty(value = "封面图尺寸宽")
    float coverImageWidth;
    @ApiModelProperty(value = "封面图尺寸高")
    float coverImageHeight;
    @ApiModelProperty(value = "视频地址")
    String videoUrl;
    @ApiModelProperty(value = "关联的宠物ID，多个使用英文下,隔开")
    String linkPets;
    @ApiModelProperty(value = "标签，多个使用英文下,隔开")
    String tags;
    @ApiModelProperty(value = "经度")
    float longitude;
    @ApiModelProperty(value = "纬度")
    float latitude;
    @ApiModelProperty(value = "地址")
    String address;
    @ApiModelProperty(value = "推荐指数")
    int recommendIndex;
    @ApiModelProperty(value = "播放次数")
    long playCount;
    @ApiModelProperty(value = "留言总数")
    long commentCount;
    @ApiModelProperty(value = "点赞总数")
    long heartCount;
    @ApiModelProperty(value = "被分享次数")
    long shareCount;
    @ApiModelProperty(value = "视频用户ID")
    long userId;
    String userNickName;
    @ApiModelProperty(value = "视频背景音乐ID")
    long musicId;
    @ApiModelProperty(value = "视频状态》》0:正常1:审核中2:下架3:需要人工审核")
    int status;
    @ApiModelProperty(value = "视频被拒下架的原因")
    String rejectMsg;


    //20180731新增
    @ApiModelProperty(value = "视频用户的头像")
    String userAvatarUrl;
    @ApiModelProperty(value = "当前用户是否关注此视频用户,如果当前没有登录,始终返回false")
    boolean isFollow;
    @ApiModelProperty(value = "当前用户是否对此视频已点赞,如果当前没有登录,始终返回false")
    boolean isHearted;
    @ApiModelProperty(value = "关联商品个数，如果没有返回0")
    int linkProductCount;

    // 第一帧图片
    @ApiModelProperty(value = "视频第一帧URL")
    String firstFrameUrl;
    float firstFrameWidth;
    float firstFrameHeight;


    @ApiModelProperty(value = "创建的时间")
    protected Date createTime;
    @ApiModelProperty(value = "时间友好显示，比如：5分前，1小时前。")
    protected String createTimeFormat;
    @ApiModelProperty(value = "视频用户的宠物")
    List<PetModelDto> petModelDtoList;

    public List<PetModelDto> getPetModelDtoList() {
        return petModelDtoList;
    }

    public void setPetModelDtoList(List<PetModelDto> petModelDtoList) {
        this.petModelDtoList = petModelDtoList;
    }

    public String getRejectMsg() {
        return rejectMsg;
    }

    public void setRejectMsg(String rejectMsg) {
        this.rejectMsg = rejectMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFirstFrameUrl() {
        return firstFrameUrl;
    }

    public void setFirstFrameUrl(String firstFrameUrl) {
        this.firstFrameUrl = firstFrameUrl;
    }

    public float getFirstFrameWidth() {
        return firstFrameWidth;
    }

    public void setFirstFrameWidth(float firstFrameWidth) {
        this.firstFrameWidth = firstFrameWidth;
    }

    public float getFirstFrameHeight() {
        return firstFrameHeight;
    }

    public void setFirstFrameHeight(float firstFrameHeight) {
        this.firstFrameHeight = firstFrameHeight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getLinkPets() {
        return linkPets;
    }

    public void setLinkPets(String linkPets) {
        this.linkPets = linkPets;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public long getMusicId() {
        return musicId;
    }

    public void setMusicId(long musicId) {
        this.musicId = musicId;
    }

    public int getLinkProductCount() {
        return linkProductCount;
    }

    public void setLinkProductCount(int linkProductCount) {
        this.linkProductCount = linkProductCount;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public boolean isHearted() {
        return isHearted;
    }

    public void setHearted(boolean hearted) {
        isHearted = hearted;
    }

    public float getCoverImageWidth() {
        return coverImageWidth;
    }

    public void setCoverImageWidth(float coverImageWidth) {
        this.coverImageWidth = coverImageWidth;
    }

    public float getCoverImageHeight() {
        return coverImageHeight;
    }

    public void setCoverImageHeight(float coverImageHeight) {
        this.coverImageHeight = coverImageHeight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
