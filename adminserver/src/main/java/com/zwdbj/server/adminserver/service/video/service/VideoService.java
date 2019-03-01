package com.zwdbj.server.adminserver.service.video.service;

import com.alibaba.fastjson.JSON;
import com.zwdbj.server.adminserver.model.EntityKeyModel;
import com.zwdbj.server.adminserver.config.AppConfigConstant;
import com.zwdbj.server.adminserver.service.comment.model.CommentInfoDto;
import com.zwdbj.server.adminserver.service.comment.service.CommentService;
import com.zwdbj.server.adminserver.service.shop.service.products.model.ProductOut;
import com.zwdbj.server.adminserver.service.shop.service.products.service.ProductService;
import com.zwdbj.server.discoverapiservice.videorandrecommend.service.VideoRandRecommendService;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.heart.service.HeartService;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.service.resourceRefGoods.service.ResRefGoodsService;
import com.zwdbj.server.adminserver.service.tag.service.TagService;
import com.zwdbj.server.adminserver.service.user.model.UserModel;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.mapper.IVideoMapper;
import com.zwdbj.server.adminserver.service.heart.model.HeartModel;
import com.zwdbj.server.adminserver.service.video.model.*;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private ProductService productServiceImpl;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    protected VideoRandRecommendService videoRandRecommendService;
    private Logger logger = LoggerFactory.getLogger(VideoService.class);

    //短视频关联商品
    @Transactional
    public ServiceStatusInfo<Object> saveGoodsAd(long videoId, String goods) {
        int linkProduct = 0;
        if (goods != null && goods.length() > 0) {
            linkProduct = goods.split(",").length;
        }
        this.videoMapper.updateVideoField("linkProductCount=" + linkProduct, videoId);
        String haveGoods = this.resRefGoodsService.getGoods(videoId);
        if (haveGoods == null) {
            this.resRefGoodsService.add(videoId, goods, 0);
        } else {
            this.resRefGoodsService.update(videoId, goods);
        }
        return new ServiceStatusInfo<>(0, "", goods);
    }

    @Transactional
    public ServiceStatusInfo<Object> addTagForVideo(long videoId,VideoAddTagInput input){
            int a = this.videoMapper.addTagForVideo(videoId,input.getName());
            int b=0;
            if (a!=0)
                 b= this.tagService.updateTagResNum(input.getTagId(),1);
            if (b==0)return new ServiceStatusInfo<>(1, "添加主题失败", null);
            return new ServiceStatusInfo<>(0, "", b);
    }

    public ServiceStatusInfo<EntityKeyModel<String>> getGoodsAd(long videoId) {
        String goods = this.resRefGoodsService.getGoods(videoId);
        if (goods == null) return new ServiceStatusInfo<>(1, "未找到关联的商品", null);
        return new ServiceStatusInfo<>(0, "", new EntityKeyModel<>(goods));
    }

    public List<VideoInfoDto> complainVideosAd(AdVideoSearchComplainInput input) {
        List<VideoInfoDto> videoInfoDtos = this.videoMapper.complainVideosAd(input);
        return videoInfoDtos;
    }

    public ServiceStatusInfo<Long> verityAd(Long id, AdVideoVerityInput input) {
        Long result = 0L;
        try {
            result = this.videoMapper.verityAd(id, input);
            try {
                if (input.getStatus() == 0) {
                    this.videoRandRecommendService.pushNewVideo(id);
                }
            } catch (Exception ex) {
                logger.warn(ex.getMessage());
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建失败：" + e.getMessage(), result);
        }
    }

    public List<AdVideoVerityInfoDto> verityListAd(AdVideoVerityInfoInput input) {
        List<AdVideoVerityInfoDto> verityVideoMoDtos = this.videoMapper.verityListAd(input);
        return verityVideoMoDtos;
    }

    public List<AdVideoComplainInfoDto> complainInfoAd(Long toResId) {
        List<AdVideoComplainInfoDto> videoComplainInfoDtos = this.videoMapper.complainInfoAd(toResId);
        return videoComplainInfoDtos;
    }

    public ServiceStatusInfo<Long> doComplainInfoAd(Long toResId, AdVideoDoComplainInput input) {
        Long result = 0L;
        try {
            result = this.videoMapper.doComplainInfoAd(toResId, input);
            if (input.getTreatment() == 2) this.videoRandRecommendService.popVideo(toResId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改失败：" + e.getMessage(), result);
        }

    }

    public List<VideoInfoDto> searchAd(SearchVideoAdInput input) {
        List<VideoInfoDto> videoModelList = this.videoMapper.searchAd(input);
        if (videoModelList == null) return null;
        for (VideoInfoDto dto : videoModelList) {
            loadVideoInfoDto(dto);
        }
        return videoModelList;
    }

    public int findAllVideoNum(SearchVideoAdInput input) {
        return this.videoMapper.findAllVideoNum(input);
    }

    @Transactional
    public long publicVideo(VideoPublishInput input) {
        input.setCoverImageKey(qiniuService.url(input.getCoverImageKey()));
        input.setVideoKey(qiniuService.url(input.getVideoKey()));
        input.setFirstFrameUrl(qiniuService.url(input.getFirstFrameUrl()));
        long videoId = UniqueIDCreater.generateID();
        long userId = JWTUtil.getCurrentId();
        if (userId <= 0) return 0;
        if (input.getTags() != null) {
            String[] tags = input.getTags().split(",");
            //tagService.everyTagCount(tags);
        }
        videoMapper.publicVideo(videoId, userId, input);
        return videoId;
    }

    //获取视频打赏详情
    public ServiceStatusInfo<List<VideoTipDetails>> getVideoTipDetails(Long videoId) {
        List<VideoTipDetails> result = null;
        try {
            result = this.videoMapper.findVideoTipDetails(videoId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询视频打赏详情失败" + e.getMessage(), null);
        }
    }


    public VideoDetailInfoDto video(long id) {
        VideoDetailInfoDto dto = this.videoMapper.video(id);
        loadVideoInfoDto(dto);
        if (dto != null) {
            dto.setLinkProductUrl(AppConfigConstant.getShopListUrl(id, "video"));
            dto.setShareTitle(dto.getTitle());
            dto.setShareContent(dto.getTitle());
            dto.setShareUrl(AppConfigConstant.getShareUrl(id, "video"));
        }
        return dto;
    }

    public ServiceStatusInfo<List<VideoInfoDto>> getHomeVideos(String ids){
        List<VideoInfoDto> videoInfoDtos= new ArrayList<>();
        try {
            if (ids==null || ids.length()==0)return new ServiceStatusInfo<>(1,"请传入视频id",null);
            String[] videoIds = ids.split(",");
            for (String id:videoIds){
                VideoInfoDto dto = this.videoMapper.video2(Long.valueOf(id));
                if (dto!=null)
                    videoInfoDtos.add(dto);
            }
            return new ServiceStatusInfo<>(0,"",videoInfoDtos);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ServiceStatusInfo<>(1,"失败",null);

    }

    public void updatePlayCount(long id) {
        this.videoMapper.updateVideoField("playCount=playCount+1", id);
    }

    public void updateShareCount(long id) {
        this.videoMapper.updateVideoField("shareCount=shareCount+1", id);
    }

    public void updateField(String fields, long id) {
        this.videoMapper.updateVideoField(fields, id);
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


    public Long findIncreasedVideoAd(int input) {
        return this.videoMapper.findIncreasedVideoAd(input);
    }

    public Long findIncreasedVideoingAd(int input) {
        return this.videoMapper.findIncreasedVideoingAd(input);
    }


    public Long everyIncreasedVideos() {
        Long increasedVideos = this.videoMapper.everyIncreasedVideos();
        return increasedVideos;
    }

    //审核相关
    public void updateReview(Long id, String reviewResultType) {
        if ("pass".equals(reviewResultType)) {
            this.videoMapper.passVideoReview(id);
            this.videoRandRecommendService.pushNewVideo(id);
        } else if ("block".equals(reviewResultType)) {
            this.videoMapper.blockVideoReview(id);
        } else if ("review".equals(reviewResultType)) {
            this.videoMapper.peopleVideoReview(id);
        } else {
            throw new RuntimeException("无数据+reviewResultType:::null");
        }
    }

    /**
     * 每天保存推荐视频
     */
    public void recVideosEveryday(String videoIds) {
        if (videoIds == null) return;
        stringRedisTemplate.opsForValue().set(AppConfigConstant.REDIS_VIDEO_RECOMMEND_KEY, videoIds, 24, TimeUnit.HOURS);
    }

    public void weightVideos(AdVideoWeightInput input) {
        if (input == null) return;
        ValueOperations<String, AdVideoWeightInput> operations = redisTemplate.opsForValue();
        operations.set(AppConfigConstant.REDIS_VIDEO_WEIGHT_KEY, input);
    }

    public void calculateVideoWeight(long id) {
        ValueOperations<String, AdVideoWeightInput> operations = redisTemplate.opsForValue();
        if (!redisTemplate.hasKey(AppConfigConstant.REDIS_VIDEO_WEIGHT_KEY)) {
            logger.info("没有设置权重");
            return;
        }
        AdVideoWeightInput input = (AdVideoWeightInput) operations.get(AppConfigConstant.REDIS_VIDEO_WEIGHT_KEY);
        if (input == null) {
            logger.info("没有设置权重");
            return;
        }
        this.videoMapper.updateVideoField("recommendIndex = (heartCount * " + input.getHeartCount() / 100.0f + " + " +
                "commentCount*" + input.getCommentCount() / 100.0f + "+shareCount*" + input.getShareCount() / 100.0f + "+playCount*" + input.getPlayCount() / 100.0f + ")", id);
        operations.set(AppConfigConstant.REDIS_VIDEO_WEIGHT_KEY, input);
    }

    public ServiceStatusInfo<AdVideoWeightInput> getVideosWeight() {
        ValueOperations<String, AdVideoWeightInput> operations = redisTemplate.opsForValue();
        AdVideoWeightInput dto = operations.get(AppConfigConstant.REDIS_VIDEO_WEIGHT_KEY);
        if (dto != null) {
            return new ServiceStatusInfo<>(0, "", dto);
        } else {
            return new ServiceStatusInfo<>(1, "没取到数据：", null);
        }
    }

    /**
     * 定时任务
     *
     * @return
     */
    public List<VideoHeartAndPlayCountDto> findHeartAndPlayCount() {
        List<VideoHeartAndPlayCountDto> videoHeartAndPlayCountDtos = this.videoMapper.findHeartAndPlayCount();
        return videoHeartAndPlayCountDtos;
    }

    public Long getVideoPlayCount(Long id) {
        return this.videoMapper.getVideoPlayCount(id);
    }

    public Long findVideoHeartCount(Long id) {
        Long hearCount = this.videoMapper.findVideoHeartCount(id);
        return hearCount;
    }

    public ServiceStatusInfo<List<SuperStarVideos>> searchSuperStarVideos(long userId, String rank, String sort) {
        List<SuperStarVideos> result = null;
        try {
            result = videoMapper.searchSuperStarVideos(userId, rank, sort);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询代言人视频失败" + e.getMessage(), null);
        }
    }

    /**
     * 操作ES數據
     * @param id
     * @param operationEnum
     */
    public void operationByIdES(long id, QueueWorkInfoModel.QueueWorkVideoInfo.OperationEnum operationEnum) throws IOException {
        switch (operationEnum){
            case CREATE:
                Map<String,String> map = selectById(id);
                IndexRequest indexRequest = new IndexRequest("video","doc");
                indexRequest.source(JSON.toJSONString(map), XContentType.JSON);
                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                break;
            case UPDATE:
                map = selectById(id);
                indexRequest = new IndexRequest("video","doc");
                indexRequest.source(JSON.toJSONString(map), XContentType.JSON);
                restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
                break;
            case DELETE:
                DeleteRequest deleteRequest = new DeleteRequest("video","doc",String.valueOf(id));
                restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
                break;
        }
    }

    private Map<String,String> selectById(long id){
        Map<String,String> map = this.videoMapper.selectByIdES(id);
        //查询种类ID
        if(  "SHOPCOMMENT".equals(map.get("type")) ){
            CommentInfoDto commentInfoDto = commentService.findVideoIdES(Long.parseLong(String.valueOf(map.get("id"))));
            if(commentInfoDto != null) {
                ProductOut productOut = productServiceImpl.selectByIdPartial(commentInfoDto.getResourceOwnerId()).getData();
                if(productOut != null){
                    map.put("categoryId",String.valueOf(productOut.getCategoryId()));
                }
            }
        }
        return map;
    }

}
