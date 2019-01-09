package com.zwdbj.server.adminserver.service.shop.service.customerComments.service;

import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.CommentInfo;
import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.CommentRank;
import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.ReplyComment;
import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.UserComments;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface CustomerCommentService {
    ServiceStatusInfo<List<CommentInfo>> commentList(long legalSubjectId);

    ServiceStatusInfo<Long> replyComment(ReplyComment replyComment);

    ServiceStatusInfo<Long> deleteComment(long commentId);

    ServiceStatusInfo<UserComments> countComments(long legalSubjectId);

    ServiceStatusInfo<List<CommentInfo>> commentRankList(long legalSubjectId,float rate);

    ServiceStatusInfo< List<CommentRank>> commentRank(long legalSubjectId);
}
