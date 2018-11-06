package com.zwdbj.server.mobileapi.service.comment.service;

import com.zwdbj.server.mobileapi.model.EntityKeyModel;
import com.zwdbj.server.mobileapi.model.HeartInput;
import com.zwdbj.server.mobileapi.service.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.comment.mapper.ICommentMapper;
import com.zwdbj.server.mobileapi.service.comment.model.AddCommentInput;
import com.zwdbj.server.mobileapi.service.comment.model.AddCommentModel;
import com.zwdbj.server.mobileapi.service.comment.model.CommentInfoDto;
import com.zwdbj.server.mobileapi.service.heart.model.HeartModel;
import com.zwdbj.server.mobileapi.service.heart.service.HeartService;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageInput;
import com.zwdbj.server.mobileapi.service.messageCenter.service.MessageCenterService;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.service.video.model.VideoDetailInfoDto;
import com.zwdbj.server.mobileapi.service.video.service.VideoService;
import com.zwdbj.server.mobileapi.shiro.JWTUtil;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            this.videoService.videoWegiht(input.getResId());
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

    public Long deleteVideoComments(Long id){
        return this.commentMapper.deleteVideoComments(id);
    }


}
