package com.zwdbj.server.mobileapi.service.adBanner.service;

import com.zwdbj.server.mobileapi.service.adBanner.mapper.AdBannerMapper;
import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerDto;
import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerInput;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdBannerServiceImpl implements AdBannerService {
    @Autowired
    private AdBannerMapper adBannerMapper;


    @Override
    public ServiceStatusInfo<List<AdBannerDto>> searchAdBanner(AdBannerInput adBannerInput) {
        List<AdBannerDto> result = null;
        try {
            result = this.adBannerMapper.searchAdBanner(adBannerInput);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "拉取banner信息失败" + e.getMessage(), null);

        }
    }
}
