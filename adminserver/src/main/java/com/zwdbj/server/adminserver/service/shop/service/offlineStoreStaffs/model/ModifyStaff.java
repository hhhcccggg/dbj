package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "修改员工信息")
public class ModifyStaff {

    @ApiModelProperty(value = "员工id")
    long userId;
    @ApiModelProperty(value = "认证资质 暂时没有 预留")
    String qualification;
    @ApiModelProperty(value = "资质url 可以上传多个资质，需要截取")
    String url;

    @ApiModelProperty(value = "备注 ")
    String notes;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
