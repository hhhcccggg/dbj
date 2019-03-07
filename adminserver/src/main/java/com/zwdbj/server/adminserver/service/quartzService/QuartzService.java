package com.zwdbj.server.adminserver.service.quartzService;

import com.zwdbj.server.adminserver.service.complain.model.UserComplainDto;
import com.zwdbj.server.adminserver.service.complain.service.ComplainService;
import com.zwdbj.server.adminserver.service.dailyIncreaseAnalysises.service.DailyIncreaseAnalysisesService;
import com.zwdbj.server.adminserver.service.living.service.LivingService;
import com.zwdbj.server.adminserver.service.operateComments.service.OperateService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuOperService;
import com.zwdbj.server.adminserver.service.review.service.TextScanSample;
import com.zwdbj.server.adminserver.service.review.service.VideoReviewService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.common.qiniu.QiniuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class QuartzService {
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
    @Autowired
    private QiniuOperService qiniuOperService;

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
     * 定时添加宠物种类
     */
    public void addPetCateGories(){
        this.qiniuOperService.catOpe();
        this.qiniuOperService.dogOpe();
        logger.info("定时添加宠物种类完成");
    }

}