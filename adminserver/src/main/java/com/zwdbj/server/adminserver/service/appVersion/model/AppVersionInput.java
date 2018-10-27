package com.zwdbj.server.adminserver.service.appVersion.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "客户端输入的版本信息")
public class AppVersionInput {
    @ApiModelProperty(value = "app版本号,同一个平台不能有重复")
    String version;
    /*@ApiModelProperty(value = "app版本数字,判定是否更新依据此数字，同一个平台不能有重复")
    int versionNum;*/
    @ApiModelProperty(value = "客户端的类型 ,0:iOS,1:android")
    int platform;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }
}
