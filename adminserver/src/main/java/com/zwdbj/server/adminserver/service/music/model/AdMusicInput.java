package com.zwdbj.server.adminserver.service.music.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "视频音乐管理输入字段")
public class AdMusicInput {
    @ApiModelProperty(value = "音乐状态")
    int isDeleted;
    @ApiModelProperty(value = "搜索关键字")
    String keywords;

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
