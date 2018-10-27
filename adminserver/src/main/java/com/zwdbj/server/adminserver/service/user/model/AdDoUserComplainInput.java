package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "被举报的用户处理输入字段")
public class AdDoUserComplainInput {
    @ApiModelProperty(value = "选择处理方式,0:忽略,1:冻结")
    int isLocked;

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }
}
