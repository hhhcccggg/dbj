package com.zwdbj.server.mobileapi.controller.wxMini;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.setting.service.WxMiniProgramService;
import com.zwdbj.server.basemodel.model.ResponseData;
import com.zwdbj.server.basemodel.model.ResponseDataCode;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wx/mini")
@Api(description = "小程序相关")
public class WxMiniSettingController {
    @Autowired
    WxMiniProgramService wxMiniProgramService;

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
