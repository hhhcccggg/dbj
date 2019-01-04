package com.zwdbj.server.adminserver.service.shop.service.productCard.service;

import com.zwdbj.server.adminserver.service.shop.service.productCard.model.ProductCard;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

public interface ProductCardService {

    /**
     * 新增
     * @param productCard
     * @return
     */
    ServiceStatusInfo<Long> create(ProductCard productCard);
}
