package com.zwdbj.server.quartz.quartzService;

import com.zwdbj.server.operate.oprateService.OperateService;
import com.zwdbj.server.service.comment.service.CommentService;
import com.zwdbj.server.service.dailyIncreaseAnalysises.service.DailyIncreaseAnalysisesService;
import com.zwdbj.server.service.dataVideos.model.DataVideosDto;
import com.zwdbj.server.service.dataVideos.service.DataVideosService;
import com.zwdbj.server.service.followers.service.FollowerService;
import com.zwdbj.server.service.user.service.UserService;
import com.zwdbj.server.service.video.model.VideoHeartAndPlayCountDto;
import com.zwdbj.server.service.video.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class QuartzService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    VideoService videoService;
    @Autowired
    OperateService operateService;
    @Autowired
    DailyIncreaseAnalysisesService dailyIncreaseAnalysisesService;
    @Autowired
    FollowerService followerService;
    @Autowired
    DataVideosService dataVideosService;
    @Autowired
    CommentService commentService;

    private Logger logger = LoggerFactory.getLogger(QuartzService.class);



    /**
     * 定时每天早上5点插入增量表的时间和id
     */

    public void everydayInsertTime(){
        int result =  this.dailyIncreaseAnalysisesService.isExistToday();
        if (result==0){
            this.dailyIncreaseAnalysisesService.everydayInsertTime();
            logger.info("我是加载时间");
        }else {
            logger.info("今日时间已加载...");
        }
    }

    /**
     * 定时每天凌晨3点插入昨天user和video的增量
     */

    public void everyIncreasedUsersAndVideos(){
        Long increasedVideos = this.videoService.everyIncreasedVideos();
        Long increasedUsers = this.userService.everyIncreasedUsers();
        this.dailyIncreaseAnalysisesService.everyIncreasedUsersAndVideos(increasedUsers,increasedVideos);
        logger.info("我是加载增长量");
    }


    /**
     * 每天增加马甲用户
     */
    public void greatVestUser(){
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"u";
            int newUserNum = 0;
            if (this.stringRedisTemplate.hasKey(date)){
                newUserNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get(date));
            }else {
                this.operateService.userNumber();
                if (this.stringRedisTemplate.hasKey(date)){
                    newUserNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get(date));
                }
            }
            if (newUserNum==0)return;
            for (int i = 1; i <= newUserNum; i++) {
                if (i%10==1 || i%10==4 || i%10==7){
                    this.operateService.newThirdUsers(8);
                    logger.info("我是 QQ 马甲: "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }else if (i%10==5){
                    this.operateService.newThirdUsers(16);
                    logger.info("我是微博马甲: "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }else {
                    this.operateService.newThirdUsers(4);
                    logger.info("我是微信马甲"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }
            }
        }catch (Exception e){
            logger.error("增加用户异常"+e.getMessage());
        }
    }

    /**
     * 定时增加视频的播放量和点赞量
     */
    public void increaseHeartAndPlayCount() {
        try {
            logger.info("定时增加视频的播放量和点赞量++++++" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            List<VideoHeartAndPlayCountDto> videoHeartAndPlayCountDtos = this.videoService.findHeartAndPlayCount();
            if (videoHeartAndPlayCountDtos == null) return;
            int count =  (int)Math.round(videoHeartAndPlayCountDtos.size()*0.8);
            for (int j=0; j<count; j++) {
                VideoHeartAndPlayCountDto dto = videoHeartAndPlayCountDtos.get(this.operateService.getRandom(0,videoHeartAndPlayCountDtos.size()));
                int dianzhan = this.operateService.getRandom(18, 34);
                int pinlun = this.operateService.getRandom(3, 6);
                int fenxiang = this.operateService.getRandom(1, 3);
                int addPlayCount = this.operateService.getRandom(50, 201);
                this.videoService.updateField("playCount=playCount+" + addPlayCount, dto.getId());
                this.videoService.updateField("heartCount=heartCount+" + Math.round(addPlayCount * dianzhan / 100.0), dto.getId());
                Long addHeartCount = this.videoService.findVideoHeartCount(dto.getId()) - dto.getHeartCount();
                this.userService.updateField("totalHearts=totalHearts+" + addHeartCount, dto.getUserId());
                this.videoService.updateField("shareCount=shareCount+" + Math.round(addHeartCount * fenxiang / 100.0), dto.getId());
                int comment = (int) Math.round(addHeartCount * pinlun / 100.0);
                int tem = 0;
                String redisComment =  this.stringRedisTemplate.opsForValue().get("REDIS_COMMENTS");
                String[] redisComments ={};
                if (redisComment!=null)
                    redisComments = redisComment.split(">");
                int size = redisComments.length;
                if (dto.getCommentCount()>=(size+99))comment=0;
                for (int i = 0; i < comment; i++){
                    if (this.redisTemplate.hasKey(dto.getId()+"_COMMENTS") && this.redisTemplate.opsForList().size(dto.getId()+"_COMMENTS")!=0){
                        long userId = this.operateService.getVestUserId1();
                        String videoComment = this.redisTemplate.opsForList().rightPop(dto.getId()+"_COMMENTS");
                        logger.info(videoComment);
                        this.commentService.greatComment(userId,videoComment,dto.getId());
                    }else {
                        int gg = this.operateService.commentVideo1(dto.getId());
                        if (gg==0)tem--;
                    }
                }
                comment = comment + tem;
                if (comment==0)return;
                this.videoService.updateField("commentCount=commentCount+" + comment, dto.getId());
                if (dto.getCommentCount()>10) this.commentService.addCommentHeart(dto.getId());
                logger.info("播放量不超过8000=++++++" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }catch(Exception e){
            logger.error("increaseHeartAndPlayCount异常" + e.getMessage());
        }
    }

    public void newMyFollowers(){
        try {
            logger.info("我是增加粉丝和关注的开始");
            List<Long> userIds = this.userService.getNoFollowersUser();
            List<Long> followers = this.userService.getVestUserIds1();
            if (userIds==null || userIds.size()==0)return;
            for (Long userId:userIds){
                int a= this.operateService.getRandom(0,61);
                this.newFollowers(userId,a,followers,0);
                this.userService.updateField("totalFans=totalFans+"+a,userId);
                int  b = this.operateService.getRandom(0,201);
                this.newFollowers(userId,b,followers,1);
                this.userService.updateField("totalMyFocuses=totalMyFocuses+"+b,userId);
            }
            logger.info("我是增加粉丝和关注的结束");
        }catch (Exception e){
            logger.error("增加粉丝和关注异常" + e.getMessage());
        }

    }

    public void newFollowers(long userId,int count,List<Long> followers,int type ){
        for (int i=0;i<count;i++){
            int c = this.operateService.getRandom(0,followers.size());
            Long follower = followers.get(c);
            int d = 0;
            if (userId==follower)return;
            if (type==0){
                d=this.followerService.followIsExit(follower,userId);
                if (d!=0)continue;
                this.followerService.newMyFollower(follower,userId);
            }else if (type==1){
                d = this.followerService.followIsExit(userId,follower);
                if (d!=0)continue;
                this.followerService.newMyFollower(userId,follower);
            }
        }
    }

    /**
     * 定时增加视频
     */
    public void videosToUser(){
        try {
            logger.info("我是增加视频开始");
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"v";
            int videosNum = this.dataVideosService.getDataVideos();
            int newVideoNum = 0;
            if (this.stringRedisTemplate.hasKey(date)){
                newVideoNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get(date));
            }else {
                this.operateService.videoNumber();
                if (this.stringRedisTemplate.hasKey(date)){
                    newVideoNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get(date));
                }
            }
            if (videosNum>newVideoNum)videosNum=newVideoNum;
            if (videosNum==0)return;
            List<Long> userIds = this.userService.getVestUserIds1();
            for (int i = 0;i<videosNum;i++){
                int a = this.operateService.getRandom(0,userIds.size());
                long userId = userIds.get(a);
                DataVideosDto dataVideosDto = this.dataVideosService.getOneDataVideo();
                this.videoService.newVideoFromData(userId,dataVideosDto);
                this.dataVideosService.updateDataVideoStatus(dataVideosDto.getId());
            }
            logger.info("我是增加视频结束");
        }catch (Exception e){
            logger.error("增加视频异常"+e.getMessage());
        }
    }

}