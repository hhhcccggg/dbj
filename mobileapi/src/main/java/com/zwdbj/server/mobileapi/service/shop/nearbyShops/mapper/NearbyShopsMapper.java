package com.zwdbj.server.mobileapi.service.shop.nearbyShops.mapper;

import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface NearbyShopsMapper {
    @Select("select id,name,logoUrl,grade,subName,address,mainConverImage,coverImages,legalSubjectId from shop_stores where id=#{storeId}")
    ShopInfo searchShopsById(@PathVariable("storeId") long storeId);

    @Select("select c.id,c.name from core_categories as c,o2o_offlineStoreServiceScopes as o where " +
            " o.storeId=#{storeId} and c.id=o.serviceScopeId ")
    List<StoreServiceCategory> searchServiceScopes(@PathVariable("storeId") long storeId);

    @Select("select id,name,storeId,couponCount from shop_discountCoupons where storeId=#{storeId}")
    List<DiscountCoupon> searchDiscountCoupon(@PathVariable("storeId") long storeId);


    @Select("select c.id,c.name from core_categories as c,o2o_offlineStoreExtraServices as o where " +
            " o.storeId=#{storeId} and c.id=o.extraServiceId ")
    List<StoreServiceCategory> searchExtraServices(@PathVariable("storeId") long storeId);

    @Select("select storeId, day,openTime,closeTime from o2o_offlineStoreOpeningHours where storeId=#{storeId}")
    List<OpeningHours> searchOpeningHours(@PathVariable("storeId") long storeId);

    @Select("select o.userId,o.storeId,u.userName,count(v.id) as videoCounts from " +
            "o2o_offlineStoreStaffs as o,core_users as u,core_videos as v where " +
            "o.storeId=#{storeId} and o.userId=u.id=v.userId GROUP BY v.userId")
    SuperStar searchSuperStar(@PathVariable("storeId") long storeId);

    @Select("select id,name,storeId,couponCount,userInfo,validStartTime,validEndTime,order,rule" +
            "onlySupportOriginProduct,range where id=#{discountCouponId}")
    DiscountCouponDetail seachDiscountCouponDetail(@PathVariable("discountCouponId") long discountCouponId);
}
