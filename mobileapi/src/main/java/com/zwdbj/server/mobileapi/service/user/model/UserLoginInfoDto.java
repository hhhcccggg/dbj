package com.zwdbj.server.mobileapi.service.user.model;

import com.zwdbj.server.tokencenter.model.UserToken;

public class UserLoginInfoDto {
    protected UserToken userToken;
    private long id;
    private String username;
    private String nickName;
    private String avatarUrl;
    private String phone;
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserToken getUserToken() {
        return userToken;
    }
    public void setUserToken(UserToken userToken) {
        this.userToken = userToken;
    }
}
