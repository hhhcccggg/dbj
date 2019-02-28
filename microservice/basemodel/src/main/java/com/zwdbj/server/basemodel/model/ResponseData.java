package com.zwdbj.server.basemodel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "接口统一响应结构")
public class ResponseData<T> implements Serializable {
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    protected int code;
    /**
     * 消息说明
     */
    @ApiModelProperty(value = "消息说明")
    protected String msg;
    /**
     * 数据主题
     */
    @ApiModelProperty(value = "数据主体")
    protected T data;
    @ApiModelProperty(value = "金币情况")
    protected ResponseCoin coins;
    /**
     * 可以通过此接口判断，接口是否相应成功
     */
    @ApiModelProperty(value = "可以通过此接口判断，接口是否相应成功")
    protected boolean success;

    public ResponseData(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public ResponseData(int code, String msg, T data,ResponseCoin coins) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.coins=coins;
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

    public ResponseCoin getCoins() {
        return coins;
    }

    public void setCoins(ResponseCoin coins) {
        this.coins = coins;
    }

    public boolean isSuccess() {
        success = code == ResponseDataCode.STATUS_NORMAL;
        return success;
    }
}
