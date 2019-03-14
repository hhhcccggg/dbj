package com.zwdbj.server.adminserver.service.shop.service.customerComments.service;

import com.zwdbj.server.adminserver.service.shop.service.customerComments.mapper.CustomerCommentMapper;
import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.*;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerCommentServiceImpl implements CustomerCommentService {

    @Autowired
    private CustomerCommentMapper customerCommentMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ServiceStatusInfo<List<CommentInfo>> commentList(long legalSubjectId, SearchInfo searchInfo) {
        List<CommentInfo> result = null;
        try {
            result = this.customerCommentMapper.commentList(legalSubjectId,searchInfo);
            for (CommentInfo c : result) {
                if (this.stringRedisTemplate.hasKey("replyComment"+c.getId())){
                    c.setRefCommentOrNot(true);
                    long id = Long.valueOf(this.stringRedisTemplate.opsForValue().get("replyComment"+c.getId()));
                    c.setRefComment(this.customerCommentMapper.commentReply(id));
                }else {
                    c.setRefCommentOrNot(false);
                }
                //点了没有回复  移除回复      没有回复
                if(searchInfo.getIsReply()==0&&c.isRefCommentOrNot()){
                    result.remove(c);
                }
                //点了回复    移除没有回复
                if(searchInfo.getIsReply()==1&&!c.isRefCommentOrNot()){
                    result.remove(c);
                }
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "拉取用户评论失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> replyComment(ReplyComment replyComment) {
        try {
            long id = UniqueIDCreater.generateID();
            long result = this.customerCommentMapper.replyComment(id, replyComment);

            if (result==0)new ServiceStatusInfo<>(1, "回复评论失败", null);
            this.stringRedisTemplate.opsForValue().set("replyComment"+replyComment.getRefCommentId(),String.valueOf(id));
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "回复评论失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteComment(long commentId) {
        try {

            long result = this.customerCommentMapper.deleteComment(commentId);

            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除评论失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<UserComments> countComments(long legalSubjectId) {
        try {
            UserComments result = this.customerCommentMapper.countComments(legalSubjectId);

            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "统计用户评价失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<CommentInfo>> commentRankList(long legalSubjectId, float rate) {
        try {
            List<CommentInfo> result = this.customerCommentMapper.commentRankList(legalSubjectId, rate);

            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "分级拉取用户评论失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo< List<CommentRank>> commentRank(long legalSubjectId) {
        try {
            List<CommentRank> result = this.customerCommentMapper.commentRank(legalSubjectId);

            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "分级统计用户评论失败" + e.getMessage(), null);
        }
    }
}