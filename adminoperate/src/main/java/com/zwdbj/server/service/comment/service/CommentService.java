package com.zwdbj.server.service.comment.service;

import com.zwdbj.server.service.comment.mapper.ICommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    ICommentMapper commentMapper;

    public int greatComment(Long id,Long userId,String contentTxt,Long resourceOwnerId){
        return this.commentMapper.greatComment(id,userId,contentTxt,resourceOwnerId);
    }

}
