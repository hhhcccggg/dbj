package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.appVersion.model.AdAppVersionInput;
import com.zwdbj.server.adminserver.service.appVersion.model.AppVersionDto;
import com.zwdbj.server.adminserver.service.appVersion.service.AppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/appVersion")
@Api(description = "APP版本更新")
public class AppVersionController {
    @Autowired
    AppVersionService appVersionService;


    @RequiresAuthentication
    @RequestMapping(value = "/dbj/insertNew",method = RequestMethod.POST)
    @ApiOperation("增加版本")
    @RequiresRoles(value = RoleIdentity.ADMIN_ROLE)
    public ResponseData<Long> newAppVersion(@RequestBody AdAppVersionInput input){
        ServiceStatusInfo<Long> statusInfo= this.appVersionService.newAppVersion(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/deleteV/{id}",method = RequestMethod.GET)
    @ApiOperation("删除版本")
    @RequiresRoles(value = RoleIdentity.ADMIN_ROLE)
    public ResponseData<Long> delAppVersion(@PathVariable Long id){
        ServiceStatusInfo<Long> statusInfo= this.appVersionService.delAppVersion(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/appVersionList",method = RequestMethod.GET)
    @ApiOperation("获取版本列表")
    @RequiresRoles(value = RoleIdentity.ADMIN_ROLE)
    public ResponsePageInfoData<List<AppVersionDto>> searchVersionList(@RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                                                                       @RequestParam(value = "rows",defaultValue = "13",required = true) int rows){
        Page<AppVersionDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<AppVersionDto> dtos = this.appVersionService.searchVersionList();
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/updateVersion/{id}",method = RequestMethod.POST)
    @ApiOperation("修改版本信息")
    @RequiresRoles(value = RoleIdentity.ADMIN_ROLE)
    public ResponseData<Long> updateVersion(@PathVariable Long id,@RequestBody AdAppVersionInput input){
        ServiceStatusInfo<Long> statusInfo= this.appVersionService.updateVersion(id,input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

}
