package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "服务范围")
public class StoreServiceCategory implements Serializable {

    private static final long serialVersionUID = -732539217741767219L;
    @ApiModelProperty(value = "服务范围id")
    private long id;
    @ApiModelProperty(value = "服务名称")
    private String name;
    @ApiModelProperty(value = "营业状态 0正常 1下线")
    int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
