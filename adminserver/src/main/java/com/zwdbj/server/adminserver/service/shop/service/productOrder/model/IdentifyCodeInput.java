package com.zwdbj.server.adminserver.service.shop.service.productOrder.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(description = "验证消费码")
public class IdentifyCodeInput {
    @NotNull(message = "验证消费码不能为空")
    @ApiModelProperty(value = "验证消费码")
    private String identifyCode;

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }
}
