package com.zwdbj.server.adminserver.service.music.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "新建视频音乐输入字段")
public class AdNewMusicInput {
    @ApiModelProperty(value = "音乐的路径,这里设置为七牛上传文件后的Key")
    String musicUrl;
    @ApiModelProperty(value = "音乐名称")
    String title;
    @ApiModelProperty(value = "音乐时长")
    float duration;
    @ApiModelProperty(value = "艺术家")
    String artist;
    @ApiModelProperty(value = "封面路径,这里设置为七牛上传文件后的Key")
    String coverUrl;
    @ApiModelProperty(value = "分类ID")
    Long categoryId;


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}
