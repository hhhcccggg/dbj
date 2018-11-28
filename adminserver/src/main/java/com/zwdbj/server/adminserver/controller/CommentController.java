package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.utility.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.comment.model.AddCommentInput;
import com.zwdbj.server.adminserver.service.comment.model.CommentInfoDto;
import com.zwdbj.server.adminserver.service.comment.service.CommentService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@Api(description = "评论相关")
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/list/{resId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取资源(视频)的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",name = "resId",value = "资源的Id")
    })
    public ResponsePageInfoData<List<CommentInfoDto>> list(@PathVariable long resId, @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                           @RequestParam(value = "rows",required = true,defaultValue = "30") int rows) {
        Page<CommentInfoDto> pageInfo = PageHelper.startPage(pageNo, rows);
        List<CommentInfoDto> comments = commentService.list(resId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",comments,pageInfo.getTotal());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取某个评论详情")
    public ResponseData<CommentInfoDto> list(@PathVariable long id) {
        CommentInfoDto dto = this.commentService.getCommentDto(id);
        if (dto==null) {
            return new ResponseData<>(ResponseDataCode.STATUS_NOT_FOUND,"评论不存在",null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",dto);
    }


    @RequiresAuthentication
    @RequestMapping(value = "/dbj/screeningComment/{id}",method = RequestMethod.GET)
    @ApiOperation("屏蔽评论")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Long> screeningComment(@PathVariable Long id){
        ServiceStatusInfo<Long> statusInfo = this.commentService.screeningComment(id);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"屏蔽评论成功",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"屏蔽评论失败"+statusInfo.getMsg(),null);

    }

}
