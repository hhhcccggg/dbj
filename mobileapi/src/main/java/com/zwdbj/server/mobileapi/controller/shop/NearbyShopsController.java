package com.zwdbj.server.mobileapi.controller.shop;

import com.zwdbj.server.mobileapi.service.shop.nearbyShops.service.NearbyShopService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "附近商家相关")
@RequestMapping(value = "/api/nearByShop/dbj")
@RestController
public class NearbyShopsController {
    @Autowired
    private NearbyShopService nearbyShopServiceImpl;


}
