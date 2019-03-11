package com.zwdbj.server.mobileapi.service.comment.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.ecwid.consul.v1.ConsulClient;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.model.EntityKeyModel;
import com.zwdbj.server.mobileapi.model.HeartInput;
import com.zwdbj.server.mobileapi.service.comment.mapper.ICommentMapper;
import com.zwdbj.server.mobileapi.service.comment.model.AddCommentInput;
import com.zwdbj.server.mobileapi.service.comment.model.AddCommentModel;
import com.zwdbj.server.mobileapi.service.comment.model.CommentInfoDto;
import com.zwdbj.server.mobileapi.service.heart.model.HeartModel;
import com.zwdbj.server.mobileapi.service.heart.service.HeartService;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageInput;
import com.zwdbj.server.mobileapi.service.messageCenter.service.MessageCenterService;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.service.userAssets.model.UserCoinDetailAddInput;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.mobileapi.service.video.model.VideoDetailInfoDto;
import com.zwdbj.server.mobileapi.service.video.service.VideoService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.consulLock.unit.Lock;
import com.zwdbj.server.basemodel.model.ResponseCoin;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CommentService {
    @Autowired
    protected ICommentMapper commentMapper;
    @Autowired
    protected UserService userService;
    @Autowired
    protected HeartService heartService;
    @Autowired
    protected VideoService videoService;
    @Autowired
    protected MessageCenterService messageCenterService;
    @Autowired
    protected UserAssetServiceImpl userAssetServiceImpl;
    @Autowired
    RedisTemplate redisTemplate;

    public List<CommentInfoDto> list(long resId, int pageNo,int rows) {
        long userId = JWTUtil.getCurrentId();
        List<CommentInfoDto> commentList = null;
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String str = hashOperations.get("videoComments" + resId, String.valueOf(pageNo));
        if (str != null) {
            commentList = JSON.parseObject(str, new TypeReference<List<CommentInfoDto>>() {
            });
            System.out.println("缓存获取评论");
            if (pageNo==1 && userId>0){
                List<CommentInfoDto> b = this.getUserCommentByVideoId(resId,userId);
                if (b != null){
                    for (int i =0;i<b.size();i++){
                        setCommentDtoExtro(b.get(i));
                        commentList.add(i,b.get(i));
                    }

                }
            }
            return commentList;
        }
        System.out.println("数据库获取评论");
        PageHelper.startPage(pageNo, rows);
        commentList = this.commentMapper.list(resId, userId);

        if (commentList!=null)
        hashOperations.put("videoComments" + resId, String.valueOf(pageNo), JSONArray.toJSONString(commentList));
        if (pageNo==1 && userId>0){
            List<CommentInfoDto> c = this.getUserCommentByVideoId(resId,userId);
            if (c != null){
                for (int i =0;i<c.size();i++){
                    commentList.add(i,c.get(i));
                }
            }
        }
        setCommentDtoExtro(commentList);
        return commentList;
    }

    /**
     * 获得此视频中我的评论
     * @param userId
     * @return
     */
    public List<CommentInfoDto> getUserCommentByVideoId(long videoId,long userId){
        if (userId>0) {
            List<CommentInfoDto> b = this.commentMapper.listByUserId(videoId, userId);
            if (b != null) {
                return b;
            }
        }
        return null;
    }

    public List<CommentInfoDto> myAllComments(long userId) {
        List<CommentInfoDto> commentList = this.commentMapper.myAllComments(userId, JWTUtil.getCurrentId());
        if (commentList != null)
            setCommentDtoExtro(commentList);
        return commentList;
    }

    public CommentInfoDto getCommentDto(long id) {
        CommentInfoDto dto = this.commentMapper.getCommentDto(id, JWTUtil.getCurrentId());
        if (dto != null)
            setCommentDtoExtro(dto);
        return dto;
    }

    @Transactional
    public ServiceStatusInfo<Object> heart(HeartInput input) {
        long userId = JWTUtil.getCurrentId();
        if (userId <= 0) return new ServiceStatusInfo<>(1, "请重新登录", null,null);
        HeartModel heartModel = this.heartService.findHeart(userId, input.getId());
        if (heartModel != null && input.isHeart()) {
            return new ServiceStatusInfo<>(1, "已经点赞过", null,null);
        }
        if (heartModel == null && !input.isHeart()) {
            return new ServiceStatusInfo<>(0, "取消成功", null,null);
        }
        if (input.isHeart()) {
            long id = UniqueIDCreater.generateID();
            ServiceStatusInfo<Long> isFirst = this.heartService.heart(id, userId, input.getId(), 1);
            this.commentMapper.addHeart(input.getId(), 1);
            if (isFirst.getCoins() != null) return new ServiceStatusInfo<>(0, "点赞成功", null, isFirst.getCoins());
            return new ServiceStatusInfo<>(0, "点赞成功", null);
        } else {
            this.heartService.unHeart(userId, input.getId());
            this.commentMapper.addHeart(input.getId(), -1);
            return new ServiceStatusInfo<>(0, "取消成功", null,null);
        }
    }

    @Transactional
    public ServiceStatusInfo<EntityKeyModel<Long>> delete(EntityKeyModel<Long> keyDto) {
        long id = this.commentMapper.delete(keyDto.getId());
        if (id > 0) {
            //TODO 注意区分类型
            this.videoService.updateField("commentCount=commentCount-1", keyDto.getId());
            return new ServiceStatusInfo<>(0, "删除成功", keyDto);
        } else {
            return new ServiceStatusInfo<>(1, "删除失败", keyDto);
        }
    }

    @Transactional
    public ServiceStatusInfo<Object> add(AddCommentInput input) {
        AddCommentModel addCommentModel = new ModelMapper().map(input, AddCommentModel.class);
        long userId = JWTUtil.getCurrentId();
        if (userId <= 0) return new ServiceStatusInfo<>(1, "请重新登录", null,null);

        addCommentModel.setId(UniqueIDCreater.generateID());
        addCommentModel.setUserId(userId);
        if (addCommentModel.getContent().trim().length()==0)return new ServiceStatusInfo<>(1, "请填写内容", null,null);
        long resultLine = this.commentMapper.add(addCommentModel);
        if (resultLine > 0) {

            if (redisTemplate.hasKey("videoComments" + input.getResId())) {
                redisTemplate.delete("videoComments" + input.getResId());
                System.out.println("删除评论缓存");
            }

            //TODO 注意区分类型
            this.videoService.updateField("commentCount=commentCount+1", input.getResId());
            VideoDetailInfoDto detailInfoDto = this.videoService.video(input.getResId());
            if (detailInfoDto != null) {
                MessageInput msgInput = new MessageInput();
                msgInput.setCreatorUserId(userId);
                msgInput.setMsgContent(input.getContent());
                msgInput.setMessageType(3);
                msgInput.setDataContent("{\"resId\":\"" + input.getResId() + "\",\"type\":\"0\"}");
                msgInput.setRefUrl(detailInfoDto.getVideoUrl());
                this.messageCenterService.push(msgInput, detailInfoDto.getUserId());
            }
            this.videoService.videoWegiht(input.getResId());
            //每日任务金币
            boolean keyExist = this.redisTemplate.hasKey("user_everydayTask_isFirstPublicComment:" + userId);
            ResponseCoin coins = null;
            if (!keyExist) {
                LocalTime midnight = LocalTime.MIDNIGHT;
                LocalDate today = LocalDate.now(ZoneId.of("Asia/Shanghai"));
                LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
                LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
                long s = TimeUnit.NANOSECONDS.toSeconds(Duration.between(LocalDateTime.now(ZoneId.of("Asia/Shanghai")), tomorrowMidnight).toNanos());
                this.redisTemplate.opsForValue().set("user_everydayTask_isFirstPublicComment:" + userId, userId + ":hasFirstPublicComment", s, TimeUnit.SECONDS);
                this.userAssetServiceImpl.userIsExist(userId);
                UserCoinDetailAddInput userCoinDetailAddInput = new UserCoinDetailAddInput();
                userCoinDetailAddInput.setStatus("SUCCESS");
                userCoinDetailAddInput.setNum(2);
                userCoinDetailAddInput.setTitle("每日首次评论获得小饼干" + 2 + "个");
                userCoinDetailAddInput.setType("TASK");
                this.userAssetServiceImpl.userPlayCoinTask(userCoinDetailAddInput, userId, "TASK", 2,"EVERYDAYFIRSTCOMMENT","DONE");
                coins = new ResponseCoin();
                coins.setCoins(2);
                coins.setMessage("每天首次评论获得小饼干2个");
            }
            return new ServiceStatusInfo<>(0, "发布成功", null,coins);
        } else {
            return new ServiceStatusInfo<>(1, "发布失败", null,null);
        }
    }

    private void setCommentDtoExtro(CommentInfoDto dto) {
        //TODO 优化性能,防止循环引用
        if (dto == null) return;
        if (dto.getRefCommentId() > 0) {
            CommentInfoDto refCmt = this.commentMapper.getCommentDto(dto.getRefCommentId(), JWTUtil.getCurrentId());
            if (refCmt != null) {
                dto.setRefComment(refCmt);
            }
        }
    }

    private void setCommentDtoExtro(List<CommentInfoDto> dtos) {
        //TODO 优化性能,防止循环引用
        if (dtos == null) return;
        for (CommentInfoDto dto : dtos) {
            setCommentDtoExtro(dto);
        }
    }

    public Long deleteVideoComments(Long id) {
        return this.commentMapper.deleteVideoComments(id);
    }

    /**
     * 查看是否为当天首次评论
     */
    public boolean isFirstPublicComment(long userId) {

        String key = "user_everydayTask_isFirstPublicComment:" + userId;
        ConsulClient consulClient = new ConsulClient("localhost", 8500);    // 创建与Consul的连接
        Lock lock = new Lock(consulClient, "mobileapi", key);
        try {
            if (lock.lock(true, 500L, 1)) {
                int result = this.commentMapper.isFirstPublicComment(userId);
                return result == 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return false;
    }

    public long findCommentNumById(long resId){
        return this.commentMapper.findCommentNumById(resId);
    }

    /**
     * 根据视频id查询评论数据(ES)
     * @param videoId
     * @return
     */
    public CommentInfoDto findVideoIdES(long videoId){
        return this.commentMapper.findVideoIdES(videoId);
    }

}
