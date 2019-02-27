package com.zwdbj.server.basemodel.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(description = "金币")
public class ResponseCoin implements Serializable {
    @ApiModelProperty("描述，终端可以直接使用，例如：恭喜你获得5个金币")
    private String message;
    @ApiModelProperty("金币数量")
    private int coins;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
