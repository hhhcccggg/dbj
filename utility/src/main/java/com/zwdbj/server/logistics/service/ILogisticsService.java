package com.zwdbj.server.logistics.service;

import com.zwdbj.server.logistics.model.Logistics;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

public interface ILogisticsService {
    //查询快递
    ServiceStatusInfo<Logistics> selectLogistics(String no, String type);
}
