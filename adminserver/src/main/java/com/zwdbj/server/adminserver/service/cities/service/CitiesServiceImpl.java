package com.zwdbj.server.adminserver.service.cities.service;

import com.zwdbj.server.adminserver.service.cities.mapper.ICitiesMapper;
import com.zwdbj.server.adminserver.service.cities.model.Cities;
import com.zwdbj.server.adminserver.service.cities.model.LevelType;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CitiesServiceImpl implements CitiesService{

    @Autowired
    private ICitiesMapper iCitiesMapper;

    @Override
    public ServiceStatusInfo<List<Cities>> selectAllProvince() {
        try {
            List<Cities> list = iCitiesMapper.selectCondition(LevelType.province,0);
            return new ServiceStatusInfo<>(0 ,"",list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1 ,"查询地址失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<List<Cities>> selectCondition(LevelType levelType, long parenId) {
        try {
            List<Cities> list = iCitiesMapper.selectCondition(levelType,parenId);
            return new ServiceStatusInfo<>(0 ,"",list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1 ,"查询地址失败",null);
        }
    }
}
