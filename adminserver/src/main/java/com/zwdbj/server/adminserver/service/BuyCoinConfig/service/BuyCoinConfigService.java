package com.zwdbj.server.adminserver.service.BuyCoinConfig.service;

import com.zwdbj.server.adminserver.service.BuyCoinConfig.model.BuyCoinConfig;
import com.zwdbj.server.adminserver.service.BuyCoinConfig.model.BuyCoinConfigAdd;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface BuyCoinConfigService {
    ServiceStatusInfo<Long> create(BuyCoinConfigAdd buyCoinConfig);

    ServiceStatusInfo<Long> update(BuyCoinConfig buyCoinConfig);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<List<BuyCoinConfig>> searchByType(String type);

    ServiceStatusInfo<List<BuyCoinConfig>> searchAll();
}
