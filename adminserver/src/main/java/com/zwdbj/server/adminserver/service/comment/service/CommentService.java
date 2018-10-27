package com.zwdbj.server.adminserver.service.comment.service;

import com.zwdbj.server.adminserver.model.EntityKeyModel;
import com.zwdbj.server.adminserver.model.HeartInput;
import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.comment.mapper.ICommentMapper;
import com.zwdbj.server.adminserver.service.comment.model.*;
import com.zwdbj.server.adminserver.service.heart.model.HeartModel;
import com.zwdbj.server.adminserver.service.heart.service.HeartService;
import com.zwdbj.server.adminserver.service.messageCenter.model.MessageInput;
import com.zwdbj.server.adminserver.service.messageCenter.service.MessageCenterService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.model.VideoDetailInfoDto;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.adminserver.shiro.JWTUtil;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public List<CommentInfoDto> list(long resId) {
        List<CommentInfoDto> commentList = this.commentMapper.list(resId,JWTUtil.getCurrentId());
        if (commentList!=null)
        setCommentDtoExtro(commentList);
        return commentList;
    }

    public List<CommentInfoDto> myAllComments(long userId) {
        List<CommentInfoDto> commentList = this.commentMapper.myAllComments(userId,JWTUtil.getCurrentId());
        if (commentList!=null)
        setCommentDtoExtro(commentList);
        return commentList;
    }

    public CommentInfoDto getCommentDto(long id) {
        CommentInfoDto dto = this.commentMapper.getCommentDto(id,JWTUtil.getCurrentId());
        if (dto!=null)
        setCommentDtoExtro(dto);
        return dto;
    }

    @Transactional
    public ServiceStatusInfo<Object> heart(HeartInput input) {
        long userId = JWTUtil.getCurrentId();
        if (userId <=0) return new ServiceStatusInfo<>(1,"请重新登录",null);
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
            this.heartService.heart(id,userId,input.getId(),1);
            this.commentMapper.addHeart(input.getId(),1);
            return new ServiceStatusInfo<>(0,"点赞成功",null);
        } else {
            this.heartService.unHeart(userId,input.getId());
            this.commentMapper.addHeart(input.getId(),-1);
            return new ServiceStatusInfo<>(0,"取消成功",null);
        }
    }
    @Transactional
    public ServiceStatusInfo<EntityKeyModel<Long>> delete(EntityKeyModel<Long> keyDto) {
        long id = this.commentMapper.delete(keyDto.getId());
        if (id>0) {
            //TODO 注意区分类型
            this.videoService.updateField("commentCount=commentCount-1",keyDto.getId());
            return new ServiceStatusInfo<>(0,"删除成功",keyDto);
        } else {
            return new ServiceStatusInfo<>(1,"删除失败",keyDto);
        }
    }

    @Transactional
    public ServiceStatusInfo<Object> add(AddCommentInput input) {
        AddCommentModel addCommentModel = new ModelMapper().map(input,AddCommentModel.class);
        long userId = JWTUtil.getCurrentId();
        if (userId <=0) return new ServiceStatusInfo<>(1,"请重新登录",null);
        addCommentModel.setId(UniqueIDCreater.generateID());
        addCommentModel.setUserId(userId);
        long resultLine = this.commentMapper.add(addCommentModel);
        if (resultLine>0) {
            //TODO 注意区分类型
            this.videoService.updateField("commentCount=commentCount+1",input.getResId());
            VideoDetailInfoDto detailInfoDto = this.videoService.video(input.getResId());
            if (detailInfoDto!=null) {
                MessageInput msgInput = new MessageInput();
                msgInput.setCreatorUserId(userId);
                msgInput.setMessageType(3);
                msgInput.setDataContent("{\"resId\":\""+input.getResId()+"\",\"type\":\"0\"}");
                this.messageCenterService.push(msgInput,detailInfoDto.getUserId());
            }
            return new ServiceStatusInfo<>(0,"发布成功",null);
        } else {
            return new ServiceStatusInfo<>(1,"发布失败",null);
        }
    }

    private void setCommentDtoExtro(CommentInfoDto dto) {
        //TODO 优化性能,防止循环引用
        if (dto==null) return;
        if (dto.getRefCommentId()>0) {
            CommentInfoDto refCmt = this.commentMapper.getCommentDto(dto.getRefCommentId(),JWTUtil.getCurrentId());
            if (refCmt!=null) {
                dto.setRefComment(refCmt);
            }
        }
    }

    private void setCommentDtoExtro(List<CommentInfoDto> dtos) {
        //TODO 优化性能,防止循环引用
        if (dtos==null) return;
        for (CommentInfoDto dto:dtos) {
            setCommentDtoExtro(dto);
        }
    }


    @Transactional
    public ServiceStatusInfo<Long> screeningComment(Long id){
        Long result = 0L;
        try {
            result = this.commentMapper.screeningComment(id);
            return new ServiceStatusInfo<>(0,"屏蔽评论成功---service",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"屏蔽评论失败"+e.getMessage(),result);
        }
    }

    public List<Map<String, Object>> findComments(){
        List<CommentReviewDto>  comments = this.commentMapper.findCommentReviewing();
        List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
        if (comments!=null){
            for (CommentReviewDto comment:comments){
                Map<String, Object> task = new LinkedHashMap<String, Object>();
                task.put("dataId", comment.getId().toString());
                task.put("content", comment.getContentTxt());
                tasks.add(task);
                if (tasks.size()==100) break;
            }
            return tasks;
        }else {
            return null;
        }

    }

    @Transactional
    public void updateComment(Long id,String reviewStatus){
        if ("block".equals(reviewStatus) || "review".equals(reviewStatus)){
            this.commentMapper.blockComment(id,reviewStatus);
        }else {
            this.commentMapper.passComment(id);
        }

    }


}
