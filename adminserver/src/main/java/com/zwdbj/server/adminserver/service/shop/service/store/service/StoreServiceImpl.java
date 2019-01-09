package com.zwdbj.server.adminserver.service.shop.service.store.service;

import com.zwdbj.server.adminserver.service.shop.service.store.mapper.IStoreMapper;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private IStoreMapper iStoreMapper;

    @Override
    public ServiceStatusInfo<Long> selectByLegalSubjectId(long legalSubjectId) {
        try{
            Long id = iStoreMapper.selectByLegalSubjectId(legalSubjectId);
            return new ServiceStatusInfo<>(0,"",id);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }
}
