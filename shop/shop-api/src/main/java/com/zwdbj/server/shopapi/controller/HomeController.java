package com.zwdbj.server.shopapi.controller;

import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.server.shop_common_service.logistics.model.Logistics;
import com.zwdbj.server.shop_common_service.logistics.service.ILogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @Autowired
    ILogisticsService logisticsService;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "logistics",method = RequestMethod.GET)
    public ServiceStatusInfo<Logistics> getLogistics(){
        ServiceStatusInfo<Logistics> logistics = logisticsService.selectLogistics("73106644852000","zto");
        return logistics;
    }

}
