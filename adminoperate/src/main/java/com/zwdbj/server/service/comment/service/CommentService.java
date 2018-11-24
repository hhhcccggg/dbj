package com.zwdbj.server.service.comment.service;

import com.zwdbj.server.operate.oprateService.OperateService;
import com.zwdbj.server.service.comment.mapper.ICommentMapper;
import com.zwdbj.server.service.video.service.VideoService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CommentService {

    @Autowired
    ICommentMapper commentMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    VideoService videoService;
    @Autowired
    OperateService operateService;
    private Logger logger = LoggerFactory.getLogger(CommentService.class);

    public int greatComment(Long userId,String contentTxt,Long resourceOwnerId){
        Long id = UniqueIDCreater.generateID();
        return this.commentMapper.greatComment(id,userId,contentTxt,resourceOwnerId);
    }

    public void addCommentHeart(long resourceOwnerId){
        List<Long> videoCommentIds = this.getVideoCommentIds(resourceOwnerId);
        if (videoCommentIds!=null && videoCommentIds.size()!=0){
            long videoHeart = this.videoService.findVideoHeartCount(resourceOwnerId);
            int v1 = this.operateService.getRandom(3,9);
            int v2=0;
            int v3=0;
            do {
                v2 = this.operateService.getRandom(3,9);
            }while (v1==v2);
            do {
                v3 = this.operateService.getRandom(3,9);
            }while (v1==v3 || v2==v3);
            Long videoCommentId1 = 0L;
            Long videoCommentId2 = 0L;;
            Long videoCommentId3 = 0L;
            Long videoCommentId4 = 0L;
            Long videoCommentId5 = 0L;

            if (this.stringRedisTemplate.hasKey(resourceOwnerId+"videoCommentId1")){
                videoCommentId1 = Long.parseLong(this.stringRedisTemplate.opsForValue().get(resourceOwnerId+"videoCommentId1"));
            }else {
                videoCommentId1 = videoCommentIds.get(0);
                this.stringRedisTemplate.opsForValue().set(resourceOwnerId+"videoCommentId1",String.valueOf(videoCommentId1),3, TimeUnit.DAYS);
            }
            if (this.stringRedisTemplate.hasKey(resourceOwnerId+"videoCommentId2")){
                videoCommentId2 = Long.parseLong(this.stringRedisTemplate.opsForValue().get(resourceOwnerId+"videoCommentId2"));
            }else {
                videoCommentId2 = videoCommentIds.get(1);
                this.stringRedisTemplate.opsForValue().set(resourceOwnerId+"videoCommentId2",String.valueOf(videoCommentId2),3, TimeUnit.DAYS);
            }
            if (this.stringRedisTemplate.hasKey(resourceOwnerId+"videoCommentId3")){
                videoCommentId3 = Long.parseLong(this.stringRedisTemplate.opsForValue().get(resourceOwnerId+"videoCommentId3"));
            }else {
                videoCommentId3 = videoCommentIds.get(v1);
                this.stringRedisTemplate.opsForValue().set(resourceOwnerId+"videoCommentId3",String.valueOf(videoCommentId3),3, TimeUnit.DAYS);
            }
            if (this.stringRedisTemplate.hasKey(resourceOwnerId+"videoCommentId4")){
                videoCommentId4 = Long.parseLong(this.stringRedisTemplate.opsForValue().get(resourceOwnerId+"videoCommentId4"));
            }else {
                videoCommentId4 = videoCommentIds.get(v2);
                this.stringRedisTemplate.opsForValue().set(resourceOwnerId+"videoCommentId4",String.valueOf(videoCommentId4),3, TimeUnit.DAYS);
            }
            if (this.stringRedisTemplate.hasKey(resourceOwnerId+"videoCommentId5")){
                videoCommentId5 = Long.parseLong(this.stringRedisTemplate.opsForValue().get(resourceOwnerId+"videoCommentId5"));
            }else {
                videoCommentId5 = videoCommentIds.get(v3);
                this.stringRedisTemplate.opsForValue().set(resourceOwnerId+"videoCommentId5",String.valueOf(videoCommentId5),3, TimeUnit.DAYS);
            }
            long commentHeart1 = Math.round(videoHeart/(Math.random()+6));
            this.commentMapper.updateheart(videoCommentId1,commentHeart1);
            long commentHeart2 = Math.round(commentHeart1/9.0);
            this.commentMapper.updateheart(videoCommentId2,commentHeart2);
            long commentHeart3 = Math.round(commentHeart1/20.0);
            this.commentMapper.updateheart(videoCommentId3,commentHeart3);
            long commentHeart4 = this.operateService.getRandom(2,6);
            this.commentMapper.updateheart(videoCommentId4,commentHeart4);
            long commentHeart5 = this.operateService.getRandom(5,21);
            this.commentMapper.updateheart(videoCommentId5,commentHeart5);
            logger.info("我是品论的点赞量");
        }

    }
    public List<Long> getVideoCommentIds(long resourceOwnerId){
        return this.commentMapper.getVideoCommentIds(resourceOwnerId);
    }


}
