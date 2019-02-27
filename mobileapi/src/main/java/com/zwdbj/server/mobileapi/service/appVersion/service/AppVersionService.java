package com.zwdbj.server.mobileapi.service.appVersion.service;

import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import com.zwdbj.server.mobileapi.service.appVersion.mapper.IAppVersionMapper;
import com.zwdbj.server.mobileapi.service.appVersion.model.AppVersionDto;
import com.zwdbj.server.mobileapi.service.appVersion.model.AppVersionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
