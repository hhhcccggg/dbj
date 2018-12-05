package com.zwdbj.server.shop_common_service.logistics.service;

import com.zwdbj.server.shop_common_service.logistics.model.Logistics;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

public interface ILogisticsService {
    //查询快递
    ServiceStatusInfo<Logistics> selectLogistics(String no, String type);
}
