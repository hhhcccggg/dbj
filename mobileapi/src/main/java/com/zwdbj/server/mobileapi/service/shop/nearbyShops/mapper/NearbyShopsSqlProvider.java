package com.zwdbj.server.mobileapi.service.shop.nearbyShops.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class NearbyShopsSqlProvider {

    public String getNearByDiscount(Map params){
        Double longitude = (Double) params.get("longitude");
        Double latitude = (Double) params.get("latitude");
        SQL sql = new SQL()
                .SELECT("id,name,storeId,couponCount," +
                        "(st_distance(POINT (d.longitude, d.latitude),POINT(#{longitude},#{latitude}))*95000) AS distance " )
                .FROM("shop_discountCoupons d")
                .LEFT_OUTER_JOIN("shop_stores s on s.id=d.storeId")
                .HAVING("distance<10000")
                /*.WHERE(String.format("longitude BETWEEN %f AND %f",results[1],results[3]))
                .AND()
                .WHERE(String.format("latitude  BETWEEN %f AND %f",results[0],results[2]))
                .AND()*/
                .ORDER_BY("distance");
        return sql.toString();

    }
}
