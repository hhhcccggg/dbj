package com.zwdbj.server.shop_admin_service.homepage.mapper;

import com.zwdbj.server.shop_admin_service.homepage.model.OrderTrend;
import com.zwdbj.server.shop_admin_service.homepage.model.TodayOverview;
import com.zwdbj.server.shop_admin_service.homepage.model.VideoTrend;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface HomePageMapper {
    @Select("SELECT count(p.payment) as payments ,count(p.id) as productorders,count(v.id) as superStarVideos from " +
            "shop_productOrders as p,(SELECT offlineStoreId,userId FROM o2o_offlineStoreStaffs where isSuperStar=1) as o2o," +
            "dbj_server_db.core_videos as v " +
            "where p.sellerId=#{offlineStoreId} and p.status in (2,3,4,7) and o2o.offlineStoreId=p.sellerId and v.userId=o2o.userId")
    TodayOverview select(@PathVariable("offlineStoreId") long offlineStoreId);

    @Select("select count(id) as counts ,DATE_FORMAT(p.createTime,'%k') as createTime from shop_productOrders as p " +
            "where p.sellerId=#{offlineStoreId} and DATE_FORMAT(NOW(),'%m-%d-%Y')= DATE_FORMAT(p.createTime,'%m-%d-%Y') " +
            "and p.status=1 GROUP BY createTime ORDER BY createTime")
    List<OrderTrend> orderDayTrendNotPay(@PathVariable("offlineStoreId") long offlineStoreId);

    @Select("select count(id) as counts ,DATE_FORMAT(p.createTime,'%w') as createTime from shop_productOrders as p " +
            "where p.sellerId=#{offlineStoreId} and p.status=1 and DATE_FORMAT(NOW(),'%v-%x')= DATE_FORMAT(p.createTime,'%v-%x') " +
            "GROUP BY createTime ORDER BY createTime")
    List<OrderTrend> orderWeekTreadNotPay(@PathVariable("offlineStoreId") long offlineStoreId);

    @Select("select count(id) as counts ,DATE_FORMAT(p.createTime,'%k') as createTime from shop_productOrders as p " +
            "where p.sellerId=#{offlineStoreId} and DATE_FORMAT(NOW(),'%m-%d-%Y')= DATE_FORMAT(p.createTime,'%m-%d-%Y') " +
            "and p.status in (2,3,4,7) GROUP BY createTime ORDER BY createTime")
    List<OrderTrend> orderDayTrendPay(@PathVariable("offlineStoreId") long offlineStoreId);

    @Select("select count(id) as counts ,DATE_FORMAT(p.createTime,'%w') as createTime from shop_productOrders as p " +
            "where p.sellerId=#{offlineStoreId} and p.status in (2,3,4,7) and DATE_FORMAT(NOW(),'%v-%x')= DATE_FORMAT(p.createTime,'%v-%x') " +
            "GROUP BY createTime ORDER BY createTime")
    List<OrderTrend> orderWeekTrendPay(@PathVariable("offlineStoreId") long offlineStoreId);

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


}


