package com.zwdbj.server.mobileapi.service.shop.nearbyShops.service;

import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface NearbyShopService {
    ServiceStatusInfo<ShopInfo> shopHomePage(long storeId);

    ServiceStatusInfo<SuperStar> superStar(long storeId);

    ServiceStatusInfo<DiscountCouponDetail> searchDiscountCouponDetail(long discountCouponId);

    ServiceStatusInfo<List<NearbyShop>> nearbyShopList(int pageNo);

    ServiceStatusInfo<List<SearchShop>> searchShop(int page, int rows, SearchInfo info);
    List<DiscountCoupon> getNearByDiscount(double longitude,double latitude);
}
