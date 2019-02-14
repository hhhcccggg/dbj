package com.zwdbj.server.mobileapi.service.video.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(description = "首页视频返回对象")
public class VideoMainDto {

    @ApiModelProperty(value = "分页字段")
    String scroll_id;

    @ApiModelProperty(value = "视频数据")
    List<VideoMain> videoMains;

    public String getScroll_id() {
        return scroll_id;
    }

    public void setScroll_id(String scroll_id) {
        this.scroll_id = scroll_id;
    }

    public List<VideoMain> getVideoMains() {
        return videoMains;
    }

    public void setVideoMains(List<VideoMain> videoMains) {
        this.videoMains = videoMains;
    }

    class VideoMain{
       private long id;
       @ApiModelProperty(value = "视频路径")
       private String videoUrl;
       @ApiModelProperty(value = "标题")
       private String title;
       @ApiModelProperty(value = "封面图片")
       private String coverImageUrl;
       @ApiModelProperty(value = "标签")
       private String tags;
       @ApiModelProperty(value = "头像")
       private String avatarUrl;
       @ApiModelProperty(value = "昵称")
       private String username;
       @ApiModelProperty(value = "点赞数")
       private long heartCount;

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
}
