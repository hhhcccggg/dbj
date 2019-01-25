package com.zwdbj.server.mobileapi.service.video.service;

import com.ecwid.consul.v1.ConsulClient;
import com.github.pagehelper.Page;
import com.zwdbj.server.discoverapiservice.videorandrecommend.service.VideoRandRecommendService;
import com.zwdbj.server.mobileapi.middleware.mq.MQWorkSender;
import com.zwdbj.server.mobileapi.service.pet.model.PetModelDto;
import com.zwdbj.server.mobileapi.service.pet.service.PetService;
import com.zwdbj.server.mobileapi.service.shop.comments.model.CommentVideoInfo;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.mobileapi.model.EntityKeyModel;
import com.zwdbj.server.mobileapi.config.AppConfigConstant;
import com.zwdbj.server.utility.consulLock.unit.Lock;
import com.zwdbj.server.utility.model.ResponseCoin;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.comment.service.CommentService;
import com.zwdbj.server.mobileapi.service.heart.service.HeartService;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageInput;
import com.zwdbj.server.mobileapi.service.messageCenter.service.MessageCenterService;
import com.zwdbj.server.mobileapi.service.qiniu.service.QiniuService;
import com.zwdbj.server.mobileapi.service.resourceRefGoods.service.ResRefGoodsService;
import com.zwdbj.server.mobileapi.service.review.service.ReviewService;
import com.zwdbj.server.mobileapi.service.share.model.ShareDto;
import com.zwdbj.server.mobileapi.service.tag.service.TagService;
import com.zwdbj.server.mobileapi.service.user.model.UserModel;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.service.video.mapper.IVideoMapper;
import com.zwdbj.server.mobileapi.service.heart.model.HeartModel;
import com.zwdbj.server.mobileapi.model.HeartInput;
import com.zwdbj.server.mobileapi.service.video.model.*;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class VideoService {
    @Autowired
    protected IVideoMapper videoMapper;
    @Autowired
    protected UserService userService;
    @Autowired
    protected QiniuService qiniuService;
    @Autowired
    protected HeartService heartService;
    @Autowired
    protected ResRefGoodsService resRefGoodsService;
    @Autowired
    protected TagService tagService;
    @Autowired
    protected MessageCenterService messageCenterService;
    @Autowired
    protected ReviewService reviewService;
    @Autowired
    protected StringRedisTemplate stringRedisTemplate;
    @Autowired
    protected CommentService commentService;
    @Autowired
    protected PetService petService;
    @Autowired
    protected UserAssetServiceImpl userAssetServiceImpl;
    @Autowired
    protected VideoRandRecommendService videoRandRecommendService;
    @Autowired
    protected RedisTemplate redisTemplate;
    protected Logger logger = LoggerFactory.getLogger(VideoService.class);

    public ServiceStatusInfo<EntityKeyModel<String>> getGoods(long videoId) {
        String goods = this.resRefGoodsService.getGoods(videoId);
        if (goods == null) return new ServiceStatusInfo<>(1, "未找到关联的商品", null);
        return new ServiceStatusInfo<>(0, "", new EntityKeyModel<>(goods));
    }

    public long publicCommentVideo(CommentVideoInfo input) {
        String videoKey = input.getVideoUrl();
        String coverImageUrl = input.getCoverImageUrl();
        String firstFrameUrl = input.getFirstFrameUrl();
        input.setVideoUrl(qiniuService.url(videoKey));
        if ((coverImageUrl == null || ("").equals(coverImageUrl)) && (firstFrameUrl != null && !("").equals(firstFrameUrl))) {
            input.setCoverImageUrl(qiniuService.url(videoKey) + "?vframe/png/offset/1");
            input.setFirstFrameUrl(qiniuService.url(firstFrameUrl));
        } else if ((coverImageUrl != null && !("").equals(coverImageUrl)) && (firstFrameUrl == null || ("").equals(firstFrameUrl))) {
            input.setCoverImageUrl(qiniuService.url(coverImageUrl));
            input.setFirstFrameUrl(qiniuService.url(videoKey) + "?vframe/png/offset/1");
        } else if ((coverImageUrl == null || ("").equals(coverImageUrl)) && (firstFrameUrl == null || ("").equals(firstFrameUrl))) {
            input.setCoverImageUrl(qiniuService.url(videoKey) + "?vframe/png/offset/1");
            input.setFirstFrameUrl(qiniuService.url(videoKey) + "?vframe/png/offset/1");
        } else {
            input.setCoverImageUrl(qiniuService.url(coverImageUrl));
            input.setFirstFrameUrl(qiniuService.url(firstFrameUrl));
        }
        long videoId = UniqueIDCreater.generateID();
        long userId = JWTUtil.getCurrentId();
        if (userId <= 0) return 0;
        if (input.getLatitude() == 0 && input.getLongitude() == 0) {
            input.setAddress("");
        }
        videoMapper.publishCommentVideo(videoId, input, userId);
        // 审核信息加入到消息队列
        if (videoKey != null && videoKey.length() > 0) {
            QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData
                    = QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData.newBuilder()
                    .setDataId(videoId)
                    .setDataType(2)
                    .setResContent(videoKey)
                    .setResType(1)
                    .build();
            this.reviewService.reviewQiniuRes(resData);
        }
        if (coverImageUrl != null && coverImageUrl.length() > 0) {
            QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData
                    = QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData.newBuilder()
                    .setDataId(videoId)
                    .setDataType(2)
                    .setResContent(coverImageUrl)
                    .setResType(0)
                    .build();
            this.reviewService.reviewQiniuRes(resData);
        }

        if (firstFrameUrl != null && firstFrameUrl.length() > 0 && !(firstFrameUrl.equals(coverImageUrl))) {
            QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData
                    = QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData.newBuilder()
                    .setDataId(videoId)
                    .setDataType(2)
                    .setResContent(firstFrameUrl)
                    .setResType(0)
                    .build();
            logger.info("我是没重复的第一帧" + firstFrameUrl);
            this.reviewService.reviewQiniuRes(resData);
        }
        return videoId;
    }

    public ServiceStatusInfo<Long> publicVideo(VideoPublishInput input) {

        String videoKey = input.getVideoKey();
        String coverImageKey = input.getCoverImageKey();
        String firstFrameImageKey = input.getFirstFrameUrl();
        input.setVideoKey(qiniuService.url(videoKey));
        if ((coverImageKey == null || ("").equals(coverImageKey)) && (firstFrameImageKey != null && !("").equals(firstFrameImageKey))) {
            input.setCoverImageKey(qiniuService.url(videoKey) + "?vframe/png/offset/1");
            input.setFirstFrameUrl(qiniuService.url(firstFrameImageKey));
        } else if ((coverImageKey != null && !("").equals(coverImageKey)) && (firstFrameImageKey == null || ("").equals(firstFrameImageKey))) {
            input.setCoverImageKey(qiniuService.url(coverImageKey));
            input.setFirstFrameUrl(qiniuService.url(videoKey) + "?vframe/png/offset/1");
        } else if ((coverImageKey == null || ("").equals(coverImageKey)) && (firstFrameImageKey == null || ("").equals(firstFrameImageKey))) {
            input.setCoverImageKey(qiniuService.url(videoKey) + "?vframe/png/offset/1");
            input.setFirstFrameUrl(qiniuService.url(videoKey) + "?vframe/png/offset/1");
        } else {
            input.setCoverImageKey(qiniuService.url(coverImageKey));
            input.setFirstFrameUrl(qiniuService.url(firstFrameImageKey));
        }

        long videoId = UniqueIDCreater.generateID();
        long userId = JWTUtil.getCurrentId();
        if (userId <= 0) return new ServiceStatusInfo<>(1,"请登录后再发布视频",null,null);
        if (input.getTags() != null) {
            String[] tags = input.getTags().split(",");
            //TODO 更新标签的数据
            this.tagService.everyTagCount(tags);
        }
        if (input.getLatitude() == 0 && input.getLongitude() == 0) {
            input.setAddress("");
        }

        videoMapper.publicVideo(videoId, userId, input);
        //每日任务小饼干
        boolean keyExist = this.redisTemplate.hasKey("user_everydayTask_isFirstPublicVideo:"+userId);
        ResponseCoin coin =null;
        if (!keyExist) {
            LocalTime midnight = LocalTime.MIDNIGHT;
            LocalDate today = LocalDate.now();
            LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
            LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
            long s = TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(), tomorrowMidnight).toNanos());
            this.redisTemplate.opsForValue().set("user_everydayTask_isFirstPublicVideo:" + userId, userId+":hasFirstPublicVideo", s, TimeUnit.SECONDS);
            this.userAssetServiceImpl.userIsExist(userId);
            UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
            userCoinDetailAddInput.setStatus("SUCCESS");
            userCoinDetailAddInput.setNum(5);
            userCoinDetailAddInput.setTitle("每天首次发布视频获得小饼干"+5+"个");
            userCoinDetailAddInput.setType("TASK");
            this.userAssetServiceImpl.userPlayCoinTask(userCoinDetailAddInput,userId,"TASK",5,"EVERYDAYFIRSTPUBLICVIDEO","DONE");
            coin= new ResponseCoin();
            coin.setCoins(5);
            coin.setMessage("每天首次发布视频获得小饼干"+5+"个");
        }

        UserModel userModel = this.userService.findUserById(userId);
        // 审核信息加入到消息队列
        if (/*userModel.isReviewed() &&*/ videoKey != null && videoKey.length() > 0) {
            QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData
                    = QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData.newBuilder()
                    .setDataId(videoId)
                    .setDataType(2)
                    .setResContent(videoKey)
                    .setResType(1)
                    .build();
            this.reviewService.reviewQiniuRes(resData);
        }
        if (/*userModel.isReviewed() &&*/ coverImageKey != null && coverImageKey.length() > 0) {
            QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData
                    = QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData.newBuilder()
                    .setDataId(videoId)
                    .setDataType(2)
                    .setResContent(coverImageKey)
                    .setResType(0)
                    .build();
            this.reviewService.reviewQiniuRes(resData);
        }

        if (/*userModel.isReviewed() && */firstFrameImageKey != null && firstFrameImageKey.length() > 0 && !(firstFrameImageKey.equals(coverImageKey))) {
            QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData resData
                    = QueueWorkInfoModel.QueueWorkQiniuWaitReviewResData.newBuilder()
                    .setDataId(videoId)
                    .setDataType(2)
                    .setResContent(firstFrameImageKey)
                    .setResType(0)
                    .build();
            logger.info("我是没重复的第一帧" + firstFrameImageKey);
            this.reviewService.reviewQiniuRes(resData);
        }
        return new ServiceStatusInfo<>(0,"",videoId,coin);
    }

    // TODO 未来优化性能检测
    public List<VideoInfoDto> nearby(double longitude, double latitude, float distance) {
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.nearby(longitude, latitude, distance);
        if (videoInfoDtos == null) return null;
        for (VideoInfoDto dto : videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        return videoInfoDtos;
    }

    public List<VideoInfoDto> listHot(Page<VideoInfoDto> pageInfo, int pageSize) {
//        long userId = JWTUtil.getCurrentId();
//        if (userId>0) {
//            List<Long> videoIDS = this.videoRandRecommendService.fetchVideo("u_"+String.valueOf(userId),pageSize);
//            if(videoIDS.size()==0) return new ArrayList<>();
//            List<VideoInfoDto> recommendVideos = this.videoMapper.listIds(StringUtils.join(videoIDS.toArray(),","));
//            if (recommendVideos!=null) {
//                for (VideoInfoDto dto:recommendVideos) {
//                    loadVideoInfoDto(dto);
//                }
//            }
//            return recommendVideos;
//        }
        boolean isNeedGetCommend = pageInfo.getPageNum() < 4;
        String recommendIds = null;
        if (this.stringRedisTemplate.hasKey(AppConfigConstant.REDIS_VIDEO_RECOMMEND_KEY) && isNeedGetCommend) {
            try {
                recommendIds = (String) this.stringRedisTemplate.opsForValue().get(AppConfigConstant.REDIS_VIDEO_RECOMMEND_KEY);
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                logger.error(ex.getStackTrace().toString());
            }
        }
        boolean isHaveRecommend = (recommendIds != null && recommendIds.length() > 0);
        String ids = null;
        if (isHaveRecommend) {
            String[] idsArr = recommendIds.split(",");
            isHaveRecommend = idsArr.length > 0;
            int fromIndex = (pageInfo.getPageNum() - 1) * 4;
            if (fromIndex < idsArr.length - 1) {
                int endIndex = fromIndex + 3;
                if (endIndex >= idsArr.length - 1) {
                    endIndex = idsArr.length - 1;
                }
                int len = endIndex - fromIndex + 1;
                String[] desIdsArr = new String[len];
                System.arraycopy(idsArr, fromIndex, desIdsArr, 0, len);
                ids = String.join(",", desIdsArr);
            } else {
                isHaveRecommend = false;
            }
        }

        List<VideoInfoDto> videoInfoDtos = null;
        if (!isHaveRecommend) {
            videoInfoDtos = this.videoMapper.listHot("");
        } else {
            videoInfoDtos = this.videoMapper.listHot("id not in (" + ids + ")");
        }
        if (videoInfoDtos == null) return null;
        for (VideoInfoDto dto : videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        //加载推荐短视频
        if (isHaveRecommend) {
            List<VideoInfoDto> recommendVideos = this.videoMapper.listIds(ids);
            if (recommendVideos != null) {
                for (VideoInfoDto dto : recommendVideos) {
                    loadVideoInfoDto(dto);
                }
            }
            //合并数据
            recommendVideos.addAll(videoInfoDtos);
            return recommendVideos;
        }
        return videoInfoDtos;
    }

    public List<VideoInfoDto> listLatest(Page<VideoInfoDto> pageInfo) {
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.listLatest();
        if (videoInfoDtos == null) return null;
        for (VideoInfoDto dto : videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        return videoInfoDtos;
    }

    /**
     * 根据标签搜索短视频
     */
    public List<VideoInfoDto> listByTag(String tag) {
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.listByTag(tag);
        if (videoInfoDtos == null) return null;
        for (VideoInfoDto dto : videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        return videoInfoDtos;

    }

    /**
     * 根据标签ID搜索短视频
     */
    public List<VideoInfoDto> listByTagName(String name, int type) {
        List<VideoInfoDto> videoInfoDtos = null;
        if (type == 0) {
            videoInfoDtos = this.videoMapper.listByTagName1(name);
        } else if (type == 1) {
            videoInfoDtos = this.videoMapper.listByTagName2(name);
        }
        if (videoInfoDtos == null) return null;
        for (VideoInfoDto dto : videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        return videoInfoDtos;

    }

    /**
     * 获取userId关注的用户的视频列表
     *
     * @param userId
     * @return
     */
    public List<VideoDetailInfoDto> listByUserFollowed(long userId) {
        List<VideoDetailInfoDto> dtos = this.videoMapper.myFollowedVideos(userId);
        if (dtos == null) return null;
        for (VideoDetailInfoDto dto : dtos) {
            dto.setLinkProductUrl(AppConfigConstant.getShopListUrl(dto.getId(), "video"));
            dto.setShareTitle(dto.getTitle());
            String videoUserNickName = this.userService.getUserName(dto.getUserId());
            if (videoUserNickName == null) {
                videoUserNickName = "爪子用户";
            }
            dto.setShareContent(videoUserNickName + "拍摄的宠物短视频");
            dto.setShareUrl(AppConfigConstant.getShareUrl(dto.getId(), "video"));
            List<PetModelDto> petModelDtos = new ArrayList<>();
            String pets = dto.getLinkPets();
            if (pets != null && pets.length() != 0) {
                String[] petIds = pets.split(",");
                for (String petId : petIds) {
                    PetModelDto petModelDto = this.petService.get(Long.valueOf(petId));
                    petModelDtos.add(petModelDto);
                }
                dto.setPetModelDtoList(petModelDtos);
            }
            loadVideoInfoDto(dto);
        }
        return dtos;
    }

    public List<VideoInfoDto> videosByUser(long userId) {
        List<VideoInfoDto> dtos = null;
        if (userId == JWTUtil.getCurrentId()) {
            dtos = this.videoMapper.videosByUser(userId);
        }
        if (userId != JWTUtil.getCurrentId()) {
            dtos = this.videoMapper.videosByUser1(userId);
        }

        if (dtos == null) return null;
        for (VideoInfoDto dto : dtos) {
            loadVideoInfoDto(dto);
        }
        return dtos;
    }

    public List<VideoInfoDto> videosByHearted(long userId) {
        List<VideoInfoDto> dtos = this.videoMapper.videosByHearted(userId);
        if (dtos == null) return null;
        for (VideoInfoDto dto : dtos) {
            loadVideoInfoDto(dto);
        }
        return dtos;
    }

    public VideoDetailInfoDto video(long id) {
        VideoDetailInfoDto dto = this.videoMapper.video(id);
        loadVideoInfoDto(dto);
        if (dto != null) {
            dto.setLinkProductUrl(AppConfigConstant.getShopListUrl(id, "video"));
            dto.setShareTitle(dto.getTitle());
            String videoUserNickName = this.userService.getUserName(dto.getUserId());
            if (videoUserNickName == null) {
                videoUserNickName = "爪子用户";
            }
            dto.setShareContent(videoUserNickName + "拍摄的宠物短视频");
            dto.setShareUrl(AppConfigConstant.getShareUrl(id, "video"));
        }
        return dto;
    }

    public void updatePlayCount(long id) {
        this.videoMapper.updateVideoField("playCount=playCount+1", id);
        this.videoWegiht(id);
    }

    public void updateShareCount(long id) {
        this.videoMapper.updateVideoField("shareCount=shareCount+1", id);
        this.videoWegiht(id);
    }

    public void updateField(String fields, long id) {
        this.videoMapper.updateVideoField(fields, id);
    }

    public List<VideoInfoDto> next(long id) {
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.next(id);
        for (VideoInfoDto dto : videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        return videoInfoDtos;
    }

    @Transactional
    public ServiceStatusInfo<Long> deleteVideo(Long id) {
        long userId = JWTUtil.getCurrentId();
        if (userId <= 0) return new ServiceStatusInfo<>(1, "请重新登录", null);
        Long video = this.videoMapper.deleteVideo(id, userId);
        //this.heartService.findVideoHeart(id);
        Long heart = this.heartService.deleteVideoHeart(id);
        Long comment = this.commentService.deleteVideoComments(id);
        if (video != 0) {
            try {
                this.videoRandRecommendService.popVideo(id);
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
            return new ServiceStatusInfo<>(0, "删除成功", null);
        } else {
            return new ServiceStatusInfo<>(1, "删除失败", null);
        }
    }

    @Transactional
    public ServiceStatusInfo<VideoHeartStatusDto> heart(HeartInput input) {
        long userId = JWTUtil.getCurrentId();
        if (userId <= 0) return new ServiceStatusInfo<>(1, "请重新登录", null,null);
        VideoHeartStatusDto videoHeartStatusDto = new VideoHeartStatusDto();
        videoHeartStatusDto.setVideoId(input.getId());
        HeartModel heartModel = this.heartService.findHeart(userId, input.getId());
        Long VUserId = this.videoMapper.findUserIdByVideoId(input.getId());
        if (heartModel != null && input.isHeart()) {
            return new ServiceStatusInfo<>(1, "已经点赞过", null,null);
        }
        if (heartModel != null && !input.isHeart()) {
            this.heartService.unHeart(userId, input.getId());
            this.videoMapper.addHeart(input.getId(), -1);
            this.userService.addHeart(VUserId, -1);
            this.videoWegiht(input.getId());
            videoHeartStatusDto.setHeart(false);
            return new ServiceStatusInfo<>(0, "取消成功", videoHeartStatusDto,null);
        }

        if (input.isHeart()) {
            long id = UniqueIDCreater.generateID();
            ServiceStatusInfo<Long> isFirst = this.heartService.heart(id, userId, input.getId(), 0);
            this.videoMapper.addHeart(input.getId(), 1);
            this.userService.addHeart(VUserId, 1);
            VideoDetailInfoDto detailInfoDto = this.video(input.getId());
            if (detailInfoDto != null) {
                MessageInput msgInput = new MessageInput();
                msgInput.setCreatorUserId(userId);
                msgInput.setDataContent("{\"resId\":\"" + input.getId() + "\",\"type\":\"0\"}");
                msgInput.setMessageType(1);
                this.messageCenterService.push(msgInput, detailInfoDto.getUserId());
            }

            this.videoWegiht(input.getId());
            videoHeartStatusDto.setHeart(true);
            if (isFirst.getCoins()!=null)
                return new ServiceStatusInfo<>(0, "点赞成功", videoHeartStatusDto,isFirst.getCoins());
            return new ServiceStatusInfo<>(0, "点赞成功", videoHeartStatusDto);
        } else {
            return new ServiceStatusInfo<>(1, "取消失败", null,null);
        }
    }

    /**
     * TODO 调用此方法的源待优化
     *
     * @param dto
     */
    protected void loadVideoInfoDto(VideoInfoDto dto) {
        if (dto == null) return;
        // TODO 考虑增加缓存||其他方式
        // 关注
        long uid = JWTUtil.getCurrentId();
        if (uid <= 0) dto.setHearted(false);
        HeartModel heartModel = this.heartService.findHeart(uid, dto.getId());
        dto.setHearted(heartModel != null);
        dto.setFollow(this.userService.isFollower(dto.getUserId(), uid));
        //头像
        UserModel userModel = this.userService.findUserById(dto.getUserId());
        if (userModel != null) {
            dto.setUserAvatarUrl(userModel.getAvatarUrl());
            dto.setUserNickName(userModel.getNickName());
        }
    }

    public String findLinkPets(Long id) {
        String linkPet = this.videoMapper.findLinkPets(id);
        if (linkPet == null) return null;
        return linkPet;
    }

    public ShareDto doShare(Long id) {
        ShareDto sharedto = this.videoMapper.doShare(id);
        if (sharedto == null) return null;
        if (sharedto.getTags() == null) sharedto.setTags("");
        return sharedto;
    }

    public void addShareCount(Long id) {
        this.videoMapper.addShareCount(id);
    }


    /**
     * 处理视频权重
     *
     * @param id
     */
    public void videoWegiht(long id) {
        String cacheKey = AppConfigConstant.getRedisVideoWeightTaskKey(id);
        if (this.stringRedisTemplate.hasKey(cacheKey)) return;
        //添加到消息队列
        try {
            QueueWorkInfoModel.QueueWorkVideoWeightData workVideoWeightData = QueueWorkInfoModel.QueueWorkVideoWeightData.newBuilder()
                    .setId(id).build();
            QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.VIDEO_WEIGHT)
                    .setVideoWeightData(workVideoWeightData)
                    .build();
            MQWorkSender.shareSender().send(workInfo);
            this.stringRedisTemplate.opsForValue().set(cacheKey, "OK", AppConfigConstant.VIDEO_WEIGHT_CALCULATE_INTERVAL, TimeUnit.SECONDS);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }


    public ServiceStatusInfo<VideoPlayTourDto> getPlayTout(Long videoId) {

        VideoPlayTourDto videoPlayTourDto = new VideoPlayTourDto();
        try {
            Long userId = JWTUtil.getCurrentId();
            videoPlayTourDto.setTipCount(videoMapper.searchTipCount(videoId));
            videoPlayTourDto.setCoins(userAssetServiceImpl.getCoinsByUserId(userId).getData());
            return new ServiceStatusInfo<>(0, "", videoPlayTourDto);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "获取打赏界面失败" + e.getMessage(), null);
        }
    }

    //视频作者获得的打赏
    @Transactional
    public void videoAuthorIncome(Long authorId, int income) {
        this.userAssetServiceImpl.userIsExist(authorId);
        UserCoinDetailAddInput addInput = new UserCoinDetailAddInput();
        addInput.setNum(income);
        addInput.setType("INCOME");
        addInput.setTitle("视频获得的打赏");
        userAssetServiceImpl.addUserCoinDetailSuccess(authorId, addInput);
        userAssetServiceImpl.updateUserCoinType(authorId, "INCOME", income);
        userAssetServiceImpl.updateUserAsset(authorId, income);
    }

    @Transactional
    public ServiceStatusInfo<Integer> playTout(int coins, Long videoId) {
        //TODO 小饼干变动时 考虑到线程安全，需要加锁
        if (coins < 1 || coins > 100000000) return new ServiceStatusInfo<>(1, "您输入的小饼干数量有误", null,null);
        long userId = JWTUtil.getCurrentId();
        String key = String.valueOf(userId) + videoId;
        ConsulClient consulClient = new ConsulClient("localhost", 8500);    // 创建与Consul的连接
        Lock lock = new Lock(consulClient, "mobileapi", "playTout-lockKey:" + key);
        try {
            if (lock.lock(true, 500L, 3)) {

                //获取视频作者id
                Long authorId = videoMapper.findUserIdByVideoId(videoId);

                if (authorId == userId) return new ServiceStatusInfo<>(1, "不能给自己打赏", null,null);
                //查看此用户是否存在小饼干账户
                this.userAssetServiceImpl.userIsExist(userId);
                int authorIncome = coins;
                //用户小饼干总数
                long counts = userAssetServiceImpl.getCoinsByUserId().getData();
                if (counts < 0 || counts < coins) {
                    return new ServiceStatusInfo<>(1, "您的小饼干不足，请充值小饼干", null,null);
                }
                //获取用户小饼干类型数量详情
                int task = (int) userAssetServiceImpl.getUserCoinType(userId, "TASK").getData().getCoins();
                int pay = (int) userAssetServiceImpl.getUserCoinType(userId, "PAY").getData().getCoins();
                int other = (int) userAssetServiceImpl.getUserCoinType(userId, "OTHER").getData().getCoins();
                //每日任务小饼干
                boolean keyExist = this.redisTemplate.hasKey("user_everydayTask_isFirstPlayTout:"+userId);
                ResponseCoin responseCoin=null;
                if (!keyExist) {
                    LocalTime midnight = LocalTime.MIDNIGHT;
                    LocalDate today = LocalDate.now();
                    LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
                    LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
                    long s = TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(), tomorrowMidnight).toNanos());
                    this.redisTemplate.opsForValue().set("user_everydayTask_isFirstPlayTout:" + userId, userId+":hasFirstPlayTout", s, TimeUnit.SECONDS);
                    this.userAssetServiceImpl.userIsExist(userId);
                    UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
                    userCoinDetailAddInput.setStatus("SUCCESS");
                    userCoinDetailAddInput.setNum(5);
                    userCoinDetailAddInput.setTitle("每日首次打赏获得小饼干" + 5 + "个");
                    userCoinDetailAddInput.setType("TASK");
                    this.userAssetServiceImpl.userPlayCoinTask(userCoinDetailAddInput, userId, "TASK", 5,"EVERYDAYFIRSTTIP","DONE");
                    responseCoin= new ResponseCoin();
                    responseCoin.setCoins(5);
                    responseCoin.setMessage("每日首次打赏获得小饼干" + 5 + "个");
                }

                if (task >= coins) {
                    //task类型的小饼干大于等于打赏数，则全部用task打赏
                    UserCoinDetailAddInput addTaskInput = new UserCoinDetailAddInput();
                    addTaskInput.setNum(-coins);
                    addTaskInput.setType("TASK");
                    addTaskInput.setTitle("视频打赏消费");

                    //修改打赏用户小饼干明细
                    userAssetServiceImpl.addUserCoinDetailSuccess(userId, addTaskInput);
                    userAssetServiceImpl.updateUserCoinType(userId, "TASK", -coins);
                    userAssetServiceImpl.updateUserAsset(userId, -authorIncome);
                    //增加视频打赏次数
                    videoMapper.addTipCount(videoId);
                    //增加视频获得的打赏详情
                    this.userAssetServiceImpl.addVideoTipDetail(videoId, userId, authorIncome);

                    //修改视频作者小饼干明细
                    videoAuthorIncome(authorId, authorIncome);

                    return new ServiceStatusInfo<>(0, "", 1,responseCoin);
                } else {
                    if (task != 0) {
                        //减去task的小饼干后仍需要支付的小饼干数
                        coins = coins - task;
                        userAssetServiceImpl.updateUserCoinType(userId, "TASK", -task);
                    }
                    if (pay >= coins) {
                        UserCoinDetailAddInput addPayInput = new UserCoinDetailAddInput();
                        addPayInput.setNum(-authorIncome);
                        addPayInput.setType("INCOME");
                        addPayInput.setTitle("视频打赏消费");

                        userAssetServiceImpl.addUserCoinDetailSuccess(userId, addPayInput);
                        userAssetServiceImpl.updateUserCoinType(userId, "PAY", -coins);
                        userAssetServiceImpl.updateUserAsset(userId, -authorIncome);
                        //增加视频获得的打赏详情
                        this.userAssetServiceImpl.addVideoTipDetail(videoId, userId, authorIncome);
                        videoMapper.addTipCount(videoId);

                        videoAuthorIncome(authorId, authorIncome);

                        return new ServiceStatusInfo<>(0, "", 1,responseCoin);
                    } else {
                        if (pay != 0) {
                            coins = coins - pay;//减去pay的小饼干后仍需要支付的小饼干数
                            userAssetServiceImpl.updateUserCoinType(userId, "PAY", -pay);
                        }
                        if (other >= coins) {
                            UserCoinDetailAddInput addOtherInput = new UserCoinDetailAddInput();
                            addOtherInput.setNum(-authorIncome);
                            addOtherInput.setType("INCOME");
                            addOtherInput.setTitle("视频打赏消费");

                            userAssetServiceImpl.addUserCoinDetailSuccess(userId, addOtherInput);
                            userAssetServiceImpl.updateUserCoinType(userId, "OTHER", -coins);
                            userAssetServiceImpl.updateUserAsset(userId, -authorIncome);
                            //增加视频获得的打赏详情
                            this.userAssetServiceImpl.addVideoTipDetail(videoId, userId, authorIncome);
                            videoMapper.addTipCount(videoId);

                            videoAuthorIncome(authorId, authorIncome);

                            return new ServiceStatusInfo<>(0, "", 1,responseCoin);
                        } else {
                            if (other != 0) {
                                coins = coins - other;//减去other的小饼干后还需要支付的小饼干数
                                userAssetServiceImpl.updateUserCoinType(userId, "OTHER", -other);
                            }
                            UserCoinDetailAddInput addIncomeInput = new UserCoinDetailAddInput();
                            addIncomeInput.setNum(-authorIncome);
                            addIncomeInput.setType("INCOME");
                            addIncomeInput.setTitle("视频打赏消费");
                            userAssetServiceImpl.addUserCoinDetailSuccess(userId, addIncomeInput);
                            userAssetServiceImpl.updateUserCoinType(userId, "INCOME", -coins);
                            userAssetServiceImpl.updateUserAsset(userId, -authorIncome);
                            //增加视频获得的打赏详情
                            this.userAssetServiceImpl.addVideoTipDetail(videoId, userId, authorIncome);
                            videoMapper.addTipCount(videoId);
                            videoAuthorIncome(authorId, authorIncome);
                            return new ServiceStatusInfo<>(0, "", 1,responseCoin);
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            return new ServiceStatusInfo<>(1, "打赏失败" + e.getMessage(), null,null);
        } finally {
            lock.unlock();
        }
        return new ServiceStatusInfo<>(1, "打赏失败", null,null);
    }

    /**
     * 用户是否为每天的首次发布视频
     */
    public boolean isFirstPublicVideo(long userId){
        String key = "user_everydayTask_isFirstPublicVideo:"+userId;
        ConsulClient consulClient = new ConsulClient("localhost", 8500);    // 创建与Consul的连接
        Lock lock = new Lock(consulClient, "mobileapi",  key);
        try {
            if (lock.lock(true, 500L, 1)){
                int result = this.videoMapper.isFirstPublicVideo(userId);
                return result==0;
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return false;
    }

    public ServiceStatusInfo<List<VideoInfoDto>> getPetsVideo(long petId){
        try{
            List<VideoInfoDto> list= videoMapper.getPetsVideo(petId);
            return new ServiceStatusInfo<>(0,"",list);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }

    public ServiceStatusInfo<Long> getPetsHeartCount(long petId) {
        try {
            Long count = videoMapper.getPetsHeartCount(petId);
            return new ServiceStatusInfo<>(0, "", count);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

}
