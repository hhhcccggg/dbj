package com.zwdbj.server.mobileapi.service.category.model;

import io.swagger.annotations.ApiModelProperty;

public class CategoryRecommendDto {
    @ApiModelProperty("分类ID")
    private long id;
    @ApiModelProperty("分类名")
    private String name;

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
}
