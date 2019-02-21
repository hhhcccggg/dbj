package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "主页视频")
public class VideoMain extends  VideoInfoDto{

    @ApiModelProperty(value = "店铺Id")
    private long storeId;
    @ApiModelProperty(value = "店铺名称")
    private String storeName;
    @ApiModelProperty(value = "logo地址")
    private String logoUrl;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}