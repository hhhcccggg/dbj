package com.zwdbj.server.adminserver.service.quartzService;

import com.zwdbj.server.adminserver.service.complain.model.UserComplainDto;
import com.zwdbj.server.adminserver.service.complain.service.ComplainService;
import com.zwdbj.server.adminserver.service.dailyIncreaseAnalysises.service.DailyIncreaseAnalysisesService;
import com.zwdbj.server.adminserver.service.living.service.LivingService;
import com.zwdbj.server.adminserver.service.operateComments.service.OperateService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.review.service.TextScanSample;
import com.zwdbj.server.adminserver.service.review.service.VideoReviewService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.model.VideoHeartAndPlayCountDto;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    UserService userService;
    @Autowired
    VideoService videoService;
    @Autowired
    LivingService livingService;
    @Autowired
    QiniuService qiniuService ;
    @Autowired
    DailyIncreaseAnalysisesService dailyIncreaseAnalysisesService;
    @Autowired
    TextScanSample textScanSample;
    @Autowired
    ComplainService complainService;
    @Autowired
    VideoReviewService videoReviewService;
    @Autowired
    OperateService operateService;

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
     * 定时审核 评论的内容
     */
    public void CommentReviews(){
        try {
            this.textScanSample.textScan();
            logger.info("-------我是评论审核--------"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }catch (Exception e){
            logger.info("评论审核异常"+e.getMessage());
        }

    }

    /**
     * 定时查询,粉丝总量,关注总量,用户举报总量,视频举报总量,并更新数据库
     */
    public void userAllCount(){
        try {
            List<UserComplainDto> userComplainDtos = this.complainService.findUserComplainCount();
            if (userComplainDtos.size()!=0){
                for (UserComplainDto dto: userComplainDtos) {
                    String field = "complainCount="+dto.getComplainCount();
                    this.userService.updateField(field,dto.getToResId());
                    logger.info("-------我是用户举报总量--------"+dto.getToResId()+"的举报总量为: "+dto.getComplainCount());
                }
                logger.info("-------我是用户举报总量--------"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }

            logger.info("-------我是用户查询相关字段数量--------"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }catch (Exception e){
            logger.info("用户查询数量异常"+e.getMessage());
        }

    }

    /**
     * 定时删除每天需要审核的表
     */
    public void deleteResourceNeedReview(){
        try {
            this.videoReviewService.deleteResourceNeedReview();
        }catch (Exception e){
            logger.info("删除review异常"+e.getMessage());
        }
    }

    /**
     * 每天增加马甲用户
     */
    public void greatVestUser(){
        try {
            for (int i = 1; i <= 50; i++) {
                if (i%5==0){
                    this.operateService.newVestUser1();
                    logger.info("我是默认马甲"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }else if (i%5==2){
                    this.operateService.newVestUser2(2);
                    logger.info("我是不同的马甲2: "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }else if (i%5==3 || i%5==1){
                    this.operateService.newVestUser2(3);
                    logger.info("我是不同的马甲3: "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }else if (i%5==4){
                    this.operateService.newVestUser2(4);
                    logger.info("我是不同的马甲4: "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
                }
            }
        }catch (Exception e){
            logger.info("增加用户异常"+e.getMessage());
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
            int count =  new Double(Math.ceil(videoHeartAndPlayCountDtos.size()*0.8)).intValue();
            for (int j=0; j<count; j++) {
                VideoHeartAndPlayCountDto dto = videoHeartAndPlayCountDtos.get(this.operateService.getRandom(0,videoHeartAndPlayCountDtos.size()));
                int dianzhan = this.operateService.getRandom(20, 37);
                int pinlun = this.operateService.getRandom(4, 7);
                int fenxiang = this.operateService.getRandom(1, 3);
                int addPlayCount = this.operateService.getRandom(50, 201);
                this.videoService.updateField("playCount=playCount+" + addPlayCount, dto.getId());
                this.videoService.updateField("heartCount=heartCount+" + new Double(Math.ceil(addPlayCount * dianzhan / 100.0)).longValue(), dto.getId());
                Long addHeartCount = this.videoService.findVideoHeartCount(dto.getId()) - dto.getHeartCount();
                this.userService.updateField("totalHearts=totalHearts+" + addHeartCount, dto.getUserId());
                this.videoService.updateField("shareCount=shareCount+" + new Double(Math.ceil(addHeartCount * fenxiang / 100.0)).longValue(), dto.getId());
                int comment = (int) Math.ceil(addHeartCount * pinlun / 100.0);
                String redisComment =  this.stringRedisTemplate.opsForValue().get("REDIS_COMMENTS");
                String[] redisComments = redisComment.split(">");
                int size = redisComments.length;
                if (dto.getCommentCount()>=size)comment=0;
                int tem = 0;
                for (int i = 0; i < comment; i++) {
                    int gg = this.operateService.commentVideo1(dto.getId());
                    if (gg==0)tem--;
                }
                comment = comment + tem;
                if (comment==0)return;
                this.videoService.updateField("commentCount=commentCount+" + comment, dto.getId());
                logger.info("播放量不超过8000=++++++" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
            }catch(Exception e){
                logger.info("increaseHeartAndPlayCount异常" + e.getMessage());
            }

        }
    }