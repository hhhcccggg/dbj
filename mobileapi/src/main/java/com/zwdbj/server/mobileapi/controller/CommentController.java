package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.comment.model.AddCommentInput;
import com.zwdbj.server.mobileapi.service.comment.model.CommentInfoDto;
import com.zwdbj.server.mobileapi.service.comment.service.CommentService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@Api(description = "评论相关")
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/list/{resId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取资源(视频)的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "resId", value = "资源的Id")
    })
    public ResponsePageInfoData<List<CommentInfoDto>> list(@PathVariable long resId, @RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                           @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<CommentInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<CommentInfoDto> comments = commentService.list(resId, pageNo);
        //过滤违规评论
        for (CommentInfoDto dto : comments) {
            if (dto.getReviewStatus() != null && (dto.getReviewStatus().equals("reviewing") || dto.getReviewStatus().equals("review")) && dto.getUserId() != JWTUtil.getCurrentId()) {
                dto.setContentTxt("评论审核中...");
            }
        }
        Long commentNum = this.commentService.findCommentNumById(resId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", comments, commentNum);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取某个评论详情")
    public ResponseData<CommentInfoDto> list(@PathVariable long id) {
        CommentInfoDto dto = this.commentService.getCommentDto(id);
        if (dto == null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND, "评论不存在", null);
        }
        if (!dto.getReviewStatus().equals("pass")) {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, "评论存在违规，不可显示", null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", dto);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/myAllComments", method = RequestMethod.GET)
    @ApiOperation(value = "获取我的所有评论")
    public ResponsePageInfoData<List<CommentInfoDto>> myAllComments(@RequestParam(value = "pageNo", required = true, defaultValue = "1") int pageNo,
                                                                    @RequestParam(value = "rows", required = true, defaultValue = "30") int rows) {
        Page<CommentInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<CommentInfoDto> comments = commentService.myAllComments(JWTUtil.getCurrentId());
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL, "", comments, pageInfo.getTotal());
    }

    @RequestMapping(value = "/heart", method = RequestMethod.POST)
    @ApiOperation(value = "评论点赞")
    @RequiresAuthentication
    public ResponseData<Object> heart(@RequestBody HeartInput input) {
        ServiceStatusInfo<Object> statusInfo = this.commentService.heart(input);
        if (statusInfo.isSuccess()) {
            if (statusInfo.getCoins() != null)
                return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), null, statusInfo.getCoins());
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除评论")
    @RequiresAuthentication
    public ResponseData<EntityKeyModel<Long>> delete(@RequestBody EntityKeyModel<Long> input) {
        ServiceStatusInfo<EntityKeyModel<Long>> statusInfo = this.commentService.delete(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), input);
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), input);
        }
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ApiOperation(value = "发布评论")
    @RequiresAuthentication
    public ResponseData<Object> publish(@RequestBody AddCommentInput input) {
        ServiceStatusInfo<Object> statusInfo = this.commentService.add(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, statusInfo.getMsg(), null,statusInfo.getCoins());
        } else {
            return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
        }
    }
}
