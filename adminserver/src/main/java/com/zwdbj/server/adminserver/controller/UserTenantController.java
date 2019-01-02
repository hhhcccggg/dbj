package com.zwdbj.server.adminserver.controller;

import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantInput;
import com.zwdbj.server.adminserver.service.userTenant.service.UserTenantService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tenant")
@Api(description = "商户相关")
public class UserTenantController {
    @Autowired
    UserTenantService userTenantService;

    @RequiresAuthentication
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "设置用户推送设置")
    public ResponseData<Integer> addUserTenant(@RequestBody @Valid UserTenantInput input) {
        ServiceStatusInfo<Integer>statusInfo = this.userTenantService.addUserTenant(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "租户添加成功", 1);
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }
}
