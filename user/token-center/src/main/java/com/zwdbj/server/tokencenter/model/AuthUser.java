package com.zwdbj.server.tokencenter.model;

import java.io.Serializable;

public class AuthUser implements Serializable {
    private String id;
    private String username;
    private String email;
    private String phone;
    private boolean isLocked;
    private String[] roles;
    private String[] permissions;
    /**
     * 用户类型
     * NORMAL:普通用户，直接是产品的用户，来自于APP、H5、小程序等渠道
     * PLATFORM: 平台用户，主要是自由平台的用户
     * BUSINESS: 商家用户
     */
    private String type;
    /**
     * 当前租户ID
     */
    private long tenantId;
    /**
     * 当前租户的标识
     */
    private String identifyTenantName;
    /**
     * 当前商户id
     */
    private long legalSubjectId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public long getTenantId() {
        return tenantId;
    }

    public void setTenantId(long tenantId) {
        this.tenantId = tenantId;
    }

    public String getIdentifyTenantName() {
        return identifyTenantName;
    }

    public void setIdentifyTenantName(String identifyTenantName) {
        this.identifyTenantName = identifyTenantName;
    }

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }
}
