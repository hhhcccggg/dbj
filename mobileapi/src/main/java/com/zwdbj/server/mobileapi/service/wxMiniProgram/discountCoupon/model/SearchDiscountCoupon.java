package com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.model;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.discountCoupon.common.DiscountType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(description = "查询优惠券")
public class SearchDiscountCoupon {

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "优惠的形式 CASH: 现金优惠 DISCOUNT:折扣")
    private DiscountType discountType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生效时间段")
    private Date validStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生效时间段")
    private Date validEndTime;

    @ApiModelProperty(value = "店铺ID" , hidden = true)
    private long storeId;

    @ApiModelProperty(value = "商家ID" , hidden = true)
    private long legalSubjectId;

    @ApiModelProperty(value = "用户ID" , hidden = true)
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
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

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getLegalSubjectId() {
        return legalSubjectId;
    }

    public void setLegalSubjectId(long legalSubjectId) {
        this.legalSubjectId = legalSubjectId;
    }
}