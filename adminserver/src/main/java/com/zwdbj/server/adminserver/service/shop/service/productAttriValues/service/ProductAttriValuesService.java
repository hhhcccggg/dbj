package com.zwdbj.server.adminserver.service.shop.service.productAttriValues.service;


import com.zwdbj.server.adminserver.service.shop.service.productAttriValues.model.ProductAttriValues;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ProductAttriValuesService {

    ServiceStatusInfo<Long> createProductAttriValues(ProductAttriValues productAttriValues);

    ServiceStatusInfo<Long> deleteById(Long id);

    ServiceStatusInfo<Long> updateProductAttriValues(ProductAttriValues productAttriValues);

    ServiceStatusInfo<List<ProductAttriValues>> select();


}
