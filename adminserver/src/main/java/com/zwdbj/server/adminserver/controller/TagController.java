package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.tag.model.*;
import com.zwdbj.server.adminserver.service.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "标签")
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;


    @RequiresAuthentication
    @RequestMapping(value = "/dbj/videoTag",method = RequestMethod.POST)
    @ApiOperation("基础信息-视频标签")
    @RequiresRoles(RoleIdentity.ADMIN_ROLE)
    public  ResponsePageInfoData<List<AdVideoTagDto>> getVideoTagAd(@RequestBody AdVideoTagInput input,
                                                                    @RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                                                                    @RequestParam(value = "rows",defaultValue = "13",required = true) int rows){
        Page<AdVideoTagDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<AdVideoTagDto> videoTagDtos = this.tagService.getVideoTagAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videoTagDtos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/videoTag/add",method = RequestMethod.POST)
    @ApiOperation("基础信息-新建视频标签")
    @RequiresRoles(RoleIdentity.ADMIN_ROLE)
    public ResponseData<Long> addVideoTagAd(@RequestBody AdNewVideoTagInput input){
        ServiceStatusInfo<Long> statusInfo = this.tagService.addVideoTagAd(input);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }
}
