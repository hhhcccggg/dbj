package com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.model.ProductSKUs;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;


public interface ProductSKUsService {
    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ServiceStatusInfo<ProductSKUs> selectById(Long id);

    /**
     * 根据商品ID查询
     * @param productId
     * @return
     */
    ServiceStatusInfo<ProductSKUs> selectByProductId(Long productId);
}
