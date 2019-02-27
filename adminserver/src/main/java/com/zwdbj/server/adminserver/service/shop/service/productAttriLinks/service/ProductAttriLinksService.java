package com.zwdbj.server.adminserver.service.shop.service.productAttriLinks.service;

import com.zwdbj.server.adminserver.service.shop.service.productAttriLinks.model.ProductAttriLinks;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface ProductAttriLinksService {
    ServiceStatusInfo<Long> updateProductAttriLinks(ProductAttriLinks productAttriLinks);

    ServiceStatusInfo<Long> createProductAttriLinks(ProductAttriLinks productAttriLinks);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<List<ProductAttriLinks>> select();


}
