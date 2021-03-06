package com.zwdbj.server.adminserver.service.category.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "分类的基本信息")
public class AdBasicCategoryDto {
    @ApiModelProperty("分类ID")
    long id;
    @ApiModelProperty("分类名")
    String name;
    @ApiModelProperty("分类头像")
    String iconUrl;
    @ApiModelProperty("是否还有子分类。isHaveNextNode==true?'该分类下有子分类':'该分类下没有子分类'")
    boolean isHaveNextNode;
    @ApiModelProperty(value = "分类创建者ID，如果是系统内置分类，此字段保持为null")
    Long userId;
    @ApiModelProperty(value = "分类创建者，如果是系统内置分类，此字段保持为null")
    Long username;
    @ApiModelProperty(value = "分类类型 分类,-1:全部,0:宠物分类，1：线下商家服务分类2：商城产品分类")
    int type;
    @ApiModelProperty(value = "品种数")
    Long sonCount;
    @ApiModelProperty(value = "父类id,默认传0")
    Long parentId;
    @ApiModelProperty("状态 0:正常 1：下线")
    int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHaveNextNode() {
        return isHaveNextNode;
    }

    public void setHaveNextNode(boolean haveNextNode) {
        isHaveNextNode = haveNextNode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUsername() {
        return username;
    }

    public void setUsername(Long username) {
        this.username = username;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getSonCount() {
        return sonCount;
    }

    public void setSonCount(Long sonCount) {
        this.sonCount = sonCount;
    }
}
