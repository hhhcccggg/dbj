package com.zwdbj.server.mobileapi.service.user.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class NewMyPasswordInput implements Serializable {
    private static final long serialVersionUID = 2091840217279552491L;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "手机号验证码")
    private String code;
    @ApiModelProperty(value = "输入的密码")
    private String password;
    @ApiModelProperty(value = "确认密码")
    private String passwordTwo;
    @ApiModelProperty(value = "秘钥")
    private String keys;

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordTwo() {
        return passwordTwo;
    }

    public void setPasswordTwo(String passwordTwo) {
        this.passwordTwo = passwordTwo;
    }
}
