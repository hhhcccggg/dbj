package com.zwdbj.server.discoverapiservice.videorandrecommend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 随机推荐视频策略
 */
@Service
public class VideoRandRecommendService {
    @Autowired
    private RedisTemplate redisTemplate;
    private String videosCacheKey = "randvideo_all";
    private Logger logger = LoggerFactory.getLogger(VideoRandRecommendService.class);
    private String userLoadedVideoCacheKey(String id) {
        return "randvideo_all_user_"+id;
    }
    /***
     * 缓存系统新的视频
     * @param id
     */
    public void pushNewVideo(Long id) {
        Long size =  this.redisTemplate.opsForSet().size(videosCacheKey);
        if (!this.redisTemplate.opsForSet().isMember(videosCacheKey,id)) {
            logger.info("新增视频"+id);
            Long resultV = this.redisTemplate.opsForSet().add(videosCacheKey, id);
            logger.info("新增结果"+resultV);
        }
    }

    /**
     * 某个用户获取一定数量的视频
     * @param userId
     * @param count
     * @return 视频ID列表
     */
    public List<Object> fetchVideo(String userId, int count) {
        String userCacheKey = userLoadedVideoCacheKey(userId);
        String diffcacheKey = "randvideo_all_user_diff_"+userId;
        this.redisTemplate.delete(diffcacheKey);
        int num = this.redisTemplate.opsForSet().differenceAndStore(videosCacheKey,userCacheKey,diffcacheKey).intValue();
        Set<Long> lst = null;
        logger.info("num="+num);
        logger.info(userCacheKey);
        logger.info(diffcacheKey);
        if (num==0) {
            long size = this.redisTemplate.opsForSet().size(userCacheKey);
            logger.info("总长度"+size);
            if (size>0) {
                this.redisTemplate.delete(userCacheKey);
            }
            lst = this.redisTemplate.opsForSet().distinctRandomMembers(videosCacheKey,count);
        } else {
            int cn = count;
            if (count>num) {
                cn = num;
            } else if (num>count && num < count * 2) {
                cn = num;
            }
            lst = this.redisTemplate.opsForSet().distinctRandomMembers(diffcacheKey,cn);
        }
        this.redisTemplate.delete(diffcacheKey);
        if (lst!=null) {
            for (Long l : lst) {
                long resultV = this.redisTemplate.opsForSet().add(userCacheKey, l);
                logger.info("l = " + l + " ,userCacheKey = " + resultV);
            }
        }
        List<Object> resultLst = new ArrayList<>(lst);
        return resultLst;
    }

    /**
     * 删除某个视频
     * @param id
     */
    public void popVideo(Long id) {
        this.redisTemplate.opsForSet().remove(videosCacheKey,id);
    }
}
