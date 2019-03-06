package com.zwdbj.server.adminserver.service.shop.service.homepage.service;

import com.zwdbj.server.adminserver.service.shop.service.homepage.model.OrderTrend;
import com.zwdbj.server.adminserver.service.shop.service.homepage.model.TodayOverview;
import com.zwdbj.server.adminserver.service.shop.service.homepage.model.UnHandle;
import com.zwdbj.server.adminserver.service.shop.service.homepage.model.VideoTrend;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.HashMap;
import java.util.List;

public interface HomePageService {
    ServiceStatusInfo<TodayOverview> select(long sellerId);

    ServiceStatusInfo<HashMap<String, List<OrderTrend>>> selectOrderDayTrend(long sellerId);

    ServiceStatusInfo<HashMap<String, List<OrderTrend>>> selectOrderWeekTrend(long sellerId);

    ServiceStatusInfo<List<VideoTrend>> selectVideoDayTrend(long sellerId);

    ServiceStatusInfo<List<VideoTrend>> selectVideoWeekTrend(long sellerId);

    ServiceStatusInfo<UnHandle> selectUnHandle(long storeId);
}
