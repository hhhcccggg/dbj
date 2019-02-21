package com.zwdbj.server.mobileapi.service.wxMiniProgram.productCashCoupon.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCashCoupon.model.ProductCashCoupon;

public interface ProductCashCouponService {

    /**
     * 根据商品ID查询
     * @param productId
     * @return
     */
    ProductCashCoupon selectByProductId(long productId);
}
