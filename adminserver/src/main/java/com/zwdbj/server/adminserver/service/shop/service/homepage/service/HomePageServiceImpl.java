package com.zwdbj.server.adminserver.service.shop.service.homepage.service;

import com.zwdbj.server.adminserver.service.shop.service.homepage.mapper.HomePageMapper;
import com.zwdbj.server.adminserver.service.shop.service.homepage.model.OrderTrend;
import com.zwdbj.server.adminserver.service.shop.service.homepage.model.TodayOverview;
import com.zwdbj.server.adminserver.service.shop.service.homepage.model.UnHandle;
import com.zwdbj.server.adminserver.service.shop.service.homepage.model.VideoTrend;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class HomePageServiceImpl implements HomePageService {
    @Autowired
    private HomePageMapper homePageMapper;

    public ServiceStatusInfo<TodayOverview> select(long sellerId) {
        TodayOverview result = null;
        try {
            result = this.homePageMapper.select(sellerId);
            //调用远程服务查询代言人视频数


            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询每日概览失败" + e.getMessage(), null);

        }
    }

    @Override
    public ServiceStatusInfo<HashMap<String, List<OrderTrend>>> selectOrderWeekTrend(long sellerId) {
        List<OrderTrend> notPay = null;
        List<OrderTrend> pay = null;
        HashMap<String, List<OrderTrend>> result = null;
        try {

            notPay = this.homePageMapper.orderWeekTreadNotPay(sellerId);
            pay = this.homePageMapper.orderWeekTrendPay(sellerId);
            result.put("下单", notPay);
            result.put("付款", pay);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询每周订单趋势失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<HashMap<String, List<OrderTrend>>> selectOrderDayTrend(long sellerId) {
        List<OrderTrend> notPay = null;
        List<OrderTrend> pay = null;
        HashMap<String, List<OrderTrend>> result = null;
        try {

            notPay = this.homePageMapper.orderDayTrendNotPay(sellerId);
            pay = this.homePageMapper.orderDayTrendPay(sellerId);
            result.put("下单", notPay);
            result.put("付款", pay);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询每日订单趋势失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<UnHandle> selectUnHandle(long storeId) {
        try {
            UnHandle handle = new UnHandle();
            handle.setComments(homePageMapper.selectUnRefcomment(storeId));
            handle.setOrders(homePageMapper.selectUnUseOrder(storeId));
            return new ServiceStatusInfo<>(0, "", handle);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询未处理失败" + e.getMessage(), null);
        }

    }

    @Override
    public ServiceStatusInfo<List<VideoTrend>> selectVideoDayTrend(long sellerId) {
        try {
            List<VideoTrend> result = homePageMapper.videoDayTrend(sellerId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询代言人每日视频增量失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<VideoTrend>> selectVideoWeekTrend(long sellerId) {
        try {
            List<VideoTrend> result = homePageMapper.videoWeekTrend(sellerId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询代言人每周视频增量失败" + e.getMessage(), null);
        }
    }
}
