package com.zwdbj.server.adminserver.service.shop.service.customerComments.service;

import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.*;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface CustomerCommentService {
    ServiceStatusInfo<List<CommentInfo>> commentList(long legalSubjectId, SearchInfo searchInfo);

    ServiceStatusInfo<Long> replyComment(ReplyComment replyComment);

    ServiceStatusInfo<Long> deleteComment(long commentId);

    ServiceStatusInfo<UserComments> countComments(long legalSubjectId);

    ServiceStatusInfo<List<CommentInfo>> commentRankList(long legalSubjectId,float rate);

    ServiceStatusInfo< List<CommentRank>> commentRank(long legalSubjectId);
}
