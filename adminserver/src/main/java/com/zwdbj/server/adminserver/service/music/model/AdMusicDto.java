package com.zwdbj.server.adminserver.service.music.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "视频音乐管理输出字段")
public class AdMusicDto {
    @ApiModelProperty(value = "音乐封面")
    String coverUrl;
    @ApiModelProperty(value = "歌曲名称")
    String title;
    @ApiModelProperty(value = "歌曲状态")
    int isDeleted;
    @ApiModelProperty(value = "歌曲时长")
    float duration;
    @ApiModelProperty(value = "使用次数")
    Long count;

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
