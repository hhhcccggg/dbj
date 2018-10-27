package com.zwdbj.server.adminserver.service.appVersion.service;

import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.appVersion.mapper.IAppVersionMapper;
import com.zwdbj.server.adminserver.service.appVersion.model.AdAppVersionInput;
import com.zwdbj.server.adminserver.service.appVersion.model.AppVersionDto;
import com.zwdbj.server.adminserver.service.appVersion.model.AppVersionInput;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppVersionService {
    @Autowired
    IAppVersionMapper appVersionMapper;

    public ServiceStatusInfo<AppVersionDto> getAppVersion(AppVersionInput input){
        try {
            AppVersionDto dto = this.appVersionMapper.getAppVersion(input.getPlatform());
            if (dto==null)return new ServiceStatusInfo<>(1, "已经是最新版本了,不需要更新", null);
            String olderVersion = input.getVersion();
            String[] orderVs = olderVersion.split("\\.");
            int orderV1 = Integer.parseInt(orderVs[0]);
            int orderV2 = Integer.parseInt(orderVs[1]);
            int orderV3 = Integer.parseInt(orderVs[2]);
            String newVersion = dto.getVersion();
            String[] newVs = newVersion.split("\\.");
            int newV1 = Integer.parseInt(newVs[0]);
            int newV2 = Integer.parseInt(newVs[1]);
            int newV3 = Integer.parseInt(newVs[2]);
            if ((orderV1 < newV1) || ((orderV1 == newV1)&&(orderV2 < newV2)) || ((orderV1 == newV1)&&(orderV2 == newV2)&&(orderV3 < newV3))){
                return new ServiceStatusInfo<>(0, "发现新版本,请更新", dto);
            }else {
                return new ServiceStatusInfo<>(1, "已经是最新版本了,不需要更新", null);
            }
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }

    }


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
