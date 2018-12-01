package com.zwdbj.server.mobileapi.service.tag.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "标签")
public class TagDto {
    @ApiModelProperty(value = "标签id")
    long id;
    @ApiModelProperty(value = "名字")
    String name;
    @ApiModelProperty(value = "标签关联的资源数")
    long resNumber;

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

    public long getResNumber() {
        return resNumber;
    }

    public void setResNumber(long resNumber) {
        this.resNumber = resNumber;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resNumber=" + resNumber +
                '}';
    }
}
