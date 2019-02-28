package com.zwdbj.server.adminserver.service.adBanner.service;

import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerDto;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerInfo;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerSerchInput;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface AdBannerService {
    ServiceStatusInfo<List<AdBannerInfo>> searchAllAdBanners();

    ServiceStatusInfo<List<AdBannerInfo>> searchAdBanners(AdBannerSerchInput input);

    ServiceStatusInfo<Long> modifyAdBanner(AdBannerDto dto, long id);

    ServiceStatusInfo<Long> createAdBanner(AdBannerDto dto);

    ServiceStatusInfo<Long> deleteAdBanner(long id);
}
