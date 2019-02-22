package com.zwdbj.server.adminserver.service.shop.service.offlineStoreStaffs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ApiModel(description = "搜索代言人信息")
public class SearchSuperStarInput {
    @NotNull
    @ApiModelProperty(value = "搜索条件,仅支持昵称，手机号搜索")
    private String search;
    @ApiModelProperty(value = "按字段排序 videos作品数 totalHearts 点赞数 totalFans粉丝数 默认按照姓名排序")
    private String rank;
    @ApiModelProperty(value = "排序规则 默认 desc 降序 asc 升序")
    private String sort;
    @Min(value = 0)
    @ApiModelProperty(value = "起始页")
    private int pageNo;
    @Max(30)
    @ApiModelProperty(value = "每页显示多少行")
    private int rows;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
