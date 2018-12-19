package com.zwdbj.server.service.video.mapper;


import com.zwdbj.server.service.dataVideos.model.DataVideosDto;
import com.zwdbj.server.service.video.model.VideoHeartAndPlayCountDto;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


@Mapper
public interface IVideoMapper {
    @Select("select id from core_videos where status=0 and playCount<10000")
    List<Long> getRandomVideoIds();

    //定时任务
    @Select("select id,userId,playCount,heartCount,shareCount,commentCount from core_videos where status=0 and playCount<8000")
    List<VideoHeartAndPlayCountDto> findHeartAndPlayCount();

    @Select("select playCount from core_videos where id=#{id}")
    Long getVideoPlayCount(@Param("id") Long id);

    @Select("select heartCount from core_videos where id=#{id}")
    Long findVideoHeartCount(@Param("id") Long id);

    @Select("select count(id) from core_videos where status=0 and date(createTime)=curDate()-1")
    Long everyIncreasedVideos();

    @UpdateProvider(type = VideoSqlProvider.class, method = "updateVideoField")
    long updateVideoField(@Param("fields") String fields, @Param("id") long id);

    @Insert("insert into core_videos(id,title,coverImageUrl,coverImageWidth,coverImageHeight,firstFrameUrl,firstFrameWidth," +
            "firstFrameHeight,videoUrl,status,userId,isManualData) values(#{id},#{dataVideosDto.title}," +
            "#{dataVideosDto.coverImageUrl},#{dataVideosDto.coverImageWidth},#{dataVideosDto.coverImageHeight}," +
            "#{dataVideosDto.firstFrameUrl},#{dataVideosDto.firstFrameWidth},#{dataVideosDto.firstFrameHeight}," +
            "#{dataVideosDto.videoUrl},0,#{userId},true)")
    int newVideoFromData(@Param("id") long id, @Param("userId") long userId, @Param("dataVideosDto") DataVideosDto dataVideosDto);

    @Select("select id from core_videos where isManualData=1 order by recommendIndex limit 1600")
    List<Long> get1300Videos();

    @Update("update core_videos set longitude=#{longitude},latitude=#{latitude},address=#{address} where id=#{id}")
    int updateVideoAddress(@Param("id") long id, @Param("longitude") float longitude, @Param("latitude") float latitude, @Param("address") String address);

    @Select("select count(id) as growthed from core_videos where isManualData=0 and createTime between date_add(now(),INTERVAL -1 HOUR) and now()")
    long videoGrowthAd();

    @Select("select count(id) as growthed from core_videos where isManualData=0 and createTime between date_add(#{date},INTERVAL -1 HOUR) and #{date}")
    int videoDayGrowthed(@Param("date") Date date);
}
