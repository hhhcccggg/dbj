package com.zwdbj.server.mobileapi.service.wxMiniProgram.productCard.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCard.mapper.IProductCardMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCard.model.ProductCard;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ProductCardServiceImpl implements ProductCardService {

    @Resource
    private IProductCardMapper iProductCardMapper;

    @Override
    public ProductCard selectByProductId(long productId) {
        return iProductCardMapper.selectByProductId(productId);
    }
}
