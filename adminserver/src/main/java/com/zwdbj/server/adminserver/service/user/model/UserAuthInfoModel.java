package com.zwdbj.server.adminserver.service.user.model;

import java.io.Serializable;
import java.util.List;

public class UserAuthInfoModel implements Serializable {
    private long id;
    private String username;
    private String avatarUrl;
    private String email;
    private String phone;
    private String nickName;
    private int sex;
    private boolean isReviewed;
    private boolean isLivingOpen;
    private boolean isLiving;
    private long livingId;
    private boolean isLocked;
    private boolean isEmailVerification;
    private boolean isPhoneVerification;

    private List<String> roles;
    private List<String> permissions;

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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public boolean isLivingOpen() {
        return isLivingOpen;
    }

    public void setLivingOpen(boolean livingOpen) {
        isLivingOpen = livingOpen;
    }

    public boolean isLiving() {
        return isLiving;
    }

    public void setLiving(boolean living) {
        isLiving = living;
    }

    public long getLivingId() {
        return livingId;
    }

    public void setLivingId(long livingId) {
        this.livingId = livingId;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isEmailVerification() {
        return isEmailVerification;
    }

    public void setEmailVerification(boolean emailVerification) {
        isEmailVerification = emailVerification;
    }

    public boolean isPhoneVerification() {
        return isPhoneVerification;
    }

    public void setPhoneVerification(boolean phoneVerification) {
        isPhoneVerification = phoneVerification;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
