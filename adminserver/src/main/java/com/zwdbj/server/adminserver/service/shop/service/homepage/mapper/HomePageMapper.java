package com.zwdbj.server.adminserver.service.shop.service.homepage.mapper;

import com.zwdbj.server.adminserver.service.shop.service.homepage.model.OrderTrend;
import com.zwdbj.server.adminserver.service.shop.service.homepage.model.TodayOverview;
import com.zwdbj.server.adminserver.service.shop.service.homepage.model.VideoTrend;
import org.apache.ibatis.annotations.Param;
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

    @Select("select a.userId,a.storeId,b.productId from shop_productOrders as a,shop_productOrderItems as b " +
            "where a.storeId=#{storeId} and a.buyerRate=1 and b.storeId=a.storeId")
    long Comnnets(@PathVariable("storeId") long storeId);

    @Select("select count(*) as counts,DATE_FORMAT(v.createTime,'%k') as createTime from" +
            "o2o_offlineStoreStaffs as o2o,dbj_server_db.core_videos as v where " +
            "o2o.offlineStoreId=#{offlineStoreId} and v.userId=o2o.userId and " +
            "DATE_FORMAT(NOW(),'%m-%d-%Y')= DATE_FORMAT(v.createTime,'%m-%d-%Y') " +
            "GROUP BY createTime ORDER BY createTime")
    List<VideoTrend> videoDayTrend(@PathVariable("offlineStoreId") long offlineStoreId);

    @Select("select count(*) as counts,DATE_FORMAT(v.createTime,'%w') as createTime from" +
            "o2o_offlineStoreStaffs as o2o,dbj_server_db.core_videos as v where " +
            "o2o.offlineStoreId=#{offlineStoreId} and v.userId=o2o.userId and " +
            "DATE_FORMAT(NOW(),'%v-%x')= DATE_FORMAT(v.createTime,'%v-%x') " +
            "GROUP BY createTime ORDER BY createTime")
    List<VideoTrend> videoWeekTrend(@PathVariable("offlineStoreId") long offlineStoreId);

    //查询对应店铺的代言人
    @Select("select o2o.userId from o2o_offlineStoreStaffs  as o2o where o2o.storeId=#{storeId}")
    List<Long> selectSuperStar(@PathVariable("storeId") long storeId);

    @Select("select count(*) from shop_productOrders where storeId =#{storeId} and status='STATE_UNUSED' and isDeleted=0")
    int selectUnUseOrder(@Param("storeId") long storeId);

    @Select("select count(*) from core_comments as c where c.resourceOwnerId in(select id from shop_products where storeId=#{storeId} and isDeleted=0) and c.reviewStatus='pass' and c.isDeleted=0")
    int selectUnRefcomment(@Param("storeId")long storeId);

}


