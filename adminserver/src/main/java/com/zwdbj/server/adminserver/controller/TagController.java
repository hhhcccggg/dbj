package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.model.ResourceOpenInput;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.tag.model.*;
import com.zwdbj.server.adminserver.service.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
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
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE,RoleIdentity.DATA_REPORT_ROLE},logical = Logical.OR)
    public  ResponsePageInfoData<List<AdVideoTagDto>> getVideoTagAd(@RequestBody AdVideoTagInput input,
                                                                    @RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                                                                    @RequestParam(value = "rows",defaultValue = "13",required = true) int rows){
        Page<AdVideoTagDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<AdVideoTagDto> videoTagDtos = this.tagService.getVideoTagAd(input);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",videoTagDtos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/videoTag/change/{id}",method = RequestMethod.POST)
    @ApiOperation("基础信息-视频标签")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public  ResponseData<Integer> changeTagStatus(@PathVariable long id,
                                                          @RequestBody UpdateTagStatusInput input){
        ServiceStatusInfo<Integer> statusInfo = this.tagService.changeTagStatus(id,input);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/videoTag/add",method = RequestMethod.POST)
    @ApiOperation("基础信息-新建视频标签")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Long> addVideoTagAd(@RequestBody AdNewVideoTagInput input){
        ServiceStatusInfo<Long> statusInfo = this.tagService.addVideoTagAd(input);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/add/hotTag",method = RequestMethod.POST)
    @ApiOperation("添加热门标签")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Object> addHotTag(@RequestBody ResourceOpenInput<Long> input){
        ServiceStatusInfo<Object> statusInfo = this.tagService.addHotTag(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",null);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"",null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/add/todayTag/{id}",method = RequestMethod.POST)
    @ApiOperation("添加今日主题标签")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponseData<Object> addTodayTag(@PathVariable long id,
                                            @RequestParam String date){
        ServiceStatusInfo<Object> statusInfo = this.tagService.addTodayTag(id,date);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,"",null);
    }
    @RequiresAuthentication
    @RequestMapping(value = "/search/todayTags",method = RequestMethod.POST)
    @ApiOperation("根据年月查看今日主题标签")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE,RoleIdentity.MARKET_ROLE},logical = Logical.OR)
    public ResponsePageInfoData<List<TodayTagsDto>> getTagsByYearAndMonth(@RequestParam String yearAndMonth,
                                                                          @RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                                                                          @RequestParam(value = "rows",defaultValue = "15",required = true) int rows){
        Page<TodayTagsDto> pageInfo = PageHelper.startPage(pageNo,rows);
        ServiceStatusInfo<List<TodayTagsDto>> statusInfo = this.tagService.getTagsByYearAndMonth(yearAndMonth);
        if (statusInfo.isSuccess()) {
            return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData(),pageInfo.getTotal());
        }
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_ERROR,"",null,0);
    }

}
