package com.zwdbj.server.mobileapi.service.shop.nearbyShops.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索条件")
public class SearchInfo {
    @ApiModelProperty("起始页")
    int pageNo;
    @ApiModelProperty("行数")
    int rows;
    @ApiModelProperty(value = "搜索内容 可以为空")
    String search;
    @ApiModelProperty(value = "过滤 默认为all")
    String filter;
    @ApiModelProperty(value = "排序方式 distance 按距离排序 grade 按评分排序")
    String rank;
    @ApiModelProperty(value = "纬度")
    double lat;
    @ApiModelProperty(value = "经度")
    double lon;

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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
