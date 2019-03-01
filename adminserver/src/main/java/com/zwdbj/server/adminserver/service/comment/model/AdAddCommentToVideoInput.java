package com.zwdbj.server.adminserver.service.comment.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "后台认为的给视频添加评论")
public class AdAddCommentToVideoInput {
    @ApiModelProperty(value = "视频的id")
    private long videoId;
    @ApiModelProperty(value = "评论内容")
    private String contentTxt;

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }


    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }
}
