package com.zwdbj.server.adminserver.service.shop.service.productOrder.service;

import com.zwdbj.server.adminserver.service.shop.service.productOrder.mapper.IProductOrderMapper;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.*;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.service.ReceiveAddressService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductOrderService {

    @Autowired
    IProductOrderMapper productOrderMapper;
    @Autowired
    UserService userService;
    @Autowired
    ReceiveAddressService receiveAddressServiceImpl;
    @Autowired
    TokenCenterManager tokenCenterManager;

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

    public ServiceStatusInfo<Integer> deliverOrder(long orderId, DeliverOrderInput input){
        try {
            long userId = JWTUtil.getCurrentId();
            AuthUser authUser = this.tokenCenterManager.fetchUser(String.valueOf(userId)).getData();
            //验证是否为本店铺操作
            long storeId = 0L;
            int result = this.productOrderMapper.deliverOrder(orderId,input,storeId);
            if (result==0)return new ServiceStatusInfo<>(1,"发货失败",0);
            return  new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"发货失败:"+e.getMessage(),0);
        }
    }

    @Transactional
    public  ServiceStatusInfo<Integer> identifyingCode(long orderId, IdentifyCodeInput input){
        try {
            String code = input.getIdentifyCode();
            // TODO 从哪里去验证此码
            //验证成功后更新订单
            int result = this.productOrderMapper.updateOrderSuccess(orderId);
            if (result==0)return new ServiceStatusInfo<>(1,"验证消费码失败",0);
            return  new ServiceStatusInfo<>(0,"验证消费码成功",result);

        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"验证消费码失败:"+e.getMessage(),0);
        }
    }
}
