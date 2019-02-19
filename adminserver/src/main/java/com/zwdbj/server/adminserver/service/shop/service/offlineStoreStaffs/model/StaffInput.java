package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "创建员工/代言人")
public class StaffInput {
    @ApiModelProperty(value = "员工姓名")
    String nickName;
    @ApiModelProperty(value = "手机号")
    String phone;
    @ApiModelProperty(value = "是否代言")
    boolean isSuperStar;
    @ApiModelProperty(value = "认证资质")
    String qualification;
    @ApiModelProperty(value = "备注 是否为店主")
    boolean isSuper;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean aSuper) {
        isSuper = aSuper;
    }
}
