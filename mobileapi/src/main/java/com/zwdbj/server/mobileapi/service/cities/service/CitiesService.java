package com.zwdbj.server.mobileapi.service.cities.service;

import com.zwdbj.server.mobileapi.service.cities.model.Cities;
import com.zwdbj.server.mobileapi.service.cities.model.LevelType;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface CitiesService {

    /**
     * 查询所有省
     * @return
     */
    ServiceStatusInfo<List<Cities>> selectAllProvince();

    /**
     * 根据条件查询
     * @param levelType
     * @param parenId
     * @return
     */
    ServiceStatusInfo<List<Cities>> selectCondition(LevelType levelType,long parenId);
}
