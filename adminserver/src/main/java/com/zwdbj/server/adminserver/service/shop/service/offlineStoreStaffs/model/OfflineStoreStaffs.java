package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = " 门店员工，或者代言人")
public class OfflineStoreStaffs {
    @ApiModelProperty(value = "员工名称")
    String name;
    @ApiModelProperty(value = "线下门店id")
    long storeId;
    @ApiModelProperty(value = "联系电话")
    String phone;
    @ApiModelProperty(value = "用户id")
    long userId;
    /// <summary>
    /// 是否为代言人
    /// </summary>
    /// <value><c>true</c> if is super star; otherwise, <c>false</c>.</value>

    @ApiModelProperty(value = "是否成为代言人")
    boolean isSuperStar;
    @ApiModelProperty(value = "认证资质")
    String qualification;
    @ApiModelProperty(value = "添加时间")
    Date createTime;
    @ApiModelProperty(value = "备注")
    String notes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
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
