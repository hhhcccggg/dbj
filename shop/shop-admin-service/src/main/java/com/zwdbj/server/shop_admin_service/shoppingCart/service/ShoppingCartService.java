package com.zwdbj.server.shop_admin_service.shoppingCart.service;

import com.zwdbj.server.shop_admin_service.shoppingCart.model.ProductCartAddInput;
import com.zwdbj.server.shop_admin_service.shoppingCart.model.ProductCartModel;
import com.zwdbj.server.shop_admin_service.shoppingCart.model.ProductCartModifyInput;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ShoppingCartService {
    List<ProductCartModel> findAllShoppingCarts();
    ServiceStatusInfo<ProductCartModel> getShoppingCartById(long id);
    ServiceStatusInfo<Integer> addShoppingCart(ProductCartAddInput input);
    ServiceStatusInfo<Integer> modifyShoppingCart(long id, ProductCartModifyInput input);
    ServiceStatusInfo<Integer> notRealDeleteShoppingCart(long id);
}
