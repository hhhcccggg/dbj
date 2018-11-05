package com.zwdbj.server.adminserver.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "搜索视频")
public class SearchVideoAdInput {
    @ApiModelProperty(value = "视频状态，-1：所有，0：正常的,1:未审核,2:下架,3:待审核")
    int status;
    @ApiModelProperty(value = "关联的商品，-1：所有，其他值为真正关联的商品数")
    int isLinkProduct;
    @ApiModelProperty(value = "视频标签，-1：所有，0：没有标签1：有标签")
    int isHaveTag;
    @ApiModelProperty(value = "关键字，可以查询标题")
    String keywords;
    @ApiModelProperty(value = "上传者的角色名称:admin market finance normal null")
    String roleName;
    @ApiModelProperty(value = "排序规则,1:浏览次数,2:点赞次数,3:评论次数,4:转发次数,5:举报次数")
    int orderRule;

    public int getOrderRule() {
        return orderRule;
    }

    public void setOrderRule(int orderRule) {
        this.orderRule = orderRule;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsLinkProduct() {
        return isLinkProduct;
    }

    public void setIsLinkProduct(int isLinkProduct) {
        this.isLinkProduct = isLinkProduct;
    }

    public int getIsHaveTag() {
        return isHaveTag;
    }

    public void setIsHaveTag(int isHaveTag) {
        this.isHaveTag = isHaveTag;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
