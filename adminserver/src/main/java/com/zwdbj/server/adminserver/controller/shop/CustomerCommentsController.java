package com.zwdbj.server.adminserver.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.service.shop.service.customerComments.model.*;
import com.zwdbj.server.adminserver.service.shop.service.customerComments.service.CustomerCommentService;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/storeComments/dbj")
@RestController
@Api(description = "店铺评价相关")
public class CustomerCommentsController {
    @Autowired
    private CustomerCommentService customerCommentServiceImpl;
    @Autowired
    private TokenCenterManager tokenCenterManager;

    @RequiresAuthentication
    @RequestMapping(value = "/commentList", method = RequestMethod.POST)
    @ApiOperation(value = "获取评论列表")
    public ResponsePageInfoData<List<CommentInfo>> commentList(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                               @RequestParam(value = "rows", required = true, defaultValue = "10") int rows,
                                                               @RequestBody SearchInfo searchInfo) {

        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        Page<CommentInfo> page = PageHelper.startPage(pageNo, rows);
        List<CommentInfo> list = customerCommentServiceImpl.commentList(legalSubjectId,searchInfo).getData();
        return new ResponsePageInfoData<>(0, "", list, page.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/commenRanktList/{rate}", method = RequestMethod.GET)
    @ApiOperation(value = "分级获取评论列表")
    public ResponsePageInfoData<List<CommentInfo>> commentRankList(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                   @RequestParam(value = "rows", required = true, defaultValue = "10") int rows,
                                                                   @PathVariable("rate") float rate) {

        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        Page<CommentInfo> page = PageHelper.startPage(pageNo, rows);
        List<CommentInfo> list = customerCommentServiceImpl.commentRankList(legalSubjectId, rate).getData();
        return new ResponsePageInfoData<>(0, "", list, page.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/replyComment", method = RequestMethod.POST)
    @ApiOperation(value = "回复评论")
    public ResponseData<Long> replyComment(@RequestBody ReplyComment replyComment) {
        ServiceStatusInfo<Long> statusInfo = customerCommentServiceImpl.replyComment(replyComment);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/deleteComment/{commentId}", method = RequestMethod.GET)
    @ApiOperation(value = "删除评论")
    public ResponseData<Long> deleteComment(@PathVariable("commentId") long commentId) {
        ServiceStatusInfo<Long> statusInfo = customerCommentServiceImpl.deleteComment(commentId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/countComments", method = RequestMethod.GET)
    @ApiOperation(value = "统计评论")
    public ResponseData<UserComments> countComments() {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<UserComments> statusInfo = customerCommentServiceImpl.countComments(legalSubjectId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }

    @RequiresAuthentication
    @RequestMapping(value = "/countCommentsRank", method = RequestMethod.GET)
    @ApiOperation(value = "分级统计评论")
    public ResponseData<List<CommentRank>> commentRank() {
        long userId = JWTUtil.getCurrentId();
        long legalSubjectId = tokenCenterManager.fetchUser(String.valueOf(userId)).getData().getLegalSubjectId();
        ServiceStatusInfo<List<CommentRank>> statusInfo = customerCommentServiceImpl.commentRank(legalSubjectId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }


}