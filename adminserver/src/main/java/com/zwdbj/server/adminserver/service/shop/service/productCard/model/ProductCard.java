package com.zwdbj.server.adminserver.service.shop.service.productCard.model;

import com.zwdbj.server.adminserver.service.shop.service.products.model.CreateProducts;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "电子卡券虚拟商品信息")
public class ProductCard {

    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "节假日是否可用")
    boolean festivalCanUse;

    boolean isDeleted;

    @ApiModelProperty(value = "使用说明")
    String useInfo;

    @ApiModelProperty(value = "生效类型")
    String validType;

    @ApiModelProperty(value = "PAY_SPEC_HOUR_VALIDED:付款后指定小时生效")
    int specHoursValid;

    @ApiModelProperty(value = "生效后多少天内有效")
    int validDays;

    @ApiModelProperty(value = "生效后指定时间范围内生效")
    Date validStartTime;

    @ApiModelProperty(value = "生效后指定时间范围内生效")
    Date validEndTime;

    Long productId;

    @ApiModelProperty(value = "存在规格有特殊定制?")
    long productSKUId;

    public ProductCard() {
    }

    public ProductCard(CreateProducts createProducts, Long productId) {
        this.festivalCanUse = createProducts.isFestivalCanUse();
        this.validType = createProducts.getValidType();
        this.specHoursValid = createProducts.getSpecHoursValid();
        this.validDays = createProducts.getValidDays();
        this.validStartTime = createProducts.getValidStartTime();
        this.validEndTime = createProducts.getValidEndTime();
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isFestivalCanUse() {
        return festivalCanUse;
    }

    public void setFestivalCanUse(boolean festivalCanUse) {
        this.festivalCanUse = festivalCanUse;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getUseInfo() {
        return useInfo;
    }

    public void setUseInfo(String useInfo) {
        this.useInfo = useInfo;
    }

    public String getValidType() {
        return validType;
    }

    public void setValidType(String validType) {
        this.validType = validType;
    }

    public int getSpecHoursValid() {
        return specHoursValid;
    }

    public void setSpecHoursValid(int specHoursValid) {
        this.specHoursValid = specHoursValid;
    }

    public int getValidDays() {
        return validDays;
    }

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }

    public Date getValidStartTime() {
        return validStartTime;
    }

    public void setValidStartTime(Date validStartTime) {
        this.validStartTime = validStartTime;
    }

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }

    public long getProductSKUId() {
        return productSKUId;
    }

    public void setProductSKUId(long productSKUId) {
        this.productSKUId = productSKUId;
    }
}
