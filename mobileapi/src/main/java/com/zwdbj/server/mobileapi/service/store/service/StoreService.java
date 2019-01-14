package com.zwdbj.server.mobileapi.service.store.service;

import com.zwdbj.server.mobileapi.service.store.model.StoreModel;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

public interface StoreService {

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    ServiceStatusInfo<StoreModel> selectById(long id);
}
