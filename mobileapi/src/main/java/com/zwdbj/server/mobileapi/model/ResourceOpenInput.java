package com.zwdbj.server.mobileapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "资源开关")
public class ResourceOpenInput<T> {
    @ApiModelProperty(value = "需要操作的对象")
    T id;
    @ApiModelProperty(value = "开/关")
    boolean isOpen;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
