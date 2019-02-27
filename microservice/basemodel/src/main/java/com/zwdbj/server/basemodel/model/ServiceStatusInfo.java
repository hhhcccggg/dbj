package com.zwdbj.server.basemodel.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ServiceStatusInfo<T> implements Serializable {
    private int code;
    private String msg;
    private T data;
    private boolean isSuccess;
    @ApiModelProperty(value = "金币情况")
    private ResponseCoin coins;

    public ServiceStatusInfo(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public ServiceStatusInfo(int code, String msg, T data,ResponseCoin coins) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.coins= coins;
    }

    public ResponseCoin getCoins() {
        return coins;
    }

    public void setCoins(ResponseCoin coins) {
        this.coins = coins;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
