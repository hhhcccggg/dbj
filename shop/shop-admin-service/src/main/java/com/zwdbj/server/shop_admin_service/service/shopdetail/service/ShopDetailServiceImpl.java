package com.zwdbj.server.shop_admin_service.service.shopdetail.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.shop_admin_service.http.HttpUtils;
import com.zwdbj.server.shop_admin_service.service.shopdetail.mapper.ShopDetailMapper;
import com.zwdbj.server.shop_admin_service.service.shopdetail.model.StoreServiceCategory;
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

    @Override
    public ServiceStatusInfo<List<StoreServiceCategory>> findExtraService(long storeId) {
        try {
            //查询店铺额外服务id
            List<Long> extraServiceIds = this.shopDetailMapper.selectExtraServiceId(storeId);
            //调用http远程服务查询对应id的额外服务
            String str = HttpUtils.getMethod("http://localhost:3008/api/category/dbj/searchCategory", extraServiceIds);
            JSONObject jsonObject = JSON.parseObject(str);

            return new ServiceStatusInfo(0, "", (List<StoreServiceCategory>) jsonObject.get("data"));

        } catch (Exception e) {
            return new ServiceStatusInfo(1, "显示店铺对应额外服务失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo findServiceScope(long storeId) {
        try {
            //查询店铺服务范围id
            List<Long> serviceScopeIds = this.shopDetailMapper.selectServiceScopeId(storeId);
            //调用http远程服务查询对应id的服务范围
            String str = HttpUtils.getMethod("http://localhost:3008/api/category/dbj/searchCategory", serviceScopeIds);
            JSONObject jsonObject = JSON.parseObject(str);
            return new ServiceStatusInfo(0, "", (List<StoreServiceCategory>) jsonObject.get("data"));

        } catch (Exception e) {
            return new ServiceStatusInfo(1, "显示店铺对应服务范围失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<StoreServiceCategory>> findAllExtraService(long stordId) {
        return null;
    }

    @Override
    public ServiceStatusInfo<Long> modifyExtraService(long storeId, List<StoreServiceCategory> list) {
        Long result = 0L;

        try {
            //先删除原来数据
            result += this.shopDetailMapper.deleteStoreExtraService(storeId);
            //再插入新数据
            for (StoreServiceCategory e : list) {
                Long id = UniqueIDCreater.generateID();
                result += this.shopDetailMapper.createStoreExtraService(id, storeId, e.getId());
            }
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改店铺额外服务失败" + e.getMessage(), null);
        }

    }
}
