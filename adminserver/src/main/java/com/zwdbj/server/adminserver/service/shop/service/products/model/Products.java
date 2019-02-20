package com.zwdbj.server.adminserver.service.shop.service.products.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "商品信息")
public class Products {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "商品类型")
    Long productType;
    @ApiModelProperty(value = "产品详细类型")
    String productDetailType;
    @ApiModelProperty(value = "商品编码")
    String numberId;
    @ApiModelProperty(value = "商品名称")
    String name;
    String subName;
    String searchName;
    String marketName;
    String sellerPoint;
    Long categoryId;
    String categoryLevel;
    boolean isDeleted;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "品牌ID")
    Long brandId;
    @ApiModelProperty(value = "分享描述")
    String shareDesc;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "卖家编号")
    long storeId;
    @ApiModelProperty(value = "评论数")
    long commentCount;
    @ApiModelProperty(value = "评分")
    long grade;
    @ApiModelProperty(value = "销量")
    long sales;
    @ApiModelProperty(value = "库存")
    long inventory;
    @ApiModelProperty(value = "原价")
    long originalPrice;
    @ApiModelProperty(value = "促销价")
    long promotionPrice;
    @ApiModelProperty(value = "商品价格上限")
    long priceUp;
    @ApiModelProperty(value = "商品价格下限")
    long priceDown;
    @ApiModelProperty(value = "商品图片地址")
    String imageUrls;
    @ApiModelProperty(value = "商品视频URL")
    String videoUrl;
    @ApiModelProperty(value = "商品分组")
    long productGroupId;
    @ApiModelProperty(value = "是否参与会员打折")
    boolean isJoinMemberDiscount;
    @ApiModelProperty(value = "是否需要物流")
    boolean isNeedDelivery;
    @ApiModelProperty("通用物流价格")
    long universalDeliveryPrice;
    @ApiModelProperty(value = "物流模板")
    long deliverytemplateId;
    @ApiModelProperty(value = "是否上架")
    boolean publish;
    @ApiModelProperty(value = "上架时间")
    long specifyPublishTime;
    @ApiModelProperty(value = "商品详情")
    String detailDescription;
    @ApiModelProperty(value = "重量Kg")
    long weight;
    @ApiModelProperty(value = "备注")
    String notes;
    @ApiModelProperty(value = "是否限购")
    int limitPerPerson;
    @ApiModelProperty(value = "支持金币兑换购买")
    boolean supportCoin;
    @ApiModelProperty(value = "规则说明")
    String ruleDescription;

    @ApiModelProperty(value = "叠加使用")
    boolean overlay;

    public boolean isOverlay() {
        return overlay;
    }

    public void setOverlay(boolean overlay) {
        this.overlay = overlay;
    }

    public long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public long getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(long promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public boolean isSupportCoin() {
        return supportCoin;
    }

    public void setSupportCoin(boolean supportCoin) {
        this.supportCoin = supportCoin;
    }

    public String getProductDetailType() {
        return productDetailType;
    }

    public void setProductDetailType(String productDetailType) {
        this.productDetailType = productDetailType;
    }

    public int getLimitPerPerson() {
        return limitPerPerson;
    }

    public void setLimitPerPerson(int limitPerPerson) {
        this.limitPerPerson = limitPerPerson;
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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getSellerPoint() {
        return sellerPoint;
    }

    public void setSellerPoint(String sellerPoint) {
        this.sellerPoint = sellerPoint;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getGrade() {
        return grade;
    }

    public void setGrade(long grade) {
        this.grade = grade;
    }

    public long getSales() {
        return sales;
    }

    public void setSales(long sales) {
        this.sales = sales;
    }

    public long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
        this.inventory = inventory;
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

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public long getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(long productGroupId) {
        this.productGroupId = productGroupId;
    }

    public boolean isJoinMemberDiscount() {
        return isJoinMemberDiscount;
    }

    public void setJoinMemberDiscount(boolean joinMemberDiscount) {
        isJoinMemberDiscount = joinMemberDiscount;
    }

    public boolean isNeedDelivery() {
        return isNeedDelivery;
    }

    public void setNeedDelivery(boolean needDelivery) {
        isNeedDelivery = needDelivery;
    }

    public long getUniversalDeliveryPrice() {
        return universalDeliveryPrice;
    }

    public void setUniversalDeliveryPrice(long universalDeliveryPrice) {
        this.universalDeliveryPrice = universalDeliveryPrice;
    }

    public long getDeliverytemplateId() {
        return deliverytemplateId;
    }

    public void setDeliverytemplateId(long deliverytemplateId) {
        this.deliverytemplateId = deliverytemplateId;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public long getSpecifyPublishTime() {
        return specifyPublishTime;
    }

    public void setSpecifyPublishTime(long specifyPublishTime) {
        this.specifyPublishTime = specifyPublishTime;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
