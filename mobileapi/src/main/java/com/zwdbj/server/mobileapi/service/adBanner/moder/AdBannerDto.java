package com.zwdbj.server.mobileapi.service.adBanner.moder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "adBanner")
public class AdBannerDto {

    @ApiModelProperty(value = "名称")
    String title;
    @ApiModelProperty(value = "图片地址")
    String imageUrl;
    @ApiModelProperty(value = "关联的h5网页")
    String refUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

}
