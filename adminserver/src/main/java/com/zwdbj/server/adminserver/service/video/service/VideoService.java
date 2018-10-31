package com.zwdbj.server.adminserver.service.video.service;

import com.github.pagehelper.Page;
import com.zwdbj.server.adminserver.model.EntityKeyModel;
import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.heart.service.HeartService;
import com.zwdbj.server.adminserver.service.messageCenter.model.MessageInput;
import com.zwdbj.server.adminserver.service.messageCenter.service.MessageCenterService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.resourceRefGoods.service.ResRefGoodsService;
import com.zwdbj.server.adminserver.service.share.model.ShareDto;
import com.zwdbj.server.adminserver.service.tag.service.TagService;
import com.zwdbj.server.adminserver.service.user.model.UserModel;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.mapper.IVideoMapper;
import com.zwdbj.server.adminserver.service.heart.model.HeartModel;
import com.zwdbj.server.adminserver.model.HeartInput;
import com.zwdbj.server.adminserver.service.video.model.*;
import com.zwdbj.server.adminserver.shiro.JWTUtil;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class VideoService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
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
    private Logger logger = LoggerFactory.getLogger(VideoService.class);

    //短视频关联商品
    @Transactional
    public ServiceStatusInfo<Object> saveGoodsAd(long videoId, String goods) {
        int linkProduct=0;
        if (goods!=null&&goods.length()>0) {
            linkProduct=goods.split(",").length;
        }
        this.videoMapper.updateVideoField("linkProductCount="+linkProduct,videoId);
        String haveGoods = this.resRefGoodsService.getGoods(videoId);
        if (haveGoods==null) {
            this.resRefGoodsService.add(videoId,goods,0);
        } else {
            this.resRefGoodsService.update(videoId,goods);
        }
        return new ServiceStatusInfo<>(0,"",goods);
    }

    public ServiceStatusInfo<EntityKeyModel<String>> getGoodsAd(long videoId) {
        String goods = this.resRefGoodsService.getGoods(videoId);
        if (goods==null) return new ServiceStatusInfo<>(1,"未找到关联的商品",null);
        return new ServiceStatusInfo<>(0,"",new EntityKeyModel<>(goods));
    }

    public List<VideoInfoDto> complainVideosAd(AdVideoSearchComplainInput input){
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.complainVideosAd(input);
        return videoInfoDtos;
    }

    public ServiceStatusInfo<Long> verityAd (Long id, AdVideoVerityInput input){
        Long result=0L;
        try {
            result = this.videoMapper.verityAd(id,input);
            return new ServiceStatusInfo<>(0, "", result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "创建失败："+e.getMessage(), result);
        }
    }

    public List<AdVideoVerityInfoDto> verityListAd(AdVideoVerityInfoInput input){
        List<AdVideoVerityInfoDto> verityVideoMoDtos = this.videoMapper.verityListAd(input);
        return verityVideoMoDtos;
    }

    public List<AdVideoComplainInfoDto> complainInfoAd(Long toResId){
        List<AdVideoComplainInfoDto> videoComplainInfoDtos = this.videoMapper.complainInfoAd(toResId);
        return videoComplainInfoDtos;
    }

    public ServiceStatusInfo<Long> doComplainInfoAd(Long toResId, AdVideoDoComplainInput input){
        Long result=0L;
        try {
             result = this.videoMapper.doComplainInfoAd(toResId,input);
            return new ServiceStatusInfo<>(0, "", result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "修改失败："+e.getMessage(), result);
        }

    }

    public List<VideoInfoDto> searchAd(SearchVideoAdInput input) {
        List<VideoInfoDto> videoModelList = this.videoMapper.searchAd(input);
        if (videoModelList==null) return null;
        for (VideoInfoDto dto:videoModelList) {
            loadVideoInfoDto(dto);
        }
        return videoModelList;      
    }

    @Transactional
    public long publicVideo(VideoPublishInput input) {
        input.setCoverImageKey(qiniuService.url(input.getCoverImageKey()));
        input.setVideoKey(qiniuService.url(input.getVideoKey()));
        input.setFirstFrameUrl(qiniuService.url(input.getFirstFrameUrl()));
        long videoId = UniqueIDCreater.generateID();
        long userId = JWTUtil.getCurrentId();
        if (userId<=0) return 0;
        if (input.getTags()!=null) {
            String[] tags = input.getTags().split(",");
            //tagService.everyTagCount(tags);
        }
        videoMapper.publicVideo(videoId,userId,input);
        return videoId;
    }

    // TODO 未来优化性能检测
    public List<VideoInfoDto> nearby(double longitude,double latitude,float distance) {
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.nearby(longitude, latitude, distance);
        if (videoInfoDtos==null) return null;
        for (VideoInfoDto dto:videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        return videoInfoDtos;
    }

    public List<VideoInfoDto> listHot(Page<VideoInfoDto> pageInfo) {
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.listHot();
        if (videoInfoDtos==null) return null;
        for (VideoInfoDto dto:videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        return videoInfoDtos;
    }

    public List<VideoInfoDto> listLatest(Page<VideoInfoDto> pageInfo) {
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.listLatest();
        if (videoInfoDtos==null) return null;
        for (VideoInfoDto dto:videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        return videoInfoDtos;
    }

    /**
     * 获取userId关注的用户的视频列表
     * @param userId
     * @return
     */
    public List<VideoInfoDto> listByUserFollowed(long userId) {
        List<VideoInfoDto> dtos = this.videoMapper.myFollowedVideos(userId);
        if (dtos==null) return null;
        for(VideoInfoDto dto:dtos) {
            loadVideoInfoDto(dto);
        }
        return dtos;
    }

    public List<VideoInfoDto> videosByUser(long userId) {
        List<VideoInfoDto> dtos = this.videoMapper.videosByUser(userId);
        if (dtos==null) return null;
        for(VideoInfoDto dto:dtos) {
            loadVideoInfoDto(dto);
        }
        return dtos;
    }

    public List<VideoInfoDto> videosByHearted(long userId) {
        List<VideoInfoDto> dtos = this.videoMapper.videosByHearted(userId);
        if (dtos==null) return null;
        for(VideoInfoDto dto:dtos) {
            loadVideoInfoDto(dto);
        }
        return dtos;
    }

    public VideoDetailInfoDto video(long id) {
        VideoDetailInfoDto dto = this.videoMapper.video(id);
        loadVideoInfoDto(dto);
        if (dto!=null) {
            dto.setLinkProductUrl(AppConfigConstant.getShopListUrl(id,"video"));
            dto.setShareTitle(dto.getTitle());
            dto.setShareContent(dto.getTitle());
            dto.setShareUrl(AppConfigConstant.getShareUrl(id,"video"));
        }
        return dto;
    }

    public void updatePlayCount(long id) {
        this.videoMapper.updateVideoField("playCount=playCount+1",id);
    }

    public void  updateShareCount(long id) {
        this.videoMapper.updateVideoField("shareCount=shareCount+1",id);
    }
    public void updateField(String fields,long id) {
        this.videoMapper.updateVideoField(fields,id);
    }
    public List<VideoInfoDto> next(long id) {
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.next(id);
        if (videoInfoDtos==null) return null;
        for (VideoInfoDto dto:videoInfoDtos) {
            loadVideoInfoDto(dto);
        }
        return videoInfoDtos;
    }
    @Transactional
    public ServiceStatusInfo<Object> heart(HeartInput input) {
        long userId = JWTUtil.getCurrentId();
        if (userId<=0) return new ServiceStatusInfo<>(1,"请重新登录",null);
        HeartModel heartModel = this.heartService.findHeart(userId,input.getId());
        if (heartModel !=null && input.isHeart()) {
            return new ServiceStatusInfo<>(1,"已经点赞过",null);
        }
        if (heartModel ==null && !input.isHeart())
        {
            return new ServiceStatusInfo<>(0,"取消成功",null);
        }
        if (input.isHeart()) {
            long id = UniqueIDCreater.generateID();
            this.heartService.heart(id,userId,input.getId(),0);
            this.videoMapper.addHeart(input.getId(),1);
            this.userService.addHeart(userId,1);
            VideoDetailInfoDto detailInfoDto = this.video(input.getId());
            if (detailInfoDto != null) {
                MessageInput msgInput = new MessageInput();
                msgInput.setCreatorUserId(userId);
                msgInput.setDataContent("{\"resId\":\""+input.getId()+"\",\"type\":\"0\"}");
                msgInput.setMessageType(1);
                this.messageCenterService.push(msgInput,detailInfoDto.getUserId());
            }
            return new ServiceStatusInfo<>(0,"点赞成功",null);
        } else {
            this.heartService.unHeart(userId,input.getId());
            this.videoMapper.addHeart(input.getId(),-1);
            this.userService.addHeart(userId,-1);
            return new ServiceStatusInfo<>(0,"取消成功",null);

        }
    }

    /**
     * TODO 调用此方法的源待优化
     * @param dto
     */
    protected void loadVideoInfoDto(VideoInfoDto dto) {
        if(dto==null)return;
        // TODO 考虑增加缓存||其他方式
        // 关注
        long uid = JWTUtil.getCurrentId();
        if (uid<=0) dto.setHearted(false);
        HeartModel heartModel = this.heartService.findHeart(uid,dto.getId());
        dto.setHearted(heartModel!=null);
        dto.setFollow(this.userService.isFollower(dto.getUserId(),uid));
        //头像
        UserModel userModel = this.userService.findUserById(dto.getUserId());
        if (userModel!=null) {
            dto.setUserAvatarUrl(userModel.getAvatarUrl());
            dto.setUserNickName(userModel.getNickName());
        }
    }

    public String findLinkPets(Long id){
        String linkPet = this.videoMapper.findLinkPets(id);
        return linkPet;
    }
    public ShareDto doShare(Long id){
        ShareDto sharedto = this.videoMapper.doShare(id);
        return sharedto;
    }


    public Long findIncreasedVideoAd(int input){
        return this.videoMapper.findIncreasedVideoAd(input);
    }

    public Long findIncreasedVideoingAd(int input){
        return this.videoMapper.findIncreasedVideoingAd(input);
    }



    public Long everyIncreasedVideos(){
        Long increasedVideos = this.videoMapper.everyIncreasedVideos();
        return increasedVideos;
    }

    //审核相关
    public void updateReview(Long id,String reviewResultType){
        if ("pass".equals(reviewResultType)){
            this.videoMapper.passVideoReview(id);
        }else if ("block".equals(reviewResultType)){
            this.videoMapper.blockVideoReview(id);
        }else if ("review".equals(reviewResultType)){
            this.videoMapper.peopleVideoReview(id);
        }else {
            throw new RuntimeException("无数据+reviewResultType:::null");
        }
    }

    /**
     * 每天保存推荐视频
     */
    public void recVideosEveryday(String videoIds){
        if (videoIds==null)return;
        stringRedisTemplate.opsForValue().set(AppConfigConstant.REDIS_VIDEO_RECOMMEND_KEY,videoIds,24,TimeUnit.HOURS);
    }

    public void weightVideos(AdVideoWeightInput input){
        if (input==null)return;
        ValueOperations<String,AdVideoWeightInput> operations = redisTemplate.opsForValue();
        operations.set(AppConfigConstant.REDIS_VIDEO_WEIGHT_KEY,input,24,TimeUnit.HOURS);
    }

    /**
     * 定时任务
     * @return
     */
    public List<VideoHeartAndPlayCountDto> findHeartAndPlayCount(){
        List<VideoHeartAndPlayCountDto> videoHeartAndPlayCountDtos = this.videoMapper.findHeartAndPlayCount();
        return videoHeartAndPlayCountDtos;
    }

    public Long findVideoHeartCount(Long id){
        Long hearCount = this.videoMapper.findVideoHeartCount(id);
        return hearCount;
    }
}
