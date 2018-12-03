package com.zwdbj.shop_common_service.logistics.service;

import com.zwdbj.server.utility.model.ServiceStatusInfo;
import com.zwdbj.shop_common_service.logistics.model.Logistics;

public interface ILogisticsService {
    //查询快递
    ServiceStatusInfo<Logistics> selectLogistics(String no, String type);
}
