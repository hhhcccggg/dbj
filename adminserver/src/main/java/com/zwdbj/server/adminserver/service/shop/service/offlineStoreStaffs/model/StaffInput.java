package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "创建员工/代言人")
public class StaffInput {
    @ApiModelProperty(value = "员工姓名")
    String fullName;
    @ApiModelProperty(value = "手机号")
    String phone;
    @ApiModelProperty(value = "是否代言")
    boolean isSuperStar;
    @ApiModelProperty(value = "认证资质")
    String qualification;
    @ApiModelProperty(value = "备注 ")
    String notes;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSuperStar() {
        return isSuperStar;
    }

    public void setSuperStar(boolean superStar) {
        isSuperStar = superStar;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
