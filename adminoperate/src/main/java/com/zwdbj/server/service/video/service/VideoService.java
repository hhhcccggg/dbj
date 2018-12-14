package com.zwdbj.server.service.video.service;

import com.zwdbj.server.service.dataVideos.model.DataVideosDto;
import com.zwdbj.server.service.video.mapper.IVideoMapper;
import com.zwdbj.server.service.video.model.VideoHeartAndPlayCountDto;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class VideoService {
    @Autowired
    IVideoMapper videoMapper;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public List<VideoHeartAndPlayCountDto> findHeartAndPlayCount() {
        List<VideoHeartAndPlayCountDto> videoHeartAndPlayCountDtos = this.videoMapper.findHeartAndPlayCount();
        return videoHeartAndPlayCountDtos;
    }

    public Long getVideoPlayCount(Long id) {
        return this.videoMapper.getVideoPlayCount(id);
    }

    public Long findVideoHeartCount(Long id) {
        Long hearCount = this.videoMapper.findVideoHeartCount(id);
        return hearCount;
    }

    public List<Long> getRandomVideoIds() {
        return this.videoMapper.getRandomVideoIds();
    }

    public Long everyIncreasedVideos() {
        Long increasedVideos = this.videoMapper.everyIncreasedVideos();
        return increasedVideos;
    }

    public void updateField(String fields, long id) {
        this.videoMapper.updateVideoField(fields, id);
    }

    public void newVideoFromData(long userId, DataVideosDto dataVideosDto) {
        long id = UniqueIDCreater.generateID();
        this.videoMapper.newVideoFromData(id, userId, dataVideosDto);
        String comments = dataVideosDto.getComments();
        if (comments != null && comments.length() != 0)
            this.setRedisComments(id, comments);
    }

    public void setRedisComments(long id, String comments) {
        Pattern p = Pattern.compile("\\{dbj}");
        String[] ss = p.split(comments);
        for (int i = 1; i < ss.length; i++) {
            this.redisTemplate.opsForList().leftPush(id + "_COMMENTS", ss[i]);
        }
    }

    public void get1300Videos() {
        List<Long> videoIds = this.videoMapper.get1300Videos();
        for (Long id : videoIds) {
            this.redisTemplate.opsForList().leftPush("TEMP_1300_VIDEOS", String.valueOf(id));
        }
    }

    public void updateVideoAddress(long id, float longitude, float latitude, String address) {
        this.videoMapper.updateVideoAddress(id, longitude, latitude, address);
    }

    public Long videoGrowth() {
        return this.videoMapper.videoGrowthAd();
    }
}
