package com.zwdbj.server.mobileapi.service.userInvitation.model;

import com.zwdbj.server.mobileapi.service.userInvitation.commmon.UserInvitationsState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "邀请好友")
public class UserInvitationModel {

    @ApiModelProperty("id")
    private long id;
    @ApiModelProperty("邀请人id")
    private long initiatorUserId;
    @ApiModelProperty("接受人ID")
    private long receivedUserId;
    @ApiModelProperty("状态 NONE REG:已注册 PETS:已添加宠物")
    private UserInvitationsState state;
    @ApiModelProperty("消息")
    private String message;

    public UserInvitationModel() {}

    public UserInvitationModel(long id, long initiatorUserId, long receivedUserId, UserInvitationsState state, String message) {
        this.id = id;
        this.initiatorUserId = initiatorUserId;
        this.receivedUserId = receivedUserId;
        this.state = state;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getInitiatorUserId() {
        return initiatorUserId;
    }

    public void setInitiatorUserId(long initiatorUserId) {
        this.initiatorUserId = initiatorUserId;
    }

    public long getReceivedUserId() {
        return receivedUserId;
    }

    public void setReceivedUserId(long receivedUserId) {
        this.receivedUserId = receivedUserId;
    }

    public UserInvitationsState getState() {
        return state;
    }

    public void setState(UserInvitationsState state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
