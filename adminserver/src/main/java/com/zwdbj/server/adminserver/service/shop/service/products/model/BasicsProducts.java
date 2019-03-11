package com.zwdbj.server.adminserver.service.shop.service.products.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "")
public class BasicsProducts {


    @ApiModelProperty(value = "ID",required = true)
    private Long[] id;

    public Long[] getId() {
        return id;
    }

    public void setId(Long[] id) {
        this.id = id;
    }

    public void setId(String ids) {
        String[] idstr = ids.split(",");
        Long[] id = new Long[idstr.length];
        for (int i = 0; i < idstr.length; i++) {
            id[i] = Long.parseLong(idstr[i]);
        }
        this.id = id;
    }
}
