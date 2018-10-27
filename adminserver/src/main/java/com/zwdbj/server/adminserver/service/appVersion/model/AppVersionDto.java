package com.zwdbj.server.adminserver.service.appVersion.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "APP版本的输出字段")
public class AppVersionDto {
    @ApiModelProperty(value = "app版本id")
    Long id;
    @ApiModelProperty(value = "此版本的创建时间")
    Date createTime;
    @ApiModelProperty(value = "app版本号,同一个平台不能有重复")
    String version;
    @ApiModelProperty(value = "app此版本的标题")
    String title;
    @ApiModelProperty(value = "app此版本的更新描述")
    String description;
    @ApiModelProperty(value = "客户端的类型 ,0:iOS,1:android")
    int platform;
    @ApiModelProperty(value = "app此版本的下载链接")
    String downloadUrl;
    @ApiModelProperty(value = "app此版本升级类型,0：选择升级1：强制升级")
    int upgradeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(int upgradeType) {
        this.upgradeType = upgradeType;
    }
}
