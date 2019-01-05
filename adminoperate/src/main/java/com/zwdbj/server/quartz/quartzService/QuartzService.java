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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class QuartzService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
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
     * jia 定时每天早上5点插入增量表的时间和id
     */

    public void everydayInsertTime() {
        int result = this.dailyIncreaseAnalysisesService.isExistToday();
        if (result == 0) {
            this.dailyIncreaseAnalysisesService.everydayInsertTime();
            logger.info("我是加载时间");
        } else {
            logger.info("今日时间已加载...");
        }
    }

    /**
     * 定时每天凌晨3点插入昨天user和video的增量
     */

    public void everyIncreasedUsersAndVideos() {
        Long increasedVideos = this.videoService.everyIncreasedVideos();
        Long increasedUsers = this.userService.everyIncreasedUsers();
        this.dailyIncreaseAnalysisesService.everyIncreasedUsersAndVideos(increasedUsers, increasedVideos);
        logger.info("我是加载增长量");
    }


    /**
     * 每天增加马甲用户
     */
    public void greatVestUser() {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "u";
            int newUserNum = 0;
            if (this.stringRedisTemplate.hasKey(date)) {
                newUserNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get(date));
            } else {
                this.operateService.userNumber();
                if (this.stringRedisTemplate.hasKey(date)) {
                    newUserNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get(date));
                }
            }
            if (newUserNum == 0) return;
            Date createTime = new Date();
            this.forGreatVestUser(newUserNum, createTime);

        } catch (Exception e) {
            logger.error("增加用户异常" + e.getMessage());
        }
    }

    public void forGreatVestUser(int newUserNum, Date createTime) {
        for (int i = 1; i <= newUserNum; i++) {
            if (i % 10 == 1 || i % 10 == 4 || i % 10 == 7) {
                this.operateService.newThirdUsers(8, createTime);
                logger.info("我是 QQ 马甲: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            } else if (i % 10 == 5) {
                this.operateService.newThirdUsers(16, createTime);
                logger.info("我是微博马甲: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            } else {
                this.operateService.newThirdUsers(4, createTime);
                logger.info("我是微信马甲" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    /**
     * 暂时增加用户
     */
    public void tempGreatUser() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date createTime = null;
            int newUserNum = 0;
            for (int i = 1; i < 5; i++) {
                newUserNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get("2019-01-0" + i + "u"));
                createTime = sdf.parse("2019-01-0" + i + " 20:00:00");
                this.forGreatVestUser(newUserNum, createTime);
            }
        } catch (Exception e) {
            logger.error("增加用户异常" + e.getMessage());
        }
    }

    /**
     * 定时增加视频的播放量和点赞量
     */
    public void increaseHeartAndPlayCount() {
        try {
            logger.info("定时增加视频的播放量和点赞量++++++" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            List<VideoHeartAndPlayCountDto> videoHeartAndPlayCountDtos = this.videoService.findHeartAndPlayCount();
            if (videoHeartAndPlayCountDtos == null || videoHeartAndPlayCountDtos.size() == 0) return;
            int videosSize = videoHeartAndPlayCountDtos.size();
            int count = (int) Math.round(videosSize * 0.8);
            logger.info("次数为：" + count);
            String redisComment = this.stringRedisTemplate.opsForValue().get("REDIS_COMMENTS");
            String[] redisComments = {};
            if (redisComment != null)
                redisComments = redisComment.split(">");
            int size = redisComments.length;
            for (int j = 0; j < count; j++) {
                VideoHeartAndPlayCountDto dto = videoHeartAndPlayCountDtos.get(this.operateService.getRandom(0, videosSize));
                int dianzhan = this.operateService.getRandom(18, 34);
                int pinlun = this.operateService.getRandom(3, 6);
                int fenxiang = this.operateService.getRandom(1, 3);
                int addPlayCount = this.operateService.getRandom(50, 201);
                this.videoService.updateField("playCount=playCount+" + addPlayCount, dto.getId());
                this.videoService.updateField("heartCount=heartCount+" + Math.round(addPlayCount * dianzhan / 100.0), dto.getId());
                long addHeartCount = this.videoService.findVideoHeartCount(dto.getId()) - dto.getHeartCount();
                this.userService.updateField("totalHearts=totalHearts+" + addHeartCount, dto.getUserId());
                this.videoService.updateField("shareCount=shareCount+" + Math.round(addHeartCount * fenxiang / 100.0), dto.getId());
                int comment = (int) Math.round(addHeartCount * pinlun / 100.0);
                if (comment == 0) continue;
                int tem = 0;
                if (dto.getCommentCount() >= (size + 99)) continue;
                for (int i = 0; i < comment; i++) {
                    if (this.redisTemplate.hasKey(dto.getId() + "_COMMENTS") && this.redisTemplate.opsForList().size(dto.getId() + "_COMMENTS") != 0) {
                        long userId = this.operateService.getVestUserId1();
                        String videoComment = this.redisTemplate.opsForList().rightPop(dto.getId() + "_COMMENTS");
                        logger.info(videoComment);
                        this.commentService.greatComment(userId, videoComment, dto.getId());
                    } else {
                        int gg = this.operateService.commentVideo1(dto.getId());
                        if (gg == 0) tem--;
                    }
                }
                comment = comment + tem;
                if (comment == 0) continue;
                this.videoService.updateField("commentCount=commentCount+" + comment, dto.getId());
                if (dto.getCommentCount() >= 10) this.commentService.addCommentHeart(dto.getId());
                logger.info("播放量不超过8000总视频数量：" + videoHeartAndPlayCountDtos.size() + "++++实际数量，第" + j + "个+++++" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        } catch (Exception e) {
            logger.error("increaseHeartAndPlayCount异常" + e.getMessage());
        }
    }

    public void newMyFollowers() {
        try {
            logger.info("我是增加粉丝和关注的开始");
            List<Long> userIds = this.userService.getNoFollowersUser();
            List<Long> followers = this.userService.getVestUserIds1();
            if (userIds == null || userIds.size() == 0) return;
            for (Long userId : userIds) {
                int a = this.operateService.getRandom(0, 61);
                this.newFollowers(userId, a, followers, 0);
                this.userService.updateField("totalFans=totalFans+" + a, userId);
                int b = this.operateService.getRandom(0, 201);
                this.newFollowers(userId, b, followers, 1);
                this.userService.updateField("totalMyFocuses=totalMyFocuses+" + b, userId);
            }
            logger.info("我是增加粉丝和关注的结束");
        } catch (Exception e) {
            logger.error("增加粉丝和关注异常" + e.getMessage());
        }

    }

    public void newFollowers(long userId, int count, List<Long> followers, int type) {
        for (int i = 0; i < count; i++) {
            int c = this.operateService.getRandom(0, followers.size());
            Long follower = followers.get(c);
            int d = 0;
            if (userId == follower) return;
            if (type == 0) {
                d = this.followerService.followIsExit(follower, userId);
                if (d != 0) continue;
                this.followerService.newMyFollower(follower, userId);
            } else if (type == 1) {
                d = this.followerService.followIsExit(userId, follower);
                if (d != 0) continue;
                this.followerService.newMyFollower(userId, follower);
            }
        }
    }

    /**
     * 定时增加视频
     */
    public void videosToUser() {
        try {
            logger.info("我是增加视频开始");
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "v";
            int videosNum = this.dataVideosService.getDataVideos();
            int newVideoNum = 0;
            if (this.stringRedisTemplate.hasKey(date)) {
                newVideoNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get(date));
            } else {
                newVideoNum = videosNum;
            }
            if (videosNum > newVideoNum) videosNum = newVideoNum;
            if (videosNum == 0) return;
            int top = this.dataVideosService.getTopVideosNum();
            if (top != 0) {
                if (top >= videosNum) {
                    this.insertVideos2(videosNum);
                } else {
                    this.insertVideos1((videosNum - top));
                    this.insertVideos2(top);
                }
            } else {
                this.insertVideos1(videosNum);
            }
            logger.info("我是增加视频结束");
        } catch (Exception e) {
            logger.error("增加视频异常" + e.getMessage());
        }
    }

    /**
     * all isPublish=false
     *
     * @param videosNum
     */
    public void insertVideos1(int videosNum) {
        try {
            List<Long> userIds = this.userService.getVestUserIds1();
            for (int i = 1; i <= videosNum; i++) {
                int a = this.operateService.getRandom(0, userIds.size());
                long userId = userIds.get(a);
                DataVideosDto dataVideosDto = this.dataVideosService.getOneDataVideo1();
                this.videoService.newVideoFromData(userId, dataVideosDto);
                this.dataVideosService.updateDataVideoStatus(dataVideosDto.getId());
                logger.info("我不是100：" + dataVideosDto.getTitle());
            }
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }

    }

    /**
     * top100=true and isPublish=false
     *
     * @param videosNum
     */
    public void insertVideos2(int videosNum) {
        List<Long> userIds = this.userService.getVestUserIds1();
        for (int i = 1; i <= videosNum; i++) {
            int a = this.operateService.getRandom(0, userIds.size());
            long userId = userIds.get(a);
            DataVideosDto dataVideosDto = this.dataVideosService.getOneDataVideo2();
            this.videoService.newVideoFromData(userId, dataVideosDto);
            this.dataVideosService.updateDataVideoStatus(dataVideosDto.getId());
            logger.info("我是100：" + dataVideosDto.getTitle());
        }
    }

    /**
     * 附近
     */
    public void get1300Videos() {
        try {
            if (this.redisTemplate.hasKey("TEMP_1300_VIDEOS") && this.redisTemplate.opsForList().size("TEMP_1300_VIDEOS") >= 1300) {
                String address = "";
                long id = 0L;
                float longitude = 0.0F;
                float latitude = 0.0F;
                for (int i = 1; i < 1301; i++) {
                    if (i % 13 == 1) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "北京市";
                        longitude = 116.41667F;
                        latitude = 39.91667F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：北京");
                    } else if (i % 13 == 2) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "上海市";
                        longitude = 121.43333F;
                        latitude = 34.50000F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：上海");
                    } else if (i % 13 == 3) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "杭州市";
                        longitude = 120.20000F;
                        latitude = 30.26667F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：杭州市");
                    } else if (i % 13 == 4) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "广州市";
                        longitude = 113.23333F;
                        latitude = 23.16667F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：广州市");
                    } else if (i % 13 == 5) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "深圳市";
                        longitude = 114.06667F;
                        latitude = 22.61667F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：深圳市");
                    } else if (i % 13 == 6) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "南京市";
                        longitude = 118.78333F;
                        latitude = 32.05000F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：南京市");
                    } else if (i % 13 == 7) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "武汉市";
                        longitude = 114.31667F;
                        latitude = 30.51667F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：武汉市");
                    } else if (i % 13 == 8) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "重庆市";
                        longitude = 106.45000F;
                        latitude = 29.56667F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：重庆市");
                    } else if (i % 13 == 9) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "郑州市";
                        longitude = 113.65000F;
                        latitude = 34.76667F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：郑州市");
                    } else if (i % 13 == 10) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "合肥市";
                        longitude = 117.17F;
                        latitude = 31.52F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：合肥市");
                    } else if (i % 13 == 11) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "西安市";
                        longitude = 108.95000F;
                        latitude = 34.26667F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：西安市");
                    } else if (i % 13 == 12) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "天津市";
                        longitude = 117.20F;
                        latitude = 39.13F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：天津市");
                    } else if (i % 13 == 0) {
                        id = Long.valueOf(this.redisTemplate.opsForList().rightPop("TEMP_1300_VIDEOS"));
                        address = "银川市";
                        longitude = 106.26667F;
                        latitude = 38.46667F;
                        this.videoService.updateVideoAddress(id, longitude, latitude, address);
                        logger.info("第" + i + "个视频：银川市");
                    }
                }

            }
        } catch (Exception e) {
            logger.info("附近异常" + e.getMessage());
        }
    }

    /**
     * 每天凌晨3:15更新昨天video的增量
     */
    public void everyUpdateVideoNum() {
        try {
            String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "v";
            if (this.stringRedisTemplate.hasKey(date)) {
                int videoNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get(date));
                this.dailyIncreaseAnalysisesService.updateVideoNum(videoNum);
                if (this.stringRedisTemplate.hasKey("OPERATE_ALL_VIDEO_NUM")) {
                    int allVideoNum = Integer.valueOf(this.stringRedisTemplate.opsForValue().get("OPERATE_ALL_VIDEO_NUM"));
                    allVideoNum = allVideoNum + videoNum;
                    this.stringRedisTemplate.opsForValue().set("OPERATE_ALL_VIDEO_NUM", String.valueOf(allVideoNum));
                }
            } else {
                this.dailyIncreaseAnalysisesService.updateVideoNum(326);
            }
        } catch (Exception e) {
            logger.error("每天更新videoNum异常" + e.getMessage());
        }

    }

    /**
     * 每小时查询用户，视频增长量
     */
    public void everyHourSearchUserAndVideoGrowthed() {
        try {
            //获取当前时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            String nowTime = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());

            //获取当天零时
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            String zeroTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(calendar.getTime());

            //查询用户每小时增量
            String userGrowth = String.valueOf(this.dailyIncreaseAnalysisesService.userGrowthAd());
            //查询视频每小时增量
            String videoGrowth = String.valueOf(this.dailyIncreaseAnalysisesService.videoGrowthAd());

            logger.info(zeroTime + "---------每日零时时间");
            //将当天0时时间作为hash名，key为当前时间，value为增长量, 设置key过期时间为7天
            logger.info("h--" + zeroTime + "userGrowth");
            logger.info("hk--" + nowTime + "userGrowth");
            redisTemplate.opsForHash().put(zeroTime + "videoGrowth", nowTime + "videoGrowth", videoGrowth);
            redisTemplate.expire(zeroTime + "videoGrowth", 7, TimeUnit.DAYS);
            logger.info("查询每小时用户增量成功");

            logger.info("h--" + zeroTime + "videoGrowth");
            logger.info("hk--" + nowTime + "videorowth");
            redisTemplate.opsForHash().put(zeroTime + "userGrowth", nowTime + "userGrowth", userGrowth);
            redisTemplate.expire(zeroTime + "userGrowth", 7, TimeUnit.DAYS);
            logger.info("查询每小时视频增量成功");

        } catch (Exception e) {
            logger.error("每小时更新用户，视频增长量失败" + e.getMessage());
        }

    }

    public void onceLoadRandVideos() {
        logger.info("缓存所有已审核视频");
        this.operateService.allReviewedVideosToCache();
    }


    public void realEveryDayUserAndVideoGrowth() {
        try {
            logger.info("realEveryDayUserAndVideoGrowth开始执行");
            Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-10-27 00:00:00");
            logger.info("startTime" + startDate.toString());
            Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-12-20 00:00:00");
            logger.info("endTime" + endDate.toString());
            Date date = startDate;
            Calendar c = Calendar.getInstance();
            int users = 0;
            int videos = 0;
            while (true) {
                users = this.userService.userDayGrowthed(date);
                logger.info("users---" + users);
                videos = this.videoService.videoDayGrowthed(date);
                logger.info("videos---" + videos);
                this.dailyIncreaseAnalysisesService.addUserAndVideoDayGrowth(date, users, videos);
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                date = c.getTime();
                logger.info("当前时间" + date.toString());
                if (date.equals(endDate)) {
                    break;
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}