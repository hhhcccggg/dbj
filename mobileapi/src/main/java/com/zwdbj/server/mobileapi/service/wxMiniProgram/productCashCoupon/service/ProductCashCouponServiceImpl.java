package com.zwdbj.server.mobileapi.service.wxMiniProgram.productCashCoupon.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCashCoupon.mapper.IProductCashCouponMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCashCoupon.model.ProductCashCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCashCouponServiceImpl implements ProductCashCouponService{

    @Autowired
    private IProductCashCouponMapper iProductCashCouponMapper;

    @Override
    public ProductCashCoupon selectByProductId(long productId) {
        return iProductCashCouponMapper.selectByProductId(productId);
    }
}
