package com.zwdbj.server.adminserver.service.shop.service.ProductAttris.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "商品属性规格")
public class ProductAttris {
    @ApiModelProperty(value = "id")
    Long id;
    @ApiModelProperty(value = "属性名")
    String name;
    /// 属性标识
    /// </summary>
    /// <value>The identify identifier.</value>
    @ApiModelProperty(value = "属性标识'")
    String identifyId;
    /// <summary>
    /// 商品分类
    /// </summary>
    /// <value>The category identifier.</value>
    @ApiModelProperty(value = "商品分类")
    Long categoryId;
    /// <summary>
    /// 父属性
    /// </summary>
    /// <value>The parent identifier.</value>

    @ApiModelProperty(value = "父属性")
    Long parentId;
    /// <summary>
    /// 属性分组
    /// </summary>
    /// <value>The group identifier.</value>
    @ApiModelProperty(value = "属性分组")
    Long groupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifyId() {
        return identifyId;
    }

    public void setIdentifyId(String identifyId) {
        this.identifyId = identifyId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}