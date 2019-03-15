package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ResponsePageInfoData;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.complain.model.*;
import com.zwdbj.server.adminserver.service.complain.service.ComplainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complain")
@Api(description = "举报相关基本信息",value = "basicComplain")
public class ComplainController {
    @Autowired
    protected ComplainService complainService;

    //admin
    @RequiresAuthentication
    @RequestMapping(value = "/dbj/basicComplain/{resTypeId}",method = RequestMethod.POST)
    @ApiOperation("基础信息-(用户/视频/直播)举报选项:被举报的资源类型0：用户,1：视频,2：直播")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponsePageInfoData<List<AdComplainListDto>>
    basicCompalinAd(@RequestBody AdFindComplainInput input,
                    @PathVariable(value = "resTypeId",required = true) int resTypeId, //0：用户,1：视频,2：直播
                    @RequestParam(value = "pageNo",required = true,defaultValue = "1") int pageNo,
                    @RequestParam(value = "rows",required = true,defaultValue = "13") int rows) {
        Page<AdComplainListDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<AdComplainListDto> complainListDtos = complainService.basicCompalinAd(input,resTypeId);
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",complainListDtos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/basicComplain/{type}/add",method = RequestMethod.POST)
    @ApiOperation("基础信息-新建(用户/视频/直播)举报选项:被举报的资源类型0：用户,1：视频,2：直播")
    @RequiresRoles(value = {RoleIdentity.ADMIN_ROLE, RoleIdentity.MARKET_ROLE}, logical = Logical.OR)
    public ResponseData<Long> addComplainAd(@RequestBody AdNewComplainInput input,
                                            @PathVariable(value = "type",required = true)int type){
        ServiceStatusInfo<Long> statusInfo = this.complainService.addComplainAd(input,type);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);

    }
}
