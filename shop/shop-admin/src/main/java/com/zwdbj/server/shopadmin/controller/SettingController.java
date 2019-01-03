package com.zwdbj.server.shopadmin.controller;

import com.zwdbj.server.shop_admin_service.setting.model.ExtraServices;
import com.zwdbj.server.shop_admin_service.setting.model.OpeningHour;
import com.zwdbj.server.shop_admin_service.setting.model.ServiceScopes;
import com.zwdbj.server.shop_admin_service.setting.service.SettingService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("设置相关")
@RequestMapping("/api/setting/dbj")
@RestController
public class SettingController {
    @Autowired
    private SettingService settingServiceImpl;

    @RequestMapping(value = "/openHour", method = RequestMethod.GET)
    @ApiOperation(value = "查询营业时间")
    public ResponseData<OpeningHour> selectOpenHour() {
        long id = JWTUtil.getCurrentId();
        ServiceStatusInfo<OpeningHour> statusInfo = this.settingServiceImpl.selectOpenHour(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/extraServices", method = RequestMethod.GET)
    @ApiOperation(value = "查询额外服务")
    public ResponseData<List<ExtraServices>> selectExtraServices() {
        long id = JWTUtil.getCurrentId();
        ServiceStatusInfo<List<ExtraServices>> statusInfo = this.settingServiceImpl.selectExtraServices(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @RequestMapping(value = "/openScope", method = RequestMethod.GET)
    @ApiOperation(value = "查询服务范围")
    public ResponseData<List<ServiceScopes>> selectServiceScopes() {
        long id = JWTUtil.getCurrentId();
        ServiceStatusInfo<List<ServiceScopes>> statusInfo = this.settingServiceImpl.selectServiceScopes(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }
    @RequestMapping(value = "/updateOpenHour",method = RequestMethod.POST)
    @ApiOperation(value = "修改营业时间")
    public ResponseData<Long> updateOpenHour(@RequestBody OpeningHour openingHour){
        long id = JWTUtil.getCurrentId();
        ServiceStatusInfo<Long> statusInfo = this.settingServiceImpl.updateOpenHour(id,openingHour);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

}
