package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.userDeviceTokens.model.UserDeviceTokensInput;
import com.zwdbj.server.mobileapi.service.userDeviceTokens.service.UserDeviceTokensService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userDevice")
@Api(description = "用户设备推送相关",value = "UserDeviceTokens")
public class UserDeviceTokensController {
    @Autowired
    private UserDeviceTokensService userDeviceTokensService;

    @RequiresAuthentication
    @RequestMapping(value = "/bindingUser",method = RequestMethod.POST)
    @ApiOperation(value = "绑定/解绑设备的token和userId,若userId=0时则代表删除对应的记录")
    public ResponseData<Object> bindingUserId(@RequestBody UserDeviceTokensInput input){
        ServiceStatusInfo<Object> statusInfo = this.userDeviceTokensService.bindingUserId(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }
}
