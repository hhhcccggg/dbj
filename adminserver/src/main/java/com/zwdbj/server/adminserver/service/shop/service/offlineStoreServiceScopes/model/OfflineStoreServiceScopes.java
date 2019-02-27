package com.zwdbj.server.adminserver.service.shop.service.offlineStoreServiceScopes.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "线下门店服务范围")
public class OfflineStoreServiceScopes {
    @ApiModelProperty(value = "id")
    Long id;
    /// 就是商户ID
    /// </summary>
    /// <value>The offline store identifier.</value>
    @ApiModelProperty(value = "商户id")
    long storeId;
    /// <summary>
    /// 分类ID
    /// </summary>
    /// <value>The service scope identifier.</value>
    @ApiModelProperty(value = "服务分类ID")
    private long serviceScopeId;
    @ApiModelProperty(value = "服务名称")
    private String categoryName;
    @ApiModelProperty(value = "notes")
    String notes;
    /// <summary>
    /// 0:正常1:下线
    /// </summary>
    /// <value>The status.</value>
    @ApiModelProperty(value = "营业状态")
    int  status;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getServiceScopeId() {
        return serviceScopeId;
    }

    public void setServiceScopeId(long serviceScopeId) {
        this.serviceScopeId = serviceScopeId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
