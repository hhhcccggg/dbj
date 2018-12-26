package com.zwdbj.server.shop_admin_service.businessSellers.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("店铺信息")
public class BusinessSellerDto {
    @ApiModelProperty(value = "店铺id")
    long id;
    @ApiModelProperty(value = "创建时间")
    Date createTime;
    @ApiModelProperty(value = "店铺名称")
    String name;
    @ApiModelProperty(value = "副标题")
    String subName;
    @ApiModelProperty(value = "店铺的LOGO的URL")
    String logoUrl;
    @ApiModelProperty(value = "卖点的名称")
    String marketName;
    @ApiModelProperty(value = "店铺的状态，0：正常，1：关闭")
    int status;
    @ApiModelProperty(value = "店铺是否通过审核")
    boolean isReviewed;
    @ApiModelProperty(value = "店铺是否停止服务")
    boolean isStopService;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public boolean isStopService() {
        return isStopService;
    }

    public void setStopService(boolean stopService) {
        isStopService = stopService;
    }
}
