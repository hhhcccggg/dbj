package com.zwdbj.server.mobileapi.service.shop.order.service;

import com.zwdbj.server.mobileapi.service.shop.order.mapper.IOrderMapper;
import com.zwdbj.server.mobileapi.service.shop.order.model.AddNewOrderInput;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderDetailModel;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderModel;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service.ProductService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model.AddOrderInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.service.ReceiveAddressService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    IOrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    private ReceiveAddressService receiveAddressServiceImpl;
    @Autowired
    private UserAssetServiceImpl userAssetServiceImpl;
    @Autowired
    private ProductService productServiceImpl;

    public List<ProductOrderModel> getMyOrders(int status){
        try {
            long userId = JWTUtil.getCurrentId();
            List<ProductOrderModel> orderModels = this.orderMapper.getMyOrders(userId,status);
            for (ProductOrderModel model:orderModels){
                model.setNickName(this.userService.getUserDetail(userId).getNickName());
                ReceiveAddressModel addressModel = this.receiveAddressServiceImpl.findById(model.getReceiveAddressId()).getData();
                if (addressModel!=null)
                    model.setAddressModel(addressModel);
            }
            return orderModels;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public ServiceStatusInfo<ProductOrderDetailModel> getOrderById(long orderId){
        try {
            ProductOrderDetailModel model = this.orderMapper.getOrderById(orderId);
            ReceiveAddressModel addressModel = this.receiveAddressServiceImpl.findById(model.getReceiveAddressId()).getData();
            model.setNickName(this.userService.getUserDetail(model.getUserId()).getNickName());
            model.setAddressModel(addressModel);
            return new ServiceStatusInfo<>(0,"",model);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "获得订单失败：" + e.getMessage(), null);
        }
    }

    /*public  ServiceStatusInfo<Boolean> canBuyNum(){
        //boolean a = this.productServiceImpl.getProductInventory(input.getProductId(),input.getProductskuId(),input.getNum()).getData();
    }*/

    @Transactional
    public ServiceStatusInfo<Integer> createOrder(AddNewOrderInput input){
        try {
            // TODO 考虑加锁
            long userId = JWTUtil.getCurrentId();
            //如果有限购，则要查看订单表，看兑换次数是否已经用完
            if (input.getLimitPerPerson()!=0){
                int account = this.orderMapper.userBuyProductAccounts(userId,input.getProductId());
                if (account>=input.getLimitPerPerson()){
                    return new ServiceStatusInfo<>(1, "您只能购买此商品"+input.getLimitPerPerson()+"个", 0);
                }else if ((account+input.getNum())>input.getLimitPerPerson()){
                    return new ServiceStatusInfo<>(1, "您只能购买此商品"+input.getLimitPerPerson()+"个", 0);
                }

            }
            //查看商品的库存是否满足
            boolean a = this.productServiceImpl.getProductInventory(input.getProductId(),input.getProductskuId(),input.getNum()).getData();
            if (!a)return new ServiceStatusInfo<>(1, "商品库存不足", 0);
            long orderId = UniqueIDCreater.generateID();
            if (input.getUseCoin()!=0){
                //查看user的账户小饼干是否够支付订单费用
                this.userAssetServiceImpl.userIsExist(userId);
                //用户小饼干总数
                long counts = userAssetServiceImpl.getCoinsByUserId().getData();
                if (counts<0 || counts<input.getUseCoin()){
                    return new ServiceStatusInfo<>(1, "您的小饼干不足，请获取足够的小饼干", 0);
                }
            }


            //创建order
            int payment = input.getPrice()*input.getNum()+input.getDeliveryFee();
            this.orderMapper.createOrder(orderId,userId,input,payment);
            //创建OrderItem
            long orderItemId = UniqueIDCreater.generateID();
            int price = input.getPrice();
            int totalFee = price*input.getNum();
            this.orderMapper.createOrderItem(orderItemId,orderId,input,price,totalFee);
            // 减去商品和sku的库存并更新销量
            this.productServiceImpl.updateProductNum(input.getProductId(),input.getProductskuId(),input.getNum());
            //兑换后减去用户所需的小饼干
            boolean flag = this.userAssetServiceImpl.minusUserCoins(input.getUseCoin(),userId,orderId);
            if (!flag)return new ServiceStatusInfo<>(1,"下单失败",0);
            // TODO 优惠券的使用
            return new ServiceStatusInfo<>(0,"下单成功",1);

        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"下单失败"+e.getMessage(),0);
        }

    }
}
