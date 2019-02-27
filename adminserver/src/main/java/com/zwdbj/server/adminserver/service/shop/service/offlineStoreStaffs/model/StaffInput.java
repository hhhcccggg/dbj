package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "创建员工/代言人")
public class StaffInput {
    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "员工姓名")
    String fullName;
    @Length(min = 11, max = 11)
    @ApiModelProperty(value = "手机号")
    String phone;
    @ApiModelProperty(value = "资质类型")
    String qualification;
    @ApiModelProperty(value = "资质url 可能有多个url 需要截取")
    String url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
