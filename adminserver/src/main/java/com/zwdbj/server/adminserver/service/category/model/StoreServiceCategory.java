package com.zwdbj.server.adminserver.service.category.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class StoreServiceCategory implements Serializable {

    private static final long serialVersionUID = -1203405384178286150L;
    @ApiModelProperty(value = "服务范围id")
    long id;
    @ApiModelProperty(value = "服务名称")
    private String categoryName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
