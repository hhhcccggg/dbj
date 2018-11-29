package com.zwdbj.server.shop_admin_service.products.service;


import com.zwdbj.server.shop_admin_service.products.model.Products;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ProductService {
    ServiceStatusInfo<Long> createProducts(Products products);

    ServiceStatusInfo<Long> deleteProductsById(Products products);

    ServiceStatusInfo<Long> updateProducts(Products products);

    ServiceStatusInfo<List<Products>> selectAll(int currentPage, int pageSize);
}
