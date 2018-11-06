package com.zwdbj.server.adminserver.service.appVersion.service;

import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.appVersion.mapper.IAppVersionMapper;
import com.zwdbj.server.adminserver.service.appVersion.model.AdAppVersionInput;
import com.zwdbj.server.adminserver.service.appVersion.model.AppVersionDto;
import com.zwdbj.server.adminserver.service.appVersion.model.AppVersionInput;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppVersionService {
    @Autowired
    IAppVersionMapper appVersionMapper;



    @Transactional
    public ServiceStatusInfo<Long> newAppVersion(AdAppVersionInput input){

        Long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = this.appVersionMapper.newAppVersion(id,input);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"创建失败"+e.getMessage(),result);
        }
    }

    @Transactional
    public ServiceStatusInfo<Long> delAppVersion(Long id){
        Long result = 0L;
        try {
            result  = this.appVersionMapper.delAppVersion(id);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"删除版本失败"+e.getMessage(),result);
        }
    }

    @Transactional
    public List<AppVersionDto> searchVersionList(){
        try {
            List<AppVersionDto> dtos = this.appVersionMapper.searchVersionList();
            return dtos;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public ServiceStatusInfo<Long> updateVersion(Long id,AdAppVersionInput input){
        Long result = 0L;
        try {
            result  = this.appVersionMapper.updateVersion(id,input);
        return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
        return new ServiceStatusInfo<>(1,"修改版本失败"+e.getMessage(),result);
        }
    }

}
