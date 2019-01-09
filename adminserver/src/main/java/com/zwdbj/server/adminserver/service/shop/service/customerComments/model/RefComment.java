package com.zwdbj.server.adminserver.service.shop.service.customerComments.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "评论回复")
public class RefComment {
    @ApiModelProperty(value = "回复评论id")
    long refCommentId;

    @ApiModelProperty(value = "用户名")
    String userName;
    @ApiModelProperty(value = "用户id")
    long userId;
    @ApiModelProperty(value = "发布时间")
    Date createTime;
    @ApiModelProperty(value = "回复内容")
    String refContentTxt;
}
