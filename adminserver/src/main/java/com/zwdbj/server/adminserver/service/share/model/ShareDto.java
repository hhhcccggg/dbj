package com.zwdbj.server.adminserver.service.share.model;

import com.zwdbj.server.adminserver.service.pet.model.PetModelDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "分享输出字段")
public class ShareDto {
    @ApiModelProperty(value = "用户昵称")
    String nickName;
    @ApiModelProperty(value = "用户头像地址")
    String avatarUrl;
    @ApiModelProperty(value = "视频标签")
    String tags;
    @ApiModelProperty(value = "视频标题")
    String title;
    @ApiModelProperty(value = "视频的URL")
    String videoUrl;
    @ApiModelProperty(value = "视频相关宠物")
    List<PetModelDto> petModelDtos;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PetModelDto> getPetModelDtos() {
        return petModelDtos;
    }

    public void setPetModelDtos(List<PetModelDto> petModelDtos) {
        this.petModelDtos = petModelDtos;
    }
}
