package com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.mapper.IProductOrderMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model.AddOrderInput;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductOrderService {
    @Autowired
    IProductOrderMapper productOrderMapper;

    @Transactional
    public ServiceStatusInfo<Integer> createOrder(AddOrderInput input){
        try {
            long orderId = UniqueIDCreater.generateID();
            long userId = JWTUtil.getCurrentId();
            //创建order
            int payment = input.getUseCoin()/10;
            this.productOrderMapper.createOrder(orderId,userId,input,payment);
            //创建OrderItem
            long OrderItemId = UniqueIDCreater.generateID();
            int price = input.getPrice_coin()/10;
            int totalFee = 0;
            this.productOrderMapper.createOrderItem(OrderItemId,orderId,input,price,totalFee);
            // TODO 减去商品和sku的库存,(input.get)
            return new ServiceStatusInfo<>(0,"下单成功",1);

        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"下单失败"+e.getMessage(),0);
        }

    }
}
