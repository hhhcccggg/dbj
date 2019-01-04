package com.zwdbj.server.mobileapi.controller.shop;

import com.zwdbj.server.shop_common_service.logistics.model.Logistics;
import com.zwdbj.server.shop_common_service.logistics.service.ILogisticsService;
import com.zwdbj.server.utility.model.ResponseData;
import com.zwdbj.server.utility.model.ResponseDataCode;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/home")
public class HomeController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @Autowired
    ILogisticsService logisticsServiceImpl;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "logistics",method = RequestMethod.GET)
    public ResponseData<Logistics> getLogistics(){
        ServiceStatusInfo<Logistics> logistics = logisticsServiceImpl.selectLogistics("73106644852000","zto");
        if (logistics.isSuccess()) {
            return new ResponseData<>(ResponseDataCode.STATUS_NORMAL, "", logistics.getData());
        }
        return new ResponseData<>(ResponseDataCode.STATUS_ERROR, logistics.getMsg(), null);
    }

}
