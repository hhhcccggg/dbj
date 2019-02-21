package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel(description = "单个商品展示")
public class ProductlShow {

    @ApiModelProperty(value = "id")
    Long id;

    @ApiModelProperty(value = "商品类型")
    Long productType;

    @ApiModelProperty(value = "产品详细类型 /// DELIVERY: 实物产品 NODELIVERY:虚拟商品(不需要物流)\n" +
            "        /// CARD:卡券(服务中套餐)，关联[ProductCard]表 CASHCOUPON:代金券，类似70抵100，关联[ProductCashCoupon]表")
    String productDetailType;

    @ApiModelProperty(value = "商品名称")
    String name;

    @ApiModelProperty(value = "库存")
    long inventory;

    @ApiModelProperty(value = "销量")
    long sales;

    @ApiModelProperty(value = "商品详情")
    String detailDescription;

    @ApiModelProperty(value = "是否限购 0：表示不限购 大于0数字表示没人只能买商品的数量")
    int limitPerPerson;

    @ApiModelProperty(value = "原价")
    long originalPrice;

    @ApiModelProperty(value = "促销价")
    Long promotionPrice;

    @ApiModelProperty(value = "节假日是否可用")
    boolean festivalCanUse;

    @ApiModelProperty(value = "支持金币兑换购买")
    boolean supportCoin;

    @ApiModelProperty(value = "品牌ID")
    Long brandId;

    @ApiModelProperty(value = "种类ID")
    Long categoryId;

    @ApiModelProperty(value = "商品图片地址")
    String imageUrls;

    @ApiModelProperty(value = "已经兑换的头像列表")
    List<String> exchangeList;

    @ApiModelProperty(value = "productSKUId")
    long productSKUId;

    @ApiModelProperty(value = "规则说明")
    String ruleDescription;

    @ApiModelProperty(value = "预约信息")
    private String appointment;

    @ApiModelProperty(value = "是否与其他优惠券共用")
    private boolean stackUse;

    @ApiModelProperty(value = "购买说明")
    List<Map<String,String>> specification;

    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    @ApiModelProperty(value = "storeId")
    private long storeId;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public boolean isStackUse() {
        return stackUse;
    }

    public void setStackUse(boolean stackUse) {
        this.stackUse = stackUse;
    }

    public List<Map<String,String>> getSpecification() {
        return specification;
    }

    public void setSpecification(List<Map<String,String>> specification) {
        this.specification = specification;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public long getProductSKUId() {
        return productSKUId;
    }

    public void setProductSKUId(long productSKUId) {
        this.productSKUId = productSKUId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public String getProductDetailType() {
        return productDetailType;
    }

    public void setProductDetailType(String productDetailType) {
        this.productDetailType = productDetailType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
        this.inventory = inventory;
    }

    public long getSales() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales = sales;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public int getLimitPerPerson() {
        return limitPerPerson;
    }

    public void setLimitPerPerson(int limitPerPerson) {
        this.limitPerPerson = limitPerPerson;
    }

    public long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public boolean isFestivalCanUse() {
        return festivalCanUse;
    }

    public void setFestivalCanUse(boolean festivalCanUse) {
        this.festivalCanUse = festivalCanUse;
    }

    public boolean isSupportCoin() {
        return supportCoin;
    }

    public void setSupportCoin(boolean supportCoin) {
        this.supportCoin = supportCoin;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getExchangeList() {
        return exchangeList;
    }

    public void setExchangeList(List<String> exchangeList) {
        this.exchangeList = exchangeList;
    }
}
