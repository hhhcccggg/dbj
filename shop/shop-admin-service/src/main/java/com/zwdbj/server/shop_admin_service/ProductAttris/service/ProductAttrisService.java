package com.zwdbj.server.shop_admin_service.ProductAttris.service;

import com.zwdbj.server.shop_admin_service.ProductAttris.model.ProductAttris;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ProductAttrisService {
    ServiceStatusInfo<Long> createProductAttris(ProductAttris productAttris);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<Long> updateProductAttris(ProductAttris productAttris);

    ServiceStatusInfo<List<ProductAttris>> select();


}
