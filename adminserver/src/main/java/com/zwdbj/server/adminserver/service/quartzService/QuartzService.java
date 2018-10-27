package com.zwdbj.server.adminserver.service.quartzService;

import com.zwdbj.server.adminserver.easemob.api.EaseMobChatRoom;
import com.zwdbj.server.adminserver.service.complain.model.UserComplainDto;
import com.zwdbj.server.adminserver.service.complain.service.ComplainService;
import com.zwdbj.server.adminserver.service.dailyIncreaseAnalysises.service.DailyIncreaseAnalysisesService;
import com.zwdbj.server.adminserver.service.living.service.LivingService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.review.service.TextScanSample;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
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
    EaseMobChatRoom easeMobChatRoom ;
    @Autowired
    QiniuService qiniuService ;
    @Autowired
    DailyIncreaseAnalysisesService dailyIncreaseAnalysisesService;
    @Autowired
    TextScanSample textScanSample;
    @Autowired
    ComplainService complainService;

    private Logger logger = LoggerFactory.getLogger(QuartzService.class);

    /**
     * 直播相关
     * 直播房间的人数,关闭无推流的聊天室
     */
    public  void timedQueryChatRoomId(){
        List<Long> ids = livingService.timedQueryIsLivingIds();
        for (Long id: ids) {
            String chatRoomId = livingService.timedQueryChatRoomId(id);
            String streamName = String.valueOf(id);
            boolean flag = qiniuService.getLiveStatus(streamName);
            if (!flag){
                easeMobChatRoom.deleteChatRoom(chatRoomId);
                qiniuService.disableStream(streamName,id);
                livingService.modifyIsLiving(id);
                logger.info("<================我是状态不正确的 :"  + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "count===============>");
            }else if(flag) {
                Integer result = Integer.valueOf(easeMobChatRoom.getAffiliationsCount(chatRoomId));
                Date createTime = this.livingService.findCreatedTimeById(id);
                Long liveingTotalTime = (new Date().getTime()-createTime.getTime())/1000;

                this.livingService.updateOnlinePeopleCount(id,result,liveingTotalTime);
                logger.info("<================我是状态正确的 :"  + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "count===============>");
            }
        }
        logger.info("<================我是验证标准 :"  + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "count===============>");
    }

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
            /*List<UserIdAndFollowersDto> followersDtos = this.userService.findMyFansCount();
            if (followersDtos.size()!=0){
                for (UserIdAndFollowersDto dto: followersDtos) {
                    String field = "totalFans="+dto.getTotalFans();
                    this.userService.updateField(field,dto.getUserId());
                    logger.info("-------我是粉丝总量--------"+dto.getUserId()+"的粉丝总量为: "+dto.getTotalFans());
                }
                logger.info("-------我是粉丝总量--------"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
            List<UserIdAndFocusesDto> focusesDtos = this.userService.findMyFocusesCount();
            if (focusesDtos.size()!=0){
                for (UserIdAndFocusesDto dto: focusesDtos) {
                    String field = "totalMyFocuses="+dto.getTotalMyFocuses();
                    this.userService.updateField(field,dto.getFollowerUserId());
                    logger.info("-------我是关注总量--------"+dto.getFollowerUserId()+"的关注总量为: "+dto.getTotalMyFocuses());
                }
                logger.info("-------我是关注总量--------"+new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }*/
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

}
