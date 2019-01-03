package com.zwdbj.server.shop_admin_service.service.shopdetail.service;

import com.zwdbj.server.shop_admin_service.service.shopdetail.mapper.ShopDetailMapper;
import com.zwdbj.server.shop_admin_service.service.shopdetail.model.OpeningHours;
import com.zwdbj.server.shop_admin_service.service.shopdetail.model.StoreDto;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopDetailServiceImpl implements ShopDetailService {

    @Autowired
    private ShopDetailMapper shopDetailMapper;

    @Override
    public ServiceStatusInfo<StoreDto> findStoreDetail(long storeId) {
        StoreDto result = null;
        try {
            result = this.shopDetailMapper.findStoreDetail(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询店铺基本信息失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<OpeningHours>> findOpeningHours(long storeId) {
        List<OpeningHours> result = null;
        try {
            result = this.shopDetailMapper.findOpeningHours(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询营业时间失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> modifyOpeningHours(List<OpeningHours> list) {
        Long result = 0L;
        try {
            for (OpeningHours openingHours : list) {
                result += this.shopDetailMapper.modifyOpeningHours(openingHours);
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改营业时间失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> addOpeningHours(long storeId, List<OpeningHours> list) {
        Long result = 0L;
        try {
            for (OpeningHours openingHours : list) {
                Long id = UniqueIDCreater.generateID();
                result += this.shopDetailMapper.createOpeningHours(id, storeId, openingHours);

            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "增加营业时间失败" + e.getMessage(), null);
        }

    }

    @Override
    public ServiceStatusInfo<String> showLocation(long storeId) {
        String result = null;
        try {
            result = this.shopDetailMapper.showLocation(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "显示位置信息失败" + e.getMessage(), result);
        }
    }
}
