package com.zwdbj.server.basemodel.model;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

@ApiModel("验证手机号")
public class VerifyPhoneInput implements Serializable {
    private String phone;
    private String code;

    public VerifyPhoneInput() {

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
}
