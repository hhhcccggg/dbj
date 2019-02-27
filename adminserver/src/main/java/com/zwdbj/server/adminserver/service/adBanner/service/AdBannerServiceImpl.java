package com.zwdbj.server.adminserver.service.adBanner.service;

import com.zwdbj.server.adminserver.service.adBanner.mapper.AdBannerMapper;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerDto;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerInfo;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerSerchInput;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdBannerServiceImpl implements AdBannerService {
    @Autowired
    private AdBannerMapper adBannerMapper;
    @Autowired
    private QiniuService qiniuService;

    @Override
    public ServiceStatusInfo<List<AdBannerInfo>> searchAllAdBanners() {
        List<AdBannerInfo> result = null;
        try {
            result = this.adBannerMapper.searchAllAdBanners();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "获取adBanner失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<AdBannerInfo>> searchAdBanners(AdBannerSerchInput input) {
        List<AdBannerInfo> result = null;
        try {
            result = this.adBannerMapper.searchAdBanners(input);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "搜索adBanner失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> modifyAdBanner(AdBannerDto dto, long id) {
        Long result = 0L;
        try {
            if (dto.getImageUrl() != null && !"".equals(dto.getImageUrl())) {
                dto.setImageUrl(qiniuService.url(dto.getImageUrl()));
            }
            result = this.adBannerMapper.modifyAdBanner(dto, id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改adBanner失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> createAdBanner(AdBannerDto dto) {
        Long result = 0L;
        try {
            if (dto.getPlatform().equals("") || dto.getPlatform()==null)dto.setPlatform("ALL");
            if (dto.getType().equals("") || dto.getType()==null)dto.setType("ALL");
            if (dto.getRefUrl().equals("") || dto.getRefUrl()==null)dto.setRefUrl("http://www.zwdbj.com/");
            long id = UniqueIDCreater.generateID();
            dto.setImageUrl(qiniuService.url(dto.getImageUrl()));
            result = this.adBannerMapper.createAdBanner(id, dto);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建adBanner失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteAdBanner(long id) {
        Long result = 0L;
        try {
            result = this.adBannerMapper.deleteAdBanner(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除adBanner失败" + e.getMessage(), null);
        }
    }
}
