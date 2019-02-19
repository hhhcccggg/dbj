package com.zwdbj.server.mobileapi.service.shop.shoppingcart.service;

import com.zwdbj.server.mobileapi.service.shop.shoppingcart.model.ProductInfo;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ShoppingCartService {
    ServiceStatusInfo<Long> add(HttpServletRequest request,
                                HttpServletResponse response,
                                ProductInfo productInfo);

    ServiceStatusInfo<List<ProductInfo>> getShoppingCart(HttpServletRequest request, HttpServletResponse response);

    ServiceStatusInfo<List<ProductInfo>> modifyShoppingCart(HttpServletRequest request, HttpServletResponse response, ProductInfo p);

    ServiceStatusInfo<List<ProductInfo>> deleteShoppingCart(HttpServletRequest request, HttpServletResponse response, ProductInfo p);

}
