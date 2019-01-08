package com.zwdbj.server.mobileapi.service.shop.nearbyShops.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.mapper.NearbyShopsMapper;
import com.zwdbj.server.mobileapi.service.shop.nearbyShops.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class NearbyShopServiceImpl implements NearbyShopService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private NearbyShopsMapper nearbyShopsMapper;

    @Override
    public ServiceStatusInfo<ShopInfo> shopInfo(long storeId) {
        try {
            //判断redis缓存中是否有当前列表信息，如果有从redis中获取。若无从数据库查询
            ValueOperations valueOperations = redisTemplate.opsForValue();
            if (valueOperations.get("shopInfo" + storeId) != null) {
                String str = (String) valueOperations.get("shopInfo" + storeId);
                ShopInfo result = (ShopInfo) JSONObject.parse(str);
                return new ServiceStatusInfo<>(0, "", result);
            }
            ShopInfo result = nearbyShopsMapper.searchShopsById(storeId);

            List<DiscountCoupon> discountCoupons = this.nearbyShopsMapper.searchDiscountCoupon(storeId);
            result.setDiscountCoupons(discountCoupons);

            List<ShopServices> serviceScopes = this.nearbyShopsMapper.searchServiceScopes(storeId);
            result.setServiceScopes(serviceScopes);

            List<ShopServices> extraServices = this.nearbyShopsMapper.searchExtraServices(storeId);
            result.setExtraServices(extraServices);

            List<OpeningHours> openingHours = this.nearbyShopsMapper.searchOpeningHours(storeId);
            result.setOpeningHours(openingHours);

            valueOperations.set("shopInfo" + storeId, JSON.toJSONString(result));
            redisTemplate.expire("shopInfo" + storeId, 30, TimeUnit.MINUTES);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "获取商家首页信息失败" + e.getMessage(), null);
        }

    }

    @Override
    public ServiceStatusInfo<SuperStar> superStar(long storeId) {
        try {
            SuperStar result = this.nearbyShopsMapper.searchSuperStar(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询代言人失败" + e.getMessage(), null);
        }
    }
}
