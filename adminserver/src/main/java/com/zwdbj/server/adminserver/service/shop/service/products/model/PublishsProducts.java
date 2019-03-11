package com.zwdbj.server.adminserver.service.shop.service.products.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class PublishsProducts extends BasicsProducts{

    @ApiModelProperty(value = "上下架",required = true)
    private boolean publish;

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
}
