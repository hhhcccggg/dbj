package com.zwdbj.server.adminserver.service.shop.service.productOrder.service;

import com.zwdbj.server.adminserver.service.shop.service.productOrder.mapper.IProductOrderMapper;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.*;
import com.zwdbj.server.adminserver.service.shop.service.products.service.ProductService;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.service.ReceiveAddressService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProductService productServiceImpl;

    private Logger logger = LoggerFactory.getLogger(ProductOrderService.class);

    public List<ProductOrderModel> getStoreOrders(long storeId, ProductOrderInput input){
        try {
            List<ProductOrderModel> orderModels = this.productOrderMapper.getStoreOrders(storeId,input);
            /*for (ProductOrderModel model:orderModels){
                model.setNickName(this.userService.getNickNameById(model.getUserId()));
            }*/
            return orderModels;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public ServiceStatusInfo<ProductOrderDetailModel> getOrderByOrderNo(String orderNo){
        try {
            ProductOrderDetailModel model = this.productOrderMapper.getOrderByOrderNo(orderNo);
            ReceiveAddressModel addressModel = this.receiveAddressServiceImpl.getReceiveAddressById(model.getReceiveAddressId()).getData();
            model.setNickName(this.userService.getNickNameById(model.getUserId()));
            model.setAddressModel(addressModel);
            return new ServiceStatusInfo<>(0,"",model);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "获得订单失败：" + e.getMessage(), null);
        }
    }
    public ServiceStatusInfo<ProductOrderDetailModel> getOrderById(long orderId){
        try {
            ProductOrderDetailModel model = this.productOrderMapper.getOrderById(orderId);
            ReceiveAddressModel addressModel = this.receiveAddressServiceImpl.getReceiveAddressById(model.getReceiveAddressId()).getData();
            model.setNickName(this.userService.getNickNameById(model.getUserId()));
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

    /**
     * 用户确认收货
     * @param orderId
     * @param userId
     * @return
     */
    public ServiceStatusInfo<Integer> deliverOrderByUser(long orderId,long userId){
        try {
            long userId2 = JWTUtil.getCurrentId();
            if (userId2<=0)return new ServiceStatusInfo<>(1,"请重新登录",null);
            if (userId!=userId2)return new ServiceStatusInfo<>(1,"账号有误",null);
            int result = this.productOrderMapper.deliverOrderByUser(orderId,userId2);
            if (result==0)return new ServiceStatusInfo<>(1,"收货失败",0);
            return  new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"收货失败:"+e.getMessage(),0);
        }
    }

    @Transactional
    public  ServiceStatusInfo<Integer> identifyingCode(long orderId, IdentifyCodeInput input){
        try {
            String code = input.getIdentifyCode();
            String verifyCode;
            if (this.stringRedisTemplate.hasKey("orderIdVerifyCode:"+orderId)){
                verifyCode = this.stringRedisTemplate.opsForValue().get("orderIdVerifyCode:"+orderId);
            }else {
                verifyCode = this.productOrderMapper.getVerifyCode(orderId);
            }
            if (!code.equals(verifyCode))return new ServiceStatusInfo<>(1,"验证消费码不正确",0);
            //验证成功后更新订单
            int result = this.productOrderMapper.updateOrderSuccess(orderId);
            if (result==0)return new ServiceStatusInfo<>(1,"验证消费码失败",0);
            return  new ServiceStatusInfo<>(0,"验证消费码成功",result);

        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"验证消费码失败:"+e.getMessage(),0);
        }
    }

    public boolean orderUnPay(long orderId,long userId){
        try {
            ProductOrderDetailModel model = this.getOrderById(orderId).getData();
            if (model.getStatus().equals("STATE_WAIT_BUYER_PAY")){
                int result = this.productOrderMapper.updateOrderUnPay(orderId,userId);
                //增加商品的库存
                if (result==0)return false;
                logger.info("未支付订单:"+orderId+"更新成功");
                long inventoryNum = this.productServiceImpl.getProductInventoryNum(model.getProductId());
                if (inventoryNum!=-10000L)
                    this.productServiceImpl.updateProductNum(model.getProductId(),model.getProductskuId(),-model.getNum());
                return true;
            }else {
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("更改未支付订单:"+orderId+"异常:"+e.getMessage());
        }

        return false;


    }
}
