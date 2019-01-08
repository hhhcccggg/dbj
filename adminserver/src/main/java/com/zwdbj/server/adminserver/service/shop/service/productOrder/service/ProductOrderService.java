package com.zwdbj.server.adminserver.service.shop.service.productOrder.service;

import com.zwdbj.server.adminserver.service.shop.service.productOrder.mapper.IProductOrderMapper;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderDetailModel;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderInput;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.ProductOrderModel;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.service.ReceiveAddressService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOrderService {

    @Autowired
    IProductOrderMapper productOrderMapper;
    @Autowired
    UserService userService;
    @Autowired
    ReceiveAddressService receiveAddressServiceImpl;

    public List<ProductOrderModel> getStoreOrders(long storeId, ProductOrderInput input){
        try {
            List<ProductOrderModel> orderModels = this.productOrderMapper.getStoreOrders(storeId,input);
            for (ProductOrderModel model:orderModels){
                model.setNickName(this.userService.getUserDetail(model.getUserId()).getNickName());
                ReceiveAddressModel addressModel = this.receiveAddressServiceImpl.getReceiveAddressById(model.getReceiveAddressId()).getData();
                if (addressModel!=null)
                    model.setAddressModel(addressModel);
            }
            return orderModels;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public ServiceStatusInfo<ProductOrderDetailModel> getOrderById(long orderId){
        try {
            ProductOrderDetailModel model = this.productOrderMapper.getOrderById(orderId);
            ReceiveAddressModel addressModel = this.receiveAddressServiceImpl.getReceiveAddressById(model.getReceiveAddressId()).getData();
            model.setNickName(this.userService.getUserDetail(model.getUserId()).getNickName());
            model.setAddressModel(addressModel);
            return new ServiceStatusInfo<>(0,"",model);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "获得订单失败：" + e.getMessage(), null);
        }



    }
}
