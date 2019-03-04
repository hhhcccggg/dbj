package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索员工")
public class SearchStaffInfo {
    @ApiModelProperty(value = "搜索条件")
    private String search;
    @ApiModelProperty(value = "0 所有 1员工 2代言人")
    private int range;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
