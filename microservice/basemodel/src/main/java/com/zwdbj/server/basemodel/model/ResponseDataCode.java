package com.zwdbj.server.basemodel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "接口响应码")
public class ResponseDataCode {
    @ApiModelProperty(value = "正常")
    public static final int STATUS_NORMAL = 0;
    @ApiModelProperty(value = "未认证")
    public static final int STATUS_UNAUTH = 401;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_ERROR = 500;
}
