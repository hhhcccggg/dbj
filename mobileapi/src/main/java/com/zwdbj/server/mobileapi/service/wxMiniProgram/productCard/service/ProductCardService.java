package com.zwdbj.server.mobileapi.service.wxMiniProgram.productCard.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCard.model.ProductCard;

public interface ProductCardService {

    /**
     * 根据productId查询
     * @param productId
     * @return
     */
    ProductCard selectByProductId(long productId);
}
