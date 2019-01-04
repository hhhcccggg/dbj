package com.zwdbj.server.adminserver.controller.shop;

import com.zwdbj.server.shop_common_service.logistics.model.Logistics;
import com.zwdbj.server.shop_common_service.logistics.service.ILogisticsService;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogisticsController {

    @Autowired
    ILogisticsService logisticsServiceImpl;

    @RequestMapping(value = "logistics",method = RequestMethod.GET)
    public ServiceStatusInfo<Logistics> getLogistics(){
        ServiceStatusInfo<Logistics> logistics = logisticsServiceImpl.selectLogistics("73106644852000","zto");
        return logistics;
    }

}
