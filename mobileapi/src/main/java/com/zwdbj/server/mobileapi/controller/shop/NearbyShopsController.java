package com.zwdbj.server.mobileapi.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.NearbyShop;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.SearchInfo;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.SearchShop;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.ShopInfo;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.service.NearbyShopService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponsePageInfoData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "附近商家相关")
@RequestMapping(value = "/api/nearByShop/dbj")
@RestController
public class NearbyShopsController {
    @Autowired
    private NearbyShopService nearbyShopServiceImpl;

    @ApiOperation(value = "商家首页")
    @RequestMapping(value = "/shopHomePage{storeId}", method = RequestMethod.GET)
    public ResponseData<ShopInfo> shopHomePage(@PathVariable("storeId") long storeId) {
        ServiceStatusInfo<ShopInfo> statusInfo = nearbyShopServiceImpl.shopHomePage(storeId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @ApiOperation(value = "附近商家列表")
    @RequestMapping(value = "/shopList", method = RequestMethod.GET)
    public ResponsePageInfoData<List<NearbyShop>> shopList(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                           @RequestParam(value = "rows", required = true, defaultValue = "10") int rows) {
        Page<NearbyShop> page = PageHelper.startPage(pageNo, rows);
        List<NearbyShop> list = this.nearbyShopServiceImpl.nearbyShopList(pageNo).getData();
        return new ResponsePageInfoData<>(0, "", list, page.getTotal());

    }

    @ApiOperation(value = "搜索商家")
    @RequestMapping(value = "/searchShop", method = RequestMethod.POST)
    public ResponsePageInfoData<List<SearchShop>> searchShop(@RequestParam(value = "page", required = true, defaultValue = "1") int page,
                                                             @RequestParam(value = "rows", required = true, defaultValue = "10") int rows,
                                                             @RequestBody SearchInfo searchInfo
    ) {
        ServiceStatusInfo<List<SearchShop>> statusInfo = this.nearbyShopServiceImpl.searchShop(page, rows, searchInfo);

        return new ResponsePageInfoData<>(0, statusInfo.getMsg(), statusInfo.getData(), statusInfo.getData().size());

    }
}
