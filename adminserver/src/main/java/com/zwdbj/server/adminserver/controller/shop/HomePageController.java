package com.zwdbj.server.adminserver.controller.shop;

import com.zwdbj.server.shop_admin_service.service.homepage.model.OrderTrend;
import com.zwdbj.server.shop_admin_service.service.homepage.model.TodayOverview;
import com.zwdbj.server.shop_admin_service.service.homepage.service.HomePageService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Api(value = "商家中心首页相关")
@RestController
@RequestMapping(value = "/api/shop/homePage/dbj")
public class HomePageController {

    @Autowired
    private HomePageService homePageServiceImpl;

    @RequestMapping(value = "/todayOverview", method = RequestMethod.GET)
    @ApiOperation(value = "今日概览")
    public ResponseData<TodayOverview> todayOverview() {
        long id = JWTUtil.getCurrentId();
        ServiceStatusInfo<TodayOverview> result = homePageServiceImpl.select(id);
        if (result.isSuccess()) {
            return new ResponseData<>(0, "", result.getData());
        }
        return new ResponseData<>(1, result.getMsg(), null);
    }

    @RequestMapping(value = "/orderDayTrend", method = RequestMethod.GET)
    @ApiOperation(value = "订单今日趋势")
    public ResponseData<HashMap<String, List<OrderTrend>>> orderDayTrend() {
        long id = JWTUtil.getCurrentId();
        ServiceStatusInfo<HashMap<String, List<OrderTrend>>> result = homePageServiceImpl.selectOrderDayTrend(id);
        if (result.isSuccess()) {
            return new ResponseData<>(0, "", result.getData());
        }
        return new ResponseData<>(1, result.getMsg(), null);
    }

    @RequestMapping(value = "/orderWeekTrend", method = RequestMethod.GET)
    @ApiOperation(value = "订单本周趋势")
    public ResponseData<HashMap<String, List<OrderTrend>>> orderWeekTrend() {
        long id = JWTUtil.getCurrentId();
        ServiceStatusInfo<HashMap<String, List<OrderTrend>>> result = homePageServiceImpl.selectOrderWeekTrend(id);
        if (result.isSuccess()) {
            return new ResponseData<>(0, "", result.getData());
        }
        return new ResponseData<>(1, result.getMsg(), null);
    }

//    @RequestMapping(value = "/videoDayTrend", method = RequestMethod.GET)
//    @ApiOperation(value = "视频今日趋势")
//    public ResponseData<List<VideoTrend>> videoDayTrend() {
//        long id = JWTUtil.getCurrentId();
//        ServiceStatusInfo<List<VideoTrend>> result = homePageServiceImpl.selectVideoDayTrend(id);
//        if (result.isSuccess()) {
//            return new ResponseData<>(0, "", result.getData());
//        }
//        return new ResponseData<>(1, result.getMsg(), null);
//    }
//
//    @RequestMapping(value = "/videoWeekTrend", method = RequestMethod.GET)
//    @ApiOperation(value = "视频今日趋势")
//    public ResponseData<List<VideoTrend>> videoWeekTrend() {
//        long id = JWTUtil.getCurrentId();
//        ServiceStatusInfo<List<VideoTrend>> result = homePageServiceImpl.selectVideoWeekTrend(id);
//        if (result.isSuccess()) {
//            return new ResponseData<>(0, "", result.getData());
//        }
//        return new ResponseData<>(1, result.getMsg(), null);
//    }
}
