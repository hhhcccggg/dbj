package com.zwdbj.server.mobileapi.controller.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.*;
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
    @RequestMapping(value = "/shopHomePage/{storeId}", method = RequestMethod.GET)
    public ResponseData<ShopInfo> shopHomePage(@PathVariable("storeId") long storeId) {
        ServiceStatusInfo<ShopInfo> statusInfo = nearbyShopServiceImpl.shopHomePage(storeId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, statusInfo.getMsg(), statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    /*@ApiOperation(value = "附近商家列表")
    @RequestMapping(value = "/shopList", method = RequestMethod.GET)
    public ResponsePageInfoData<List<NearbyShop>> shopList(@RequestParam(value = "pageNo", defaultValue = "1", required = true) int pageNo,
                                                           @RequestParam(value = "rows", required = true, defaultValue = "10") int rows) {
        Page<NearbyShop> page = PageHelper.startPage(pageNo, rows);
        List<NearbyShop> list = this.nearbyShopServiceImpl.nearbyShopList(pageNo).getData();
        return new ResponsePageInfoData<>(0, "", list, page.getTotal());

    }*/

    @ApiOperation(value = "搜索商家")
    @RequestMapping(value = "/searchShop", method = RequestMethod.POST)
    public ResponsePageInfoData<List<SearchShop>> searchShop(@RequestBody SearchInfo searchInfo) {
        ServiceStatusInfo<List<SearchShop>> statusInfo = this.nearbyShopServiceImpl.searchShop(searchInfo);
        if (statusInfo.isSuccess()) {
            return new ResponsePageInfoData<>(0, statusInfo.getMsg(), statusInfo.getData(), statusInfo.getData().size());
        }
        return new ResponsePageInfoData<>(1, statusInfo.getMsg(), null, 0);


    }

    @ApiOperation(value = "根据优惠券的id查询此优惠券的详情")
    @RequestMapping(value = "/getDetail/discount/{id}", method = RequestMethod.GET)
    public ResponseData<DiscountCouponDetail> getDiscountById(@PathVariable long id) {
        ServiceStatusInfo<DiscountCouponDetail> statusInfo = this.nearbyShopServiceImpl.searchDiscountCouponDetail(id);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }

    @ApiOperation(value = "查询附近的优惠券")
    @RequestMapping(value = "/nearby/discount/{longitude}/{latitude}", method = RequestMethod.GET)
    public ResponsePageInfoData<List<DiscountCoupon>> getNearByDiscount(@PathVariable double longitude,
                                                                        @PathVariable double latitude,
                                                                        @RequestParam(value = "page", required = true, defaultValue = "1") int pageNo,
                                                                        @RequestParam(value = "rows", required = true, defaultValue = "10") int rows) {
        Page<DiscountCoupon> pageInfo = PageHelper.startPage(pageNo, rows);
        List<DiscountCoupon> discountCouponDetails = this.nearbyShopServiceImpl.getNearByDiscount(longitude, latitude);
        return new ResponsePageInfoData<>(0, "", discountCouponDetails, pageInfo.getTotal());
    }

    @ApiOperation(value = "查询商家代言人")
    @RequestMapping(value = "/getSuperStar/{storeId}", method = RequestMethod.GET)
    public ResponseData<List<SuperStar>> getSuperStar(@PathVariable("storeId") long storeId) {
        ServiceStatusInfo<List<SuperStar>> statusInfo = nearbyShopServiceImpl.superStar(storeId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }
    @ApiOperation(value = "商家的认证资料")
    @RequestMapping(value = "/authentication/{storeId}", method = RequestMethod.GET)
    public ResponseData<StoreAuthenticationInfo> authenticationStore(@PathVariable("storeId") long storeId) {
        ServiceStatusInfo<StoreAuthenticationInfo> statusInfo = nearbyShopServiceImpl.authenticationStore(storeId);
        if (statusInfo.isSuccess()) {
            return new ResponseData<>(0, "", statusInfo.getData());
        }
        return new ResponseData<>(1, statusInfo.getMsg(), null);
    }


}
