package com.zwdbj.server.service.video.mapper;


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
}
