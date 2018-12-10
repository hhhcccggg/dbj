package com.zwdbj.server.adminserver.service.userAssets.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分类型存储用户金币总额")
public class UserCoinType {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "用户金币获得类型")
    String type;
    @ApiModelProperty(value = "金币总额")
    Long coins;
    @ApiModelProperty(value = "用户id")
    Long userId;
}
