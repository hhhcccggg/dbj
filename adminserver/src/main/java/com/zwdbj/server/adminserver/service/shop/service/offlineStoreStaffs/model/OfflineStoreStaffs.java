package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = " 门店员工，或者代言人")
public class OfflineStoreStaffs {

    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "线下门店id")
    long offlineStoreId;
    @ApiModelProperty(value = "用户id")
    long userId;
    /// <summary>
    /// 是否为代言人
    /// </summary>
    /// <value><c>true</c> if is super star; otherwise, <c>false</c>.</value>

    @ApiModelProperty(value = "是否成为代言人")
    boolean isSuperStar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getOfflineStoreId() {
        return offlineStoreId;
    }

    public void setOfflineStoreId(long offlineStoreId) {
        this.offlineStoreId = offlineStoreId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isSuperStar() {
        return isSuperStar;
    }

    public void setSuperStar(boolean superStar) {
        isSuperStar = superStar;
    }
}
