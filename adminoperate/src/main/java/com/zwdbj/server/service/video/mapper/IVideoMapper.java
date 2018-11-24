package com.zwdbj.server.service.video.mapper;


import com.zwdbj.server.service.dataVideos.model.DataVideosDto;
import com.zwdbj.server.service.video.model.VideoHeartAndPlayCountDto;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface IVideoMapper {
    @Select("select id from core_videos where status=0 and playCount<10000")
    List<Long> getRandomVideoIds();
    //定时任务
    @Select("select id,userId,playCount,heartCount,shareCount,commentCount from core_videos where status=0 and playCount<8000")
    List<VideoHeartAndPlayCountDto> findHeartAndPlayCount();

    @Select("select playCount from core_videos where id=#{id}")
    Long getVideoPlayCount(@Param("id")Long id);

    @Select("select heartCount from core_videos where id=#{id}")
    Long findVideoHeartCount(@Param("id")Long id);
    @Select("select count(id) from core_videos where status=0 and date(createTime)=curDate()-1")
    Long everyIncreasedVideos();

    @UpdateProvider(type = VideoSqlProvider.class,method = "updateVideoField")
    long updateVideoField(@Param("fields") String fields,@Param("id") long id);

    @Insert("insert into core_videos(id,title,coverImageUrl,coverImageWidth,coverImageHeight,firstFrameUrl,firstFrameWidth," +
            "firstFrameHeight,videoUrl,status,userId,isManualData) values(#{id},#{dataVideosDto.title}," +
            "#{dataVideosDto.coverImageUrl},#{dataVideosDto.coverImageWidth},#{dataVideosDto.coverImageHeight}," +
            "#{dataVideosDto.firstFrameUrl},#{dataVideosDto.firstFrameWidth},#{dataVideosDto.firstFrameHeight}," +
            "#{dataVideosDto.videoUrl},0,#{userId},true)")
    int newVideoFromData(@Param("id")long id, @Param("userId")long userId, @Param("dataVideosDto")DataVideosDto dataVideosDto);
}
