package com.zwdbj.server.adminserver.service.shop.service.store.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索店铺的参数")
public class StoreSearchInput {
    @ApiModelProperty(value = "搜索的关键字,店铺名称")
    private String keyWords;

    @ApiModelProperty(value = "营业状态,0为全部,1为营业,2为休息")
    private int stopService;
    @ApiModelProperty(value = "店铺是否通过审核,0为全部,1通过,2拒绝")
    private int reviewed;
    @ApiModelProperty(value = "店铺状态是否停止服务 -1 全部 0正常 1关闭")
    private int status;

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public int getStopService() {
        return stopService;
    }

    public void setStopService(int stopService) {
        this.stopService = stopService;
    }

    public int getReviewed() {
        return reviewed;
    }

    public void setReviewed(int reviewed) {
        this.reviewed = reviewed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
