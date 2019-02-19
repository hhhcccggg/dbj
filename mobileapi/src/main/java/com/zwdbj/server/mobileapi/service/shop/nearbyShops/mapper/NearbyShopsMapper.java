package com.zwdbj.server.mobileapi.service.shop.nearbyShops.mapper;

import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface NearbyShopsMapper {
    @Select("select id,name,logoUrl,grade,subName,address,contactPhone,mainConverImage,coverImages,legalSubjectId from shop_stores where id=#{storeId}")
    ShopInfo searchShopsById(@Param("storeId") long storeId);

    @Select("select c.id,c.name from core_categories as c,o2o_offlineStoreServiceScopes as o where " +
            " o.storeId=#{storeId} and c.id=o.serviceScopeId ")
    List<StoreServiceCategory> searchServiceScopes(@Param("storeId") long storeId);

    @Select("select id,name,storeId,couponCount from shop_discountCoupons where storeId=#{storeId}")
    List<DiscountCoupon> searchDiscountCoupon(@Param("storeId") long storeId);


    @Select("select c.id,c.name from core_categories as c,o2o_offlineStoreExtraServices as o where " +
            " o.storeId=#{storeId} and c.id=o.extraServiceId ")
    List<StoreServiceCategory> searchExtraServices(@Param("storeId") long storeId);

    @Select("select storeId, day,openTime,closeTime from o2o_offlineStoreOpeningHours where storeId=#{storeId}")
    List<OpeningHours> searchOpeningHours(@Param("storeId") long storeId);

    @Select("select o.userId,o.storeId,u.userName,u.avatarUrl,count(v.id) as videoCounts from " +
            "o2o_offlineStoreStaffs as o,core_users as u,core_videos as v where " +
            "o.storeId=#{storeId} and o.userId=u.id=v.userId GROUP BY v.userId")
    SuperStar searchSuperStar(@Param("storeId") long storeId);

    @Select("select id,name,storeId,couponCount,userInfo,validStartTime,validEndTime,order,rule" +
            "onlySupportOriginProduct,range where id=#{discountCouponId}")
    DiscountCouponDetail seachDiscountCouponDetail(@Param("discountCouponId") long discountCouponId);

    @Select("select id,name,logoUrl,grade,longitude,latitude,address,cityId,cityLevel from shop_stores where status=0 and stopService =0")
    List<NearbyShop> nearbyShopList();

    @SelectProvider(type = NearbyShopsSqlProvider.class,method = "getNearByDiscount")
    List<DiscountCoupon> getNearByDiscount(@Param("longitude") double longitude, @Param("latitude") double latitude);


}
