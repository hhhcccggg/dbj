package com.zwdbj.server.shop_admin_service.service.productCard.service;

import com.zwdbj.server.shop_admin_service.service.productCard.model.ProductCard;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

public interface ProductCardService {

    /**
     * 新增
     * @param productCard
     * @return
     */
    ServiceStatusInfo<Long> create(ProductCard productCard);

    /**
     * 修改
     * @param productCard
     * @return
     */
    ServiceStatusInfo<Long> update(ProductCard productCard);
}
