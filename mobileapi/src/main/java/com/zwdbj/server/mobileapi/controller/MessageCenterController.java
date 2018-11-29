package com.zwdbj.server.mobileapi.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageInfoDto;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageUnReadDto;
import com.zwdbj.server.mobileapi.service.messageCenter.service.MessageCenterService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messageCenter")
@Api(description = "消息相关")
public class MessageCenterController {
    @Autowired
    MessageCenterService messageCenterService;

    @RequiresAuthentication
    @RequestMapping(value = "/unread",method = RequestMethod.GET)
    @ApiOperation("获取消息未读情况")
    public ResponseData<MessageUnReadDto> unread() {
        long userId = JWTUtil.getCurrentId();
        MessageUnReadDto readDto = this.messageCenterService.unRead(userId);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",readDto);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/readAll/{type}",method = RequestMethod.GET)
    @ApiOperation("消息已读")
    @ApiImplicitParam(name = "type",value = "0:系统消息,1:点赞类2:粉丝类3:评论")
    public ResponseData<Object> readAll(@PathVariable int type) {
        long userId = JWTUtil.getCurrentId();
        this.messageCenterService.readAll(userId,type);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/sysmsg",method = RequestMethod.GET)
    @ApiOperation(value = "获取系统消息")
    public ResponsePageInfoData<List<MessageInfoDto>> systemMessages(@RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                                                                     @RequestParam(value = "rows",required = true,defaultValue = "30") int rows) {
        Page<MessageInfoDto> pageainfo = PageHelper.startPage(pageNo,rows);
        List<MessageInfoDto> dtos = this.messageCenterService.systemMessage(JWTUtil.getCurrentId());
        this.messageCenterService.readAll(JWTUtil.getCurrentId(),0);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageainfo.getTotal());
    }

}
