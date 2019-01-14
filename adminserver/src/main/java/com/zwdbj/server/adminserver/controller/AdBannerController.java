package com.zwdbj.server.adminserver.controller;

import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerDto;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerInfo;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerInput;
import com.zwdbj.server.adminserver.service.adBanner.service.AdBannerService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "adBanner相关")
@RequestMapping("/api/adBanner")
@RestController
public class AdBannerController {
    @Autowired
    private AdBannerService adBannerServiceImpl;


    @ApiOperation(value = "获取adBanners")
    @RequestMapping(value = "/searchAllAdBanners", method = RequestMethod.GET)
    public ResponseData<List<AdBannerInfo>> searchAllAdBanners() {
        ServiceStatusInfo<List<AdBannerInfo>> statusInfo = this.adBannerServiceImpl.searchAllAdBanners();
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @ApiOperation(value = "搜索adBanners")
    @RequestMapping(value = "/searchAdBanners", method = RequestMethod.POST)
    public ResponseData<List<AdBannerInfo>> searchAdBanners(@RequestBody AdBannerInput input) {
        ServiceStatusInfo<List<AdBannerInfo>> statusInfo = this.adBannerServiceImpl.searchAdBanners(input);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @ApiOperation(value = "修改adBanners")
    @RequestMapping(value = "/modifyAdBanners/{id}", method = RequestMethod.POST)
    public ResponseData<Long> modifyAdBanners(@RequestBody AdBannerDto dto, @PathVariable("id") long id) {
        ServiceStatusInfo<Long> statusInfo = this.adBannerServiceImpl.modifyAdBanner(dto, id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @ApiOperation(value = "创建adBanners")
    @RequestMapping(value = "/createAdBanners", method = RequestMethod.POST)
    public ResponseData<Long> createAdBanners(@RequestBody AdBannerDto dto) {
        ServiceStatusInfo<Long> statusInfo = this.adBannerServiceImpl.createAdBanner(dto);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @ApiOperation(value = "删除adBanners")
    @RequestMapping(value = "/deleteAdBanners/{id}", method = RequestMethod.GET)
    public ResponseData<Long> deleteAdBanners(@PathVariable("id") long id) {
        ServiceStatusInfo<Long> statusInfo = this.adBannerServiceImpl.deleteAdBanner(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }
}
