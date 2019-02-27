package com.zwdbj.server.adminserver.service.shop.service.offlineStoreExtraServices.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "门店其他服务")
public class OfflineStoreExtraServices {
    @ApiModelProperty("id")
    long id;
    @ApiModelProperty(value = "商家id")
    Long storeId;
    @ApiModelProperty(value = "额外服务id")
    Long extraServiceId;
    /// <summary>
    /// 0:正常1：非正常
    /// </summary>
    /// <value>The status.</value>
    @ApiModelProperty(value = "服务状态")
    int status;
    @ApiModelProperty(value = "服务名称")
    private String categoryName;
    @ApiModelProperty(value = "")
    String notes;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getExtraServiceId() {
        return extraServiceId;
    }

    public void setExtraServiceId(Long extraServiceId) {
        this.extraServiceId = extraServiceId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
