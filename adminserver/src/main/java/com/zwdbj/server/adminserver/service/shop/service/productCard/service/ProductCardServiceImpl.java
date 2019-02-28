package com.zwdbj.server.adminserver.service.shop.service.productCard.service;

import com.zwdbj.server.adminserver.service.shop.service.productCard.mapper.IProductCardMapper;
import com.zwdbj.server.adminserver.service.shop.service.productCard.model.ProductCard;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductCardServiceImpl implements ProductCardService {

    @Resource
    private IProductCardMapper iProductCardMapper;

    @Override
    public ServiceStatusInfo<Long> create(ProductCard productCard) {
        try {
            Long result = this.iProductCardMapper.createProductCard(UniqueIDCreater.generateID(),productCard);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "新增失败" + e.getMessage(), 0L);
        }
    }

}
