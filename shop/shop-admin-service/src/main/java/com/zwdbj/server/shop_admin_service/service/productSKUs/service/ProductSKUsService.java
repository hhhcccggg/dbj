package com.zwdbj.server.shop_admin_service.service.productSKUs.service;

import com.zwdbj.server.shop_admin_service.service.productSKUs.model.ProductSKUs;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ProductSKUsService {
    ServiceStatusInfo<Long> createProductSKUs(ProductSKUs productSKUs);
    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<Long> updateProductSKUs(ProductSKUs productSKUs);

    ServiceStatusInfo<List<ProductSKUs>> selectAll();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ServiceStatusInfo<ProductSKUs> selectById(Long id);
}
