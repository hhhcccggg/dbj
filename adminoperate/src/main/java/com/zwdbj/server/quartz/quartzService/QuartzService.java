package com.zwdbj.server.quartz.quartzService;

import com.zwdbj.server.operate.oprateService.OperateService;
import com.zwdbj.server.service.user.service.UserService;
import com.zwdbj.server.service.video.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class QuartzService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;
    @Autowired
    OperateService operateService;

    private Logger logger = LoggerFactory.getLogger(QuartzService.class);



    /**
     * 每天增加马甲用户
     */
    public void greatVestUser(){
        try {
        }catch (Exception e){
            logger.info("增加用户异常"+e.getMessage());
        }
    }

    /**
     * 定时增加视频的播放量和点赞量
     */
    public void increaseHeartAndPlayCount() {
        try {
            }catch(Exception e){
                logger.info("increaseHeartAndPlayCount异常" + e.getMessage());
            }

        }
    }