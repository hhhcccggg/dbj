package com.zwdbj.server.shop_admin_service.service.homepage.mapper;

import com.zwdbj.server.shop_admin_service.service.homepage.model.OrderTrend;
import com.zwdbj.server.shop_admin_service.service.homepage.model.TodayOverview;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface HomePageMapper {
    @Select("SELECT sum(p.payment) as payments ,count(p.id) as productorders from " +
            "shop_productOrders as p where p.storeId=#{storeId} and p.status like 'STATE_BUYER_PAYED' or " +
            "'STATE_SELLER_DELIVERIED' or 'STATE_BUYER_DELIVERIED' or 'STATE_SUCCESS' ")
    TodayOverview select(@PathVariable("storeId") long storeId);

    @Select("select count(id) as counts ,DATE_FORMAT(p.createTime,'%k') as createTime from shop_productOrders as p " +
            "where p.storeId=#{storeId} and DATE_FORMAT(NOW(),'%m-%d-%Y')= DATE_FORMAT(p.createTime,'%m-%d-%Y') " +
            "and p.status='STATE_WAIT_BUYER_PAY' GROUP BY createTime ORDER BY createTime")
    List<OrderTrend> orderDayTrendNotPay(@PathVariable("storeId") long storeId);

    @Select("select count(id) as counts ,DATE_FORMAT(p.createTime,'%w') as createTime from shop_productOrders as p " +
            "where p.storeId=#{storeId} and p.status='STATE_WAIT_BUYER_PAY' and DATE_FORMAT(NOW(),'%v-%x')= DATE_FORMAT(p.createTime,'%v-%x') " +
            "GROUP BY createTime ORDER BY createTime")
    List<OrderTrend> orderWeekTreadNotPay(@PathVariable("storeId") long storeId);

    @Select("select count(id) as counts ,DATE_FORMAT(p.createTime,'%k') as createTime from shop_productOrders as p " +
            "where p.storeId=#{storeId} and DATE_FORMAT(NOW(),'%m-%d-%Y')= DATE_FORMAT(p.createTime,'%m-%d-%Y') " +
            "and p.status='STATE_BUYER_PAYED' GROUP BY createTime ORDER BY createTime")
    List<OrderTrend> orderDayTrendPay(@PathVariable("storeId") long storeId);

    @Select("select count(id) as counts ,DATE_FORMAT(p.createTime,'%w') as createTime from shop_productOrders as p " +
            "where p.storeId=#{storeId} and  p.status='STATE_BUYER_PAYED' and DATE_FORMAT(NOW(),'%v-%x')= DATE_FORMAT(p.createTime,'%v-%x') " +
            "GROUP BY createTime ORDER BY createTime")
    List<OrderTrend> orderWeekTrendPay(@PathVariable("storeId") long storeId);

//    @Select("select a.userId,a.storeId,b.productId from shop_productOrders as a,shop_productOrderItems as b " +
//            "where a.storeId=#{storeId} and a.buyerRate=1 and b.storeId=a.storeId")
//    long Comnnets(@PathVariable("storeId") long storeId);

//    @Select("select count(*) as counts,DATE_FORMAT(v.createTime,'%k') as createTime from" +
//            "o2o_offlineStoreStaffs as o2o,dbj_server_db.core_videos as v where " +
//            "o2o.offlineStoreId=#{offlineStoreId} and v.userId=o2o.userId and " +
//            "DATE_FORMAT(NOW(),'%m-%d-%Y')= DATE_FORMAT(v.createTime,'%m-%d-%Y') " +
//            "GROUP BY createTime ORDER BY createTime")
//    List<VideoTrend> videoDayTrend(@PathVariable("offlineStoreId") long offlineStoreId);
//
//    @Select("select count(*) as counts,DATE_FORMAT(v.createTime,'%w') as createTime from" +
//            "o2o_offlineStoreStaffs as o2o,dbj_server_db.core_videos as v where " +
//            "o2o.offlineStoreId=#{offlineStoreId} and v.userId=o2o.userId and " +
//            "DATE_FORMAT(NOW(),'%v-%x')= DATE_FORMAT(v.createTime,'%v-%x') " +
//            "GROUP BY createTime ORDER BY createTime")
//    List<VideoTrend> videoWeekTrend(@PathVariable("offlineStoreId") long offlineStoreId);


}


