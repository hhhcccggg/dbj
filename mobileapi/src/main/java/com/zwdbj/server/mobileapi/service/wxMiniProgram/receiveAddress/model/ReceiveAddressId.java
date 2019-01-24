package com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

@ApiModel(description = "id对象")
public class ReceiveAddressId {

    @Min(value = 1)
    @ApiModelProperty(value = "id")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
