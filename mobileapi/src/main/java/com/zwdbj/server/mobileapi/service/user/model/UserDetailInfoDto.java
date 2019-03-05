package com.zwdbj.server.mobileapi.service.user.model;


import com.zwdbj.server.utility.common.shiro.JWTUtil;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class UserDetailInfoDto implements Serializable {

  private long id;
  private java.util.Date createTime;
  @ApiModelProperty(value = "用户账号/用户编号/类似抖音ID称谓")
  private String username;
  private String nickName;
  private String avatarUrl;
  private String phone;
  private String email;
  @ApiModelProperty(value = "用户的签名")
  private String signature;
  @ApiModelProperty(value = "环信账号")
  private String hxUserName;
  @ApiModelProperty(value = "环信密码")
  private String hxPwd;
  @ApiModelProperty(value = "账号是否已经被锁定，不能使用")
  private boolean isLocked;
  @ApiModelProperty(value = "电子邮件地址是否认证")
  private boolean isEmailVerification;
  @ApiModelProperty(value = "手机号码是否认证")
  private boolean isPhoneVerification;
  @ApiModelProperty(value = "是否设置密码")
  private boolean isSetPWD;
  @ApiModelProperty(value = "小饼干总数")
  private long coins;
  @ApiModelProperty(value = "是否可以开启直播功能")
  private boolean isLivingOpen;
  @ApiModelProperty(value = "是否有观看直播的权限")
  private boolean isLivingWatch;
  @ApiModelProperty(value = "当前是否正在直播")
  private boolean isLiving;
  @ApiModelProperty(value = "如果正在直播，当前的直播ID")
  private long livingId;
  @ApiModelProperty(value = "累计获得的点赞")
  private long totalHearts;
  @ApiModelProperty(value = "视频累计获得的点赞")
  private long totalVideosHearts;
  @ApiModelProperty(value = "宠物累计获得的点赞")
  private long totalPetsHearts;
  @ApiModelProperty(value = "总粉丝数")
  private long totalFans;
  @ApiModelProperty(value = "我关注的总数")
  private long totalMyFocuses;
  @ApiModelProperty(value = "地址/城市")
  private String address;
  @ApiModelProperty(value = "累计被举报的次数")
  private int complainCount;
  @ApiModelProperty(value = "账号是否已被审核")
  private boolean isReviewed;
  private double latitude;
  @ApiModelProperty(value = "登录类别>>1：手机号2：账号4：微信8：qq16：微博")
  private int loginType;
  private double longitude;
  @ApiModelProperty(value = "性别>>0：未知1：男2：女3：保密")
  private int sex;
  @ApiModelProperty(value = "宠物总数")
  private int petCount;
  @ApiModelProperty(value = "出生年月")
  private Date birthday;
  @ApiModelProperty(value = "年龄")
  private int age;
  @ApiModelProperty(value = "累计总视频数")
  private int videoCount;
  @ApiModelProperty(value = "累计直播过总数")
  private int liveCount;
  @ApiModelProperty(value = "商城用户相关信息")
  UserShopInfoDto shopInfoDto;
  @ApiModelProperty(value = "用户的收藏数")
  private int favoriteNums;

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public boolean isPhoneVerification() {
    return isPhoneVerification;
  }

  public void setPhoneVerification(boolean phoneVerification) {
    isPhoneVerification = phoneVerification;
  }

  public boolean isSetPWD() {
    return isSetPWD;
  }

  public void setSetPWD(boolean setPWD) {
    isSetPWD = setPWD;
  }

  public boolean isReviewed() {
    return isReviewed;
  }

  public void setReviewed(boolean reviewed) {
    isReviewed = reviewed;
  }

  public long getTotalVideosHearts() {
    return totalVideosHearts;
  }

  public void setTotalVideosHearts(long totalVideosHearts) {
    this.totalVideosHearts = totalVideosHearts;
  }

  public long getTotalPetsHearts() {
    return totalPetsHearts;
  }

  public void setTotalPetsHearts(long totalPetsHearts) {
    this.totalPetsHearts = totalPetsHearts;
  }

  public int getFavoriteNums() {
    return favoriteNums;
  }

  public void setFavoriteNums(int favoriteNums) {
    this.favoriteNums = favoriteNums;
  }

  public String getHxUserName() {
    return hxUserName;
  }

  public void setHxUserName(String hxUserName) {
    this.hxUserName = hxUserName;
  }

  public String getHxPwd() {
    long curUserId = JWTUtil.getCurrentId();
    if (curUserId ==0 || this.getId()!=curUserId) {
      return "";
    }
    return hxPwd;
  }

  public void setHxPwd(String hxPwd) {
    this.hxPwd = hxPwd;
  }


  public UserDetailInfoDto() {
    this.shopInfoDto = new UserShopInfoDto();
  }

  public int getVideoCount() {
    return videoCount;
  }

  public void setVideoCount(int videoCount) {
    this.videoCount = videoCount;
  }

  public int getLiveCount() {
    return liveCount;
  }

  public void setLiveCount(int liveCount) {
    this.liveCount = liveCount;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public java.util.Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.util.Date createTime) {
    this.createTime = createTime;
  }

  public boolean isLivingWatch() {
    return isLivingWatch;
  }

  public void setLivingWatch(boolean livingWatch) {
    isLivingWatch = livingWatch;
    isLivingWatch = true;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }


  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }



  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public boolean getIsLocked() {
    return isLocked;
  }

  public void setIsLocked(boolean isLocked) {
    this.isLocked = isLocked;
  }


  public boolean getIsEmailVerification() {
    return isEmailVerification;
  }

  public void setIsEmailVerification(boolean isEmailVerification) {
    this.isEmailVerification = isEmailVerification;
  }


  public boolean getIsPhoneVerification() {
    return isPhoneVerification;
  }

  public void setIsPhoneVerification(boolean isPhoneVerification) {
    this.isPhoneVerification = isPhoneVerification;
  }


  public long getCoins() {
    return coins;
  }

  public void setCoins(long coins) {
    this.coins = coins;
  }


  public boolean getIsLivingOpen() {
    return isLivingOpen;
  }

  public void setIsLivingOpen(boolean isLivingOpen) {
    this.isLivingOpen = isLivingOpen;
    this.isLivingOpen = true;
  }


  public boolean getIsLiving() {
    return isLiving;
  }

  public void setIsLiving(boolean isLiving) {
    this.isLiving = isLiving;
  }


  public long getLivingId() {
    return livingId;
  }

  public void setLivingId(long livingId) {
    this.livingId = livingId;
  }


  public long getTotalHearts() {
    return totalHearts;
  }

  public void setTotalHearts(long totalHearts) {
    this.totalHearts = totalHearts;
  }


  public long getTotalFans() {
    return totalFans;
  }

  public void setTotalFans(long totalFans) {
    this.totalFans = totalFans;
  }


  public long getTotalMyFocuses() {
    return totalMyFocuses;
  }

  public void setTotalMyFocuses(long totalMyFocuses) {
    this.totalMyFocuses = totalMyFocuses;
  }



  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public int getComplainCount() {
    return complainCount;
  }

  public void setComplainCount(int complainCount) {
    this.complainCount = complainCount;
  }


  public boolean getIsReviewed() {
    return isReviewed;
  }

  public void setIsReviewed(boolean isReviewed) {
    this.isReviewed = isReviewed;
  }


  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }


  public int getLoginType() {
    return loginType;
  }

  public void setLoginType(int loginType) {
    this.loginType = loginType;
  }


  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }





  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public int getPetCount() {
    return petCount;
  }

  public void setPetCount(int petCount) {
    this.petCount = petCount;
  }


  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
    if (birthday ==null) return;

    //计算年龄
    int age = 0;
    try {
      Calendar now = Calendar.getInstance();
      now.setTime(new Date());// 当前时间

      Calendar birth = Calendar.getInstance();
      birth.setTime(birthday);

      if (birth.after(now)) {
        age = 0;
      } else {
        age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
          age += 1;
        }
      }
    } catch (Exception e) {}
    this.setAge(age);
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public UserShopInfoDto getShopInfoDto() {
    return shopInfoDto;
  }

  public void setShopInfoDto(UserShopInfoDto shopInfoDto) {
    this.shopInfoDto = shopInfoDto;
  }
}
