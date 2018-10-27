package com.zwdbj.server.adminserver.service.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "管理账号的输入字段")
public class AdManageUserInput {
    @ApiModelProperty(value = "账号状态,-1:全部,0:正常,1:停用")
    int isLocked;
    @ApiModelProperty(value = "搜索的手机号或者姓名")
    String keywords;

    public int getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
