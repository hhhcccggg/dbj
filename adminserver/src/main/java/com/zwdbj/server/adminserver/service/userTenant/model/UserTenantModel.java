package com.zwdbj.server.adminserver.service.userTenant.model;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class UserTenantModel implements Serializable {
    @ApiModelProperty(value = "租户id")
    private long id;
    @ApiModelProperty(value = "租户创建时间")
    private Date createTime;
    @ApiModelProperty(value = "租户的名字")
    private String name;
    @ApiModelProperty(value = "租户的联系人")
    private String nickName;
    @ApiModelProperty(value = "租户联系人的手机号码")
    private String phone;
    @ApiModelProperty(value = "租户标识，要保持唯一性")
    private String identifyName;
    @ApiModelProperty(value = "电商系统中的商户ID")
    private long legalSubjectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getIdentifyName() {
        return identifyName;
    }

    public void setIdentifyName(String identifyName) {
        this.identifyName = identifyName;
    }

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }
}
