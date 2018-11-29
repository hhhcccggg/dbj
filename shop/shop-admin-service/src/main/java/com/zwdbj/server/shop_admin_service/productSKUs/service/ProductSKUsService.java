package com.zwdbj.server.shop_admin_service.productSKUs.service;

import com.zwdbj.server.shop_admin_service.productSKUs.model.ProductSKUs;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ProductSKUsService {
    ServiceStatusInfo<Long> createProductSKUs(ProductSKUs productSKUs);
    ServiceStatusInfo<Long> deleteById(ProductSKUs productSKUs);

    ServiceStatusInfo<Long> updateProductSKUs(ProductSKUs productSKUs);

    ServiceStatusInfo<List<ProductSKUs>> selectAll();
}
