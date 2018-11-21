package com.zwdbj.server.adminserver.service.comment.service;

import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.comment.mapper.ICommentMapper;
import com.zwdbj.server.adminserver.service.comment.model.*;
import com.zwdbj.server.adminserver.service.heart.service.HeartService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.video.service.VideoService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
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

    public List<CommentInfoDto> list(long resId) {
        List<CommentInfoDto> commentList = this.commentMapper.list(resId,JWTUtil.getCurrentId());
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

    public int greatComment(Long id,Long userId,String contentTxt,Long resourceOwnerId){
        return this.commentMapper.greatComment(id,userId,contentTxt,resourceOwnerId);
    }


}
