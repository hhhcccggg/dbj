package com.zwdbj.server.mobileapi.service.shop.comments.service;

import com.zwdbj.server.mobileapi.service.shop.comments.model.CommentInput;
import com.zwdbj.server.mobileapi.service.shop.comments.model.CommentVideoInfo;
import com.zwdbj.server.mobileapi.service.shop.comments.model.ShopCommentsExtraDatas;
import com.zwdbj.server.mobileapi.service.shop.comments.model.UserComments;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface ShopCommentService {
    ServiceStatusInfo<UserComments> userComments(long storeId);

    ServiceStatusInfo<List<ShopCommentsExtraDatas>> commentList(long storeId);

    ServiceStatusInfo<Long> publishServiceComment(CommentVideoInfo videoInput);

    ServiceStatusInfo<Long> publishProductComment(CommentInput commentInput);
}
