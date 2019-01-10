package com.zwdbj.server.mobileapi.service.shop.comments.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.zwdbj.server.mobileapi.service.qiniu.service.QiniuService;
import com.zwdbj.server.mobileapi.service.shop.comments.mapper.ShopCommentsMapper;
import com.zwdbj.server.mobileapi.service.shop.comments.model.CommentInput;
import com.zwdbj.server.mobileapi.service.shop.comments.model.CommentVideoInfo;
import com.zwdbj.server.mobileapi.service.shop.comments.model.ShopCommentsExtraDatas;
import com.zwdbj.server.mobileapi.service.shop.comments.model.UserComments;
import com.zwdbj.server.mobileapi.service.video.service.VideoService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopCommentServiceImpl implements ShopCommentService {

    @Autowired
    private ShopCommentsMapper shopCommentsMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private QiniuService qiniuService;
    @Autowired
    private VideoService videoService;

    @Override
    public ServiceStatusInfo<UserComments> userComments(long storeId) {
        UserComments result = null;
        try {
            result = this.shopCommentsMapper.CountComments(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询用户评价失败", null);
        }
    }

    @Override
    public ServiceStatusInfo<List<ShopCommentsExtraDatas>> commentList(long storeId) {
        List<ShopCommentsExtraDatas> result = null;
        try {
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
            if (valueOperations.get("commentList" + storeId) != null) {
                System.out.println("从缓存中拉取评论列表");
                String str = valueOperations.get("commentList" + storeId);
                result = JSON.parseObject(str, new TypeReference<List<ShopCommentsExtraDatas>>() {
                });
                return new ServiceStatusInfo<>(0, "", result);
            }
            result = this.shopCommentsMapper.commentList(storeId);
            valueOperations.set("commentList" + storeId, JSONArray.toJSONString(result));
            System.out.println("从数据库中拉取评论列表");
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "拉取评论失败" + e.getMessage(), null);
        }
    }

    //发布商家服务评论
    @Override
    @Transactional
    public ServiceStatusInfo<Long> publishServiceComment(CommentVideoInfo videoInput) {
        try {
            long result = 0L;
            long userId = JWTUtil.getCurrentId();
            long commentId = UniqueIDCreater.generateID();
            String videoUrl = qiniuService.url(videoInput.getDataContent());
            videoInput.setDataContent(videoUrl);
            videoInput.setVideoUrl(videoUrl);
            result += this.shopCommentsMapper.publishComment(commentId, userId, videoInput);
            long videoId = videoService.publicCommentVideo(videoInput);
            result++;
            result += this.shopCommentsMapper.commentExtraDatas(UniqueIDCreater.generateID(), commentId, videoId, videoInput);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "发布商家服务评价失败" + e.getMessage(), null);
        }

    }

    //发布商家产品评论
    @Transactional
    public ServiceStatusInfo<Long> publishProductComment(CommentInput commentInput) {
        try {
            long result = 0L;
            long userId = JWTUtil.getCurrentId();
            long commentId = UniqueIDCreater.generateID();
            commentInput.setDataContent(qiniuService.url(commentInput.getDataContent()));
            result += this.shopCommentsMapper.publishComment(commentId, userId, commentInput);
            result += this.shopCommentsMapper.commentExtraDatas(UniqueIDCreater.generateID(), commentId, 0, commentInput);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "发布商家产品评价失败" + e.getMessage(), null);
        }
    }
}