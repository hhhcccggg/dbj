package com.zwdbj.server.mobileapi.service.userInvitation.model;

import com.zwdbj.server.mobileapi.service.userInvitation.commmon.UserInvitationsState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索邀请")
public class SearchUserInvitation {

    @ApiModelProperty(value = "邀请人id" , hidden = true)
    long initiatorUserId;
    @ApiModelProperty("状态 NONE REG:已注册 PETS:已添加宠物")
    private UserInvitationsState state;

    public long getInitiatorUserId() {
        return initiatorUserId;
    }

    public void setInitiatorUserId(long initiatorUserId) {
        this.initiatorUserId = initiatorUserId;
    }

    public UserInvitationsState getState() {
        return state;
    }

    public void setState(UserInvitationsState state) {
        this.state = state;
    }
}
