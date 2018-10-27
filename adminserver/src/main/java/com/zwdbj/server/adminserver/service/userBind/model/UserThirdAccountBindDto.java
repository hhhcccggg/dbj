package com.zwdbj.server.adminserver.service.userBind.model;


import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class UserThirdAccountBindDto {
  @ApiModelProperty(value = "编号")
  private long id;
  @ApiModelProperty(value = "用户Id")
  private long userId;
  @ApiModelProperty(value = "第三方平台的开放用户ID")
  private String thirdOpenId;
  @ApiModelProperty(value = "4:微信8:QQ16:微博")
  private int accountType;
  @ApiModelProperty(value = "第三方平台的token")
  private String accessToken;
  @ApiModelProperty(value = "过期时间，单位秒")
  private long exipreIn;
  private Date createTime;
  @ApiModelProperty(value = "昵称")
  private String nickName;

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }



  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public String getThirdOpenId() {
    return thirdOpenId;
  }

  public void setThirdOpenId(String thirdOpenId) {
    this.thirdOpenId = thirdOpenId;
  }


  public int getAccountType() {
    return accountType;
  }

  public void setAccountType(int accountType) {
    this.accountType = accountType;
  }


  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }


  public long getExipreIn() {
    return exipreIn;
  }

  public void setExipreIn(long exipreIn) {
    this.exipreIn = exipreIn;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

}
