package com.zwdbj.server.mobileapi.service.youzan.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索有赞商品请求")
public class YZSearchItemInput {
    @ApiModelProperty(value = "作为查询条件的商品ID列表，以逗号分隔，如1,2")
    String ids;
    @ApiModelProperty(value = "有赞支持的搜索关键字,可以为空")
    String q;
    @ApiModelProperty(value = "页码，从1开始")
    int pageNo;
    @ApiModelProperty(value = "每页显示数量")
    int rows;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
