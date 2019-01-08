package com.zwdbj.server.mobileapi.controller.shop;

import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.ShopInfo;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.service.NearbyShopService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "附近商家相关")
@RequestMapping(value = "/api/nearByShop/dbj")
@RestController
public class NearbyShopsController {
    @Autowired
    private NearbyShopService nearbyShopServiceImpl;

    @ApiOperation(value = "商家首页")
    @RequestMapping(value = "/shopHomePage", method = RequestMethod.GET)
    public ResponseData<ShopInfo> shopHomePage() {
        ServiceStatusInfo<ShopInfo> statusInfo = nearbyShopServiceImpl.shopHomePage(1);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }
}
