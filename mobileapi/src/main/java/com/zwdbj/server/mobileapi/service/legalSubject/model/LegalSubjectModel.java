package com.zwdbj.server.mobileapi.service.legalSubject.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "关于商家的信息")
public class LegalSubjectModel {
    @ApiModelProperty(value = "id")
    private long id;
    @ApiModelProperty(value = "商家创建时间")
    private Date createTime;
    @ApiModelProperty(value = "商家的名字")
    private String name;
    @ApiModelProperty(value = "副标题")
    private String subName;
    @ApiModelProperty(value = "营销标题")
    private String marketName;
    @ApiModelProperty(value = "")
    private String description;
    @ApiModelProperty(value = "企业编号")
    private String corpId;
    @ApiModelProperty(value = "法人代表姓名")
    private String leagalRepresentativeName;
    @ApiModelProperty(value = "法人代表身份证号码")
    private String leagalRepresentativeID;
    @ApiModelProperty(value = "logo地址")
    private String logoUrl;
    @ApiModelProperty(value = "联系地址")
    private String contactAddress;
    @ApiModelProperty(value = "企业注册地址")
    private String regAddress;
    @ApiModelProperty(value = "企业注册地城市编号")
    private int cityId;
    @ApiModelProperty(value = "城市的等级")
    private String cityLevel;
    @ApiModelProperty(value = "联系人姓名")
    private String contactName;
    @ApiModelProperty(value = "联系人手机号码")
    private String contactPhone;
    @ApiModelProperty(value = "企业qq")
    private String qq;
    @ApiModelProperty(value = "企业微信")
    private String wechat;
    @ApiModelProperty(value = "企业状态 0：正常1：关闭")
    private int status;
    @ApiModelProperty(value = "企业是否已审核通过")
    private boolean reviewed;
    @ApiModelProperty(value = "审核说明")
    private String reviewMsg;
    @ApiModelProperty(value = "企业类型 P:个人；B:企业")
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getLeagalRepresentativeName() {
        return leagalRepresentativeName;
    }

    public void setLeagalRepresentativeName(String leagalRepresentativeName) {
        this.leagalRepresentativeName = leagalRepresentativeName;
    }

    public String getLeagalRepresentativeID() {
        return leagalRepresentativeID;
    }

    public void setLeagalRepresentativeID(String leagalRepresentativeID) {
        this.leagalRepresentativeID = leagalRepresentativeID;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityLevel() {
        return cityLevel;
    }

    public void setCityLevel(String cityLevel) {
        this.cityLevel = cityLevel;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getReviewMsg() {
        return reviewMsg;
    }

    public void setReviewMsg(String reviewMsg) {
        this.reviewMsg = reviewMsg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
