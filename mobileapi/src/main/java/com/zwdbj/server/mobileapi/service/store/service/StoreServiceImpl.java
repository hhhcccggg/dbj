package com.zwdbj.server.mobileapi.service.store.service;

import com.zwdbj.server.mobileapi.service.store.mapper.IStoreMapper;
import com.zwdbj.server.mobileapi.service.store.model.StoreModel;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private IStoreMapper iStoreMapper;

    @Override
    public ServiceStatusInfo<StoreModel> selectById(long id) {
        try{
            StoreModel storeModel = iStoreMapper.selectById(id);
            return new ServiceStatusInfo<>(0,"",storeModel);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }
}
