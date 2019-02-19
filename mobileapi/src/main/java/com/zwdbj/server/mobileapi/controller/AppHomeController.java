package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.appHome.model.AppHomeInput;
import com.zwdbj.server.mobileapi.service.appHome.model.AppHomeResDto;
import com.zwdbj.server.mobileapi.service.appHome.service.AppHomeService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/app/home")
@Api(description = "app首页相关")
public class AppHomeController {
    @Autowired
    private AppHomeService appHomeService;
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    @ApiOperation(value = "app首页相关")
    public ResponseData<AppHomeResDto> allHome(@RequestBody AppHomeInput input){
        ServiceStatusInfo<AppHomeResDto> statusInfo = this.appHomeService.allHome(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "成功", statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, statusInfo.getMsg(), null);
    }

}
