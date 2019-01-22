package com.zwdbj.server.mobileapi.controller;

import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerDto;
import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerInput;
import com.zwdbj.server.mobileapi.service.adBanner.service.AdBannerService;
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

@RequestMapping("/api/banner")
@RestController
@Api(description = "banner相关")
public class AdBannerController {
    @Autowired
    private AdBannerService adBannerServiceImpl;

    @ApiOperation(value = "拉取banner信息")
    @RequestMapping(value = "/searchBanner", method = RequestMethod.POST)
    public ResponseData<List<AdBannerDto>> searchAdBanner(@RequestBody AdBannerInput adBannerInput) {
        ServiceStatusInfo<List<AdBannerDto>> statusInfo = this.adBannerServiceImpl.searchAdBanner(adBannerInput);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }


}

