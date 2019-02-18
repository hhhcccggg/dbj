package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索员工")
public class SearchStaffInfo {
    @ApiModelProperty(value = "搜索条件(仅支持姓名搜索)")
    private String search;
    @ApiModelProperty(value = "是否代言")
    private boolean isSuperStar;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean isSuperStar() {
        return isSuperStar;
    }

    public void setSuperStar(boolean superStar) {
        isSuperStar = superStar;
    }
}
