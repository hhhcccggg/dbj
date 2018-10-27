package com.zwdbj.server.adminserver.controller;


import com.zwdbj.server.adminserver.identity.RoleIdentity;
import com.zwdbj.server.adminserver.model.LoginType;
import com.zwdbj.server.adminserver.model.ResponseData;
import com.zwdbj.server.adminserver.model.ResponseDataCode;
import com.zwdbj.server.adminserver.model.SexType;
import com.zwdbj.server.adminserver.service.AppConfigDto;
import com.zwdbj.server.adminserver.shiro.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "APP设置相关")
@RequestMapping("/api/app")
public class AppController {
    @RequestMapping(value = "/config",method = RequestMethod.GET)
    @ApiOperation(value = "默认的一些配置信息")
    public ResponseData<AppConfigDto> config() {
        AppConfigDto dto = new AppConfigDto();
        if(JWTUtil.getCurrentId()!=0) {
            dto.setRoles(new RoleIdentity().getRoles());
        }
        dto.setLoginType(LoginType.all());
        dto.setSexs(SexType.all());
        dto.setAppLivingOpen(true);
        return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,"",dto);
    }
}
