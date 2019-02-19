package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "主页视频")
public class VideoMain{
        @ApiModelProperty(value = "视频ID")
        private long id;
        @ApiModelProperty(value = "视频路径")
        private String videoUrl;
        @ApiModelProperty(value = "标题")
        private String title;
        @ApiModelProperty(value = "封面图片")
        private String coverImageUrl;
        @ApiModelProperty(value = "封面图片宽")
        private double coverImageWidth;
        @ApiModelProperty(value = "封面图片高")
        private double coverImageHeight;
        @ApiModelProperty(value = "标签")
        private String tags;
        @ApiModelProperty(value = "用户ID")
        private long userId;
        @ApiModelProperty(value = "头像")
        private String avatarUrl;
        @ApiModelProperty(value = "昵称")
        private String username;
        @ApiModelProperty(value = "点赞数")
        private long heartCount;
        @ApiModelProperty(value = "店铺Id")
        private long storeId;
        @ApiModelProperty(value = "店铺名称")
        private String storeName;
        @ApiModelProperty(value = "logo地址")
        private String logoUrl;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getStoreId() {
            return storeId;
        }

        public void setStoreId(long storeId) {
            this.storeId = storeId;
        }

        public double getCoverImageWidth() {
            return coverImageWidth;
        }

        public void setCoverImageWidth(double coverImageWidth) {
            this.coverImageWidth = coverImageWidth;
        }

        public double getCoverImageHeight() {
            return coverImageHeight;
        }

        public void setCoverImageHeight(double coverImageHeight) {
            this.coverImageHeight = coverImageHeight;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getAvatarUrl() {
           return avatarUrl;
       }

       public void setAvatarUrl(String avatarUrl) {
           this.avatarUrl = avatarUrl;
       }

       public String getUsername() {
           return username;
       }

       public void setUsername(String username) {
           this.username = username;
       }

       public long getHeartCount() {
           return heartCount;
       }

       public void setHeartCount(long heartCount) {
           this.heartCount = heartCount;
       }

       public long getId() {
           return id;
       }

       public void setId(long id) {
           this.id = id;
       }

       public String getVideoUrl() {
           return videoUrl;
       }

       public void setVideoUrl(String videoUrl) {
           this.videoUrl = videoUrl;
       }

       public String getTitle() {
           return title;
       }

       public void setTitle(String title) {
           this.title = title;
       }

       public String getCoverImageUrl() {
           return coverImageUrl;
       }

       public void setCoverImageUrl(String coverImageUrl) {
           this.coverImageUrl = coverImageUrl;
       }

       public String getTags() {
           return tags;
       }

       public void setTags(String tags) {
           this.tags = tags;
       }
   }