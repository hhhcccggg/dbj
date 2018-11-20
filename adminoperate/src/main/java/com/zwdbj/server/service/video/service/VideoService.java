package com.zwdbj.server.service.video.service;
import com.zwdbj.server.service.video.mapper.IVideoMapper;
import com.zwdbj.server.service.video.model.VideoHeartAndPlayCountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {
    @Autowired
    IVideoMapper videoMapper;

    public List<VideoHeartAndPlayCountDto> findHeartAndPlayCount(){
        List<VideoHeartAndPlayCountDto> videoHeartAndPlayCountDtos = this.videoMapper.findHeartAndPlayCount();
        return videoHeartAndPlayCountDtos;
    }
    public Long getVideoPlayCount(Long id){
        return this.videoMapper.getVideoPlayCount(id);
    }

    public Long findVideoHeartCount(Long id){
        Long hearCount = this.videoMapper.findVideoHeartCount(id);
        return hearCount;
    }
    public List<Long> getRandomVideoIds(){
        return this.videoMapper.getRandomVideoIds();
    }
    public Long everyIncreasedVideos(){
        Long increasedVideos = this.videoMapper.everyIncreasedVideos();
        return increasedVideos;
    }

    public void updateField(String fields,long id) {
        this.videoMapper.updateVideoField(fields,id);
    }

}
