package com.zwdbj.server.mobileapi.controller;


import com.zwdbj.server.mobileapi.model.ResponseData;
import com.zwdbj.server.mobileapi.model.ResponseDataCode;
import com.zwdbj.server.mobileapi.service.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.appVersion.model.AppVersionDto;
import com.zwdbj.server.mobileapi.service.appVersion.model.AppVersionInput;
import com.zwdbj.server.mobileapi.service.appVersion.service.AppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appVersion")
@Api(description = "APP版本更新")
public class AppVersionController {
    @Autowired
    AppVersionService appVersionService;

    @RequestMapping(value = "/updateOrNot",method = RequestMethod.POST)
    @ApiOperation("版本查看是否需要更新")
    public ResponseData<AppVersionDto> getAppVersion(@RequestBody AppVersionInput input){
        ServiceStatusInfo<AppVersionDto> statusInfo  = this.appVersionService.getAppVersion(input);
        if (statusInfo.isSuccess()){
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);

    }

}
