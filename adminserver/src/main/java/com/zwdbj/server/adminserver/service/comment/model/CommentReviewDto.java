package com.zwdbj.server.adminserver.service.comment.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "评论审核")
public class CommentReviewDto {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "评论的内容")
    String contentTxt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentTxt() {
        return contentTxt;
    }

    public void setContentTxt(String contentTxt) {
        this.contentTxt = contentTxt;
    }
}
