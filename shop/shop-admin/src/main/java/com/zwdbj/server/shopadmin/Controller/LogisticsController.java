package com.zwdbj.server.shopadmin.Controller;

import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.shop_common_service.logistics.model.Logistics;
import com.zwdbj.shop_common_service.logistics.service.ILogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogisticsController {

    @Autowired
    ILogisticsService logisticsService;

    @RequestMapping(value = "logistics",method = RequestMethod.GET)
    public ServiceStatusInfo<Logistics> getLogistics(){
        ServiceStatusInfo<Logistics> logistics = logisticsService.selectLogistics("73106644852000","zto");
        return logistics;
    }

}
