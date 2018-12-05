package com.zwdbj.server.adminserver.service.user.model;

public class UserModel {
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
    private boolean isLivingWatch;
    private String  roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public UserModel() {
        
    }

    public UserModel(long id, String username, String avatarUrl, String email, String phone) {
        this.id = id;
        this.username = username;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.phone = phone;
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

    public boolean isLivingWatch() {
        return isLivingWatch;
    }

    public void setLivingWatch(boolean livingWatch) {
        isLivingWatch = livingWatch;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
}
