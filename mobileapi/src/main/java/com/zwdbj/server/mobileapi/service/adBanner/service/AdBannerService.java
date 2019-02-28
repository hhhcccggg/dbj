package com.zwdbj.server.mobileapi.service.adBanner.service;

import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerDto;
import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerInput;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface AdBannerService {
    ServiceStatusInfo<List<AdBannerDto>> searchAdBanner(AdBannerInput adBannerInput);
}
