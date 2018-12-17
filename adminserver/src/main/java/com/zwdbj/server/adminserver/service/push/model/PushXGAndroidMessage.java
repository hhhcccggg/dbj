package com.zwdbj.server.adminserver.service.push.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class PushXGAndroidMessage implements Serializable {
    private String title;
    private String content;
    private PushXGExtraMessage custom_content;
    @ApiModelProperty(value = "消息类型0:系统消息,1:点赞类2:粉丝类3:评论4:关注人发布视频5:关注人发布直播")
    private int type;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PushXGExtraMessage getCustom_content() {
        return custom_content;
    }

    public void setCustom_content(PushXGExtraMessage custom_content) {
        this.custom_content = custom_content;
    }
}
