package com.zwdbj.server.adminserver.service.shop.service.store.model;

import com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model.OfflineStoreServiceScopes;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class StoreSimpleInfo {
    @ApiModelProperty(value = "店铺id")
    private long id;
    @ApiModelProperty(value = "店铺名称")
    private String name;
    @ApiModelProperty(value = "服务范围")//后面再整理
    private String serviceScopes;
    @ApiModelProperty(value = "店铺状态是否停止服务 0 正常1关闭2审核中")
    private int status;
    @ApiModelProperty(value = "是否通过审核")
    private boolean reviewed;
    @ApiModelProperty(value = "店铺营业状态,是否停止服务")
    private boolean stopService;
    @ApiModelProperty(value = "店铺提供的资质")
    private String shopAptitude;
    @ApiModelProperty(value = "商家的legalSubjectId")
    private long legalSubjectId;

    public String getServiceScopes() {
        return serviceScopes;
    }

    public void setServiceScopes(String serviceScopes) {
        this.serviceScopes = serviceScopes;
    }

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }

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

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public boolean isStopService() {
        return stopService;
    }

    public void setStopService(boolean stopService) {
        this.stopService = stopService;
    }

    public String getShopAptitude() {
        return shopAptitude;
    }

    public void setShopAptitude(String shopAptitude) {
        this.shopAptitude = shopAptitude;
    }
}
