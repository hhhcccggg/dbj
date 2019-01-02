package com.zwdbj.server.shopadmin.controller;

import com.zwdbj.server.shop_admin_service.service.shopdetail.model.OpeningHours;
import com.zwdbj.server.shop_admin_service.service.shopdetail.model.StoreDto;
import com.zwdbj.server.shop_admin_service.service.shopdetail.service.ShopDetailService;
import com.zwdbj.server.tokencenter.TokenCenterManager;
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

@Api("店铺信息相关")
@RequestMapping("/api/storeInfo/dbj")
@RestController
public class ShopDetailController {
    @Autowired
    private ShopDetailService shopDetailServiceImpl;
    @Autowired
    private TokenCenterManager tokenCenterManager;

    @RequestMapping(value = "/basicInfo", method = RequestMethod.GET)
    @ApiOperation(value = "展示店铺基本信息")
    public ResponseData<StoreDto> basicInfo() {
        long id = JWTUtil.getCurrentId();
        tokenCenterManager.fetchUser(String.valueOf(id));
        long storeId = 0L;
        ServiceStatusInfo<StoreDto> statusInfo = shopDetailServiceImpl.findStoreDetail(storeId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());

        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);


    }

    @RequestMapping(value = "/openingHours", method = RequestMethod.GET)
    @ApiOperation(value = "展示营业时间")
    public ResponseData<List<OpeningHours>> openingHours() {
        long id = JWTUtil.getCurrentId();
        tokenCenterManager.fetchUser(String.valueOf(id));
        long storeId = 0L;
        ServiceStatusInfo<List<OpeningHours>> statusInfo = shopDetailServiceImpl.findOpeningHours(storeId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());

        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);


    }

    @RequestMapping(value = "/modifyOpeningHours", method = RequestMethod.POST)
    @ApiOperation(value = "修改营业时间")
    public ResponseData<Long> modifyOpeningHours(@RequestBody List<OpeningHours> list) {
        ServiceStatusInfo<Long> statusInfo = this.shopDetailServiceImpl.modifyOpeningHours(list);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }

    @RequestMapping(value = "/addOpeningHours", method = RequestMethod.POST)
    @ApiOperation(value = "增加营业时间段")
    public ResponseData<Long> addOpeningHours(@RequestBody List<OpeningHours> list) {
        long id = JWTUtil.getCurrentId();
        tokenCenterManager.fetchUser(String.valueOf(id));
        long storeId = 0L;
        ServiceStatusInfo<Long> statusInfo = this.shopDetailServiceImpl.addOpeningHours(storeId, list);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);


    }

    @RequestMapping(value = "/location", method = RequestMethod.GET)
    @ApiOperation(value = "显示位置信息")
    public ResponseData<String> showLocation() {
        long id = JWTUtil.getCurrentId();
        tokenCenterManager.fetchUser(String.valueOf(id));
        long storeId = 0L;
        ServiceStatusInfo<String> statusInfo = this.shopDetailServiceImpl.showLocation(storeId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);

    }
}
