package com.zwdbj.server.shopservice.product.service;

import com.zwdbj.server.shopservice.product.model.Products;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;

public interface ProductService {
    ServiceStatusInfo<Long> createProducts(Products products);

    ServiceStatusInfo<Long> deleteProductsById(Long id);

    ServiceStatusInfo<Long> updateProducts(Products products);

    ServiceStatusInfo<List<Products>> selectAll(int currentPage,int pageSize);
}
