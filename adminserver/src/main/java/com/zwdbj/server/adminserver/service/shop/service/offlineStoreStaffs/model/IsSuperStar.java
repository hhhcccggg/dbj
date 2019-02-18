package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "设为/取消代言人")
public class IsSuperStar {
    @ApiModelProperty(value = "设为/取消代言人 true/false")
    boolean isSuperStar;
    @ApiModelProperty(value = "员工id")
    long staffId;

    public boolean isSuperStar() {
        return isSuperStar;
    }

    public void setSuperStar(boolean superStar) {
        isSuperStar = superStar;
    }

    public long getStaffId() {
        return staffId;
    }

    public void setStaffId(long staffId) {
        this.staffId = staffId;
    }
}
