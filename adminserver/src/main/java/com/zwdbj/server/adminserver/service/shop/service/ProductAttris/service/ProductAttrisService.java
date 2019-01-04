package com.zwdbj.server.adminserver.service.shop.service.ProductAttris.service;

import com.zwdbj.server.adminserver.service.shop.service.ProductAttris.model.ProductAttris;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ProductAttrisService {
    ServiceStatusInfo<Long> createProductAttris(ProductAttris productAttris);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<Long> updateProductAttris(ProductAttris productAttris);

    ServiceStatusInfo<List<ProductAttris>> select();


}
