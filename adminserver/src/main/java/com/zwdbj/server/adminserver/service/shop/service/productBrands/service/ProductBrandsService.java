package com.zwdbj.server.adminserver.service.shop.service.productBrands.service;


import com.zwdbj.server.adminserver.service.shop.service.productBrands.model.ProductBrands;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ProductBrandsService {
    ServiceStatusInfo<Long> createProductBrands(ProductBrands productBrands);

    ServiceStatusInfo<Long> updateProductBrands(ProductBrands productBrands);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<List<ProductBrands>> selectAll();
}
