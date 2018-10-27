package com.zwdbj.server.mobileapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "接口统一分页相应结构")
public class ResponsePageInfoData<T> extends ResponseData<T> {
    @ApiModelProperty(value = "总数据量")
    protected long totalDatas;

    public ResponsePageInfoData(int code, String msg, T data, long totalDatas) {
        super(code, msg, data);
        this.totalDatas = totalDatas;
    }

    public long getTotalDatas() {
        return totalDatas;
    }

    public void setTotalDatas(long totalDatas) {
        this.totalDatas = totalDatas;
    }
}
