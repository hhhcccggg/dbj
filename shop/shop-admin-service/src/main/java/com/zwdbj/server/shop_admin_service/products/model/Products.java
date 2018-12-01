package com.zwdbj.server.shop_admin_service.products.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "商品信息")
public class Products {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "商品类型")
    Long productType;
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
    @ApiModelProperty(value = "品牌ID")
    Long brandId;
    @ApiModelProperty(value = "分享描述")
    String shareDesc;
    @ApiModelProperty(value = "卖家编号")
    long sellerId;
    @ApiModelProperty(value = "评论数")
    long commentCount;
    @ApiModelProperty(value = "评分")
    float grade;
    @ApiModelProperty(value = "销量")
    long sales;
    @ApiModelProperty(value = "库存")
    long inventory;
    @ApiModelProperty(value = "商品价格上限")
    float priceUp;
    @ApiModelProperty(value = "商品价格下限")
    float priceDown;
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
    float universalDeliveryPrice;
    @ApiModelProperty(value = "物流模板")
    long deliverytemplateId;
    @ApiModelProperty(value = "是否上架")
    boolean isPublish;
    @ApiModelProperty(value = "上架时间")
    long specifyPublishTime;
    @ApiModelProperty(value = "商品详情")
    String detailDescription;
    @ApiModelProperty(value = "重量Kg")
    float weight;
    @ApiModelProperty(value = "备注")
    String notes;

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

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
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

    public float getPriceUp() {
        return priceUp;
    }

    public void setPriceUp(float priceUp) {
        this.priceUp = priceUp;
    }

    public float getPriceDown() {
        return priceDown;
    }

    public void setPriceDown(float priceDown) {
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

    public float getUniversalDeliveryPrice() {
        return universalDeliveryPrice;
    }

    public void setUniversalDeliveryPrice(float universalDeliveryPrice) {
        this.universalDeliveryPrice = universalDeliveryPrice;
    }

    public long getDeliverytemplateId() {
        return deliverytemplateId;
    }

    public void setDeliverytemplateId(long deliverytemplateId) {
        this.deliverytemplateId = deliverytemplateId;
    }

    public boolean isPublish() {
        return isPublish;
    }

    public void setPublish(boolean publish) {
        isPublish = publish;
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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
