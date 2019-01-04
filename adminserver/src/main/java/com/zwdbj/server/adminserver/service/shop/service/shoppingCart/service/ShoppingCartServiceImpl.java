package com.zwdbj.server.adminserver.service.shop.service.shoppingCart.service;

import com.zwdbj.server.adminserver.service.shop.service.shoppingCart.mapper.IShoppingCartMapper;
import com.zwdbj.server.adminserver.service.shop.service.shoppingCart.model.ProductCartAddInput;
import com.zwdbj.server.adminserver.service.shop.service.shoppingCart.model.ProductCartModel;
import com.zwdbj.server.adminserver.service.shop.service.shoppingCart.model.ProductCartModifyInput;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Resource
    IShoppingCartMapper shoppingCartMapper;

    @Override
    public List<ProductCartModel> findAllShoppingCarts() {
        List<ProductCartModel> shoppingCartModels = this.shoppingCartMapper.findAllShoppingCarts();
        return shoppingCartModels;
    }

    @Override
    public ServiceStatusInfo<ProductCartModel> getShoppingCartById(long id) {
        try {
            ProductCartModel shoppingCartModel = this.shoppingCartMapper.getShoppingCartById(id);
            return new ServiceStatusInfo<>(0,"获取购物车详情成功",shoppingCartModel);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"获取购物车详情失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> notRealDeleteShoppingCart(long id) {
        try {
            int result = this.shoppingCartMapper.notRealDeleteShoppingCart(id);
            if (result==0) return new ServiceStatusInfo<>(1,"删除购物车失败",result);
            return new ServiceStatusInfo<>(0,"删除购物车成功",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"删除购物车失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> addShoppingCart(ProductCartAddInput input) {
        try {
            long id = UniqueIDCreater.generateID();
            int result = this.shoppingCartMapper.addShoppingCart(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"添加新的购物车失败",result);
            return new ServiceStatusInfo<>(0,"添加新的购物车成功",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"添加新的购物车失败",null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> modifyShoppingCart(long id, ProductCartModifyInput input) {
        try {
            int result = this.shoppingCartMapper.modifyShoppingCart(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"修改购物车失败",result);
            return new ServiceStatusInfo<>(0,"修改购物车成功",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"修改购物车失败",null);
        }
    }
}
