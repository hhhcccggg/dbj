package com.zwdbj.server.shop_admin_service.products.service;


import com.zwdbj.server.shop_admin_service.products.model.Products;
import com.zwdbj.server.shop_admin_service.products.model.SearchProducts;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ProductService {
    ServiceStatusInfo<Long> createProducts(Products products);

    ServiceStatusInfo<Long> deleteProductsById(Long id);

    ServiceStatusInfo<Long> updateProducts(Products products);

    ServiceStatusInfo<List<Products>> selectAll();
    ServiceStatusInfo<List<Products>> searchProducts(SearchProducts searchProduct);

}
