package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.service.WxMiniProgramService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wx/mini")
@Api(description = "小程序相关")
public class WXMiniProgramController {
    @Autowired
    WxMiniProgramService wxMiniProgramService;

    @RequiresAuthentication
    @RequestMapping(value = "/code2Session", method = RequestMethod.GET)
    @ApiOperation(value = "通过临时登录凭证 code获得openId")
    public ResponseData<Object> getOpenIdByCode(@RequestParam String code) {
        ServiceStatusInfo<Object> statusInfo = this.wxMiniProgramService.getOpenIdByCode(code);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL,statusInfo.getMsg(),statusInfo.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR,statusInfo.getMsg(),null);
    }
}
