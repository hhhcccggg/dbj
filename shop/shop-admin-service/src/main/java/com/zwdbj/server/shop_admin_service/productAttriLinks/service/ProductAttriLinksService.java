package com.zwdbj.server.shop_admin_service.productAttriLinks.service;

import com.zwdbj.server.shop_admin_service.productAttriLinks.model.ProductAttriLinks;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ProductAttriLinksService {
    ServiceStatusInfo<Long> updateProductAttriLinks(ProductAttriLinks productAttriLinks);

    ServiceStatusInfo<Long> createProductAttriLinks(ProductAttriLinks productAttriLinks);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<List<ProductAttriLinks>> select();


}
