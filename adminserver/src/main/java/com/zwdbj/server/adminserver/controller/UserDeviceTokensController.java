package com.zwdbj.server.adminserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.model.ResponseData;
import com.zwdbj.server.adminserver.model.ResponseDataCode;
import com.zwdbj.server.adminserver.model.ResponsePageInfoData;
import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdDeviceTokenDto;
import com.zwdbj.server.adminserver.service.userDeviceTokens.model.AdUserDeviceTokensInput;
import com.zwdbj.server.adminserver.service.userDeviceTokens.service.UserDeviceTokensService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userDevice")
@Api(description = "用户设备推送相关",value = "UserDeviceTokens")
public class UserDeviceTokensController {
    @Autowired
    private UserDeviceTokensService userDeviceTokensService;

    @RequiresAuthentication
    @RequestMapping(value = "/bindingUser",method = RequestMethod.POST)
    @ApiOperation(value = "绑定/解绑设备的token和userId,若userId=0时则代表删除对应的记录")
    public ResponseData<Object> bindingUserId(@RequestBody AdUserDeviceTokensInput input){
        ServiceStatusInfo<Object> statusInfo = this.userDeviceTokensService.bindingUserId(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/tokenList",method = RequestMethod.GET)
    @ApiOperation(value = "获取token的列表")
    @RequiresRoles(RoleIdentity.ADMIN_ROLE)
    public ResponsePageInfoData<List<AdDeviceTokenDto>>
        getTokenList(@RequestParam(value = "pageNo",defaultValue = "1",required = true) int pageNo,
                     @RequestParam(value = "rows",defaultValue = "13",required = true) int rows){
        Page<AdDeviceTokenDto> pageInfo = PageHelper.startPage(pageNo,rows);
        List<AdDeviceTokenDto> dtos = this.userDeviceTokensService.getTokenList();
        return new ResponsePageInfoData<>(ResponseDataCode.STATUS_NORMAL,"",dtos,pageInfo.getTotal());
    }

    @RequiresAuthentication
    @RequestMapping(value = "/dbj/delToken/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除内容")
    @RequiresRoles(RoleIdentity.ADMIN_ROLE)
    public ResponseData<Long> delTokenById(@PathVariable Long id){
        ServiceStatusInfo<Long> statusInfo = this.userDeviceTokensService.delTokenById(id);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"删除成功",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }

}
