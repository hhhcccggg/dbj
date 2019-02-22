package com.zwdbj.server.adminserver.service.shop.service.products.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索需要的商品信息")
public class SearchProducts {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "商品类型")
    private Long productType;
    @ApiModelProperty(value = "商品编码")
    private String numberId;
    @ApiModelProperty(value = "商品名称")
    private String name;
    @ApiModelProperty(value = "销量上限")
    private long salesUp;
    @ApiModelProperty(value = "销量下限")
    private long salseDown;
    @ApiModelProperty(value = "商品价格上限")
    private long priceUp;
    @ApiModelProperty(value = "商品价格下限")
    private long priceDown;
    @ApiModelProperty(value = "商品分组")
    private long productGroupId;

    @ApiModelProperty(value = "卖家编号",hidden = true)
    private long storeId;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSalesUp() {
        return salesUp;
    }

    public void setSalesUp(long salesUp) {
        this.salesUp = salesUp;
    }

    public long getSalseDown() {
        return salseDown;
    }

    public void setSalseDown(long salseDown) {
        this.salseDown = salseDown;
    }

    public long getPriceUp() {
        return priceUp;
    }

    public void setPriceUp(long priceUp) {
        this.priceUp = priceUp;
    }

    public long getPriceDown() {
        return priceDown;
    }

    public void setPriceDown(long priceDown) {
        this.priceDown = priceDown;
    }


    public long getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(long productGroupId) {
        this.productGroupId = productGroupId;
    }
}
