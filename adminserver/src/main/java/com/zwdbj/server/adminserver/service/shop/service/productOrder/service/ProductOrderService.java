package com.zwdbj.server.adminserver.service.shop.service.productOrder.service;

import com.zwdbj.server.adminserver.service.shop.service.productOrder.mapper.IProductOrderMapper;
import com.zwdbj.server.adminserver.service.shop.service.productOrder.model.*;
import com.zwdbj.server.adminserver.service.shop.service.products.service.ProductService;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.service.ReceiveAddressService;
import com.zwdbj.server.adminserver.service.shop.service.store.service.StoreService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.common.mq.DelayMQWorkSender;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.tokencenter.IAuthUserManager;
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
    @Autowired
    private IAuthUserManager authUserManagerImpl;
    @Autowired
    private StoreService storeServiceImpl;

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
            model.setNickName(this.userService.getNickNameById(model.getUserId()));
            return new ServiceStatusInfo<>(0,"",model);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1, "获得订单失败：" + e.getMessage(), null);
        }
    }
    public ServiceStatusInfo<ProductOrderDetailModel> getOrderById(long orderId){
        try {
            ProductOrderDetailModel model = this.productOrderMapper.getOrderById(orderId);
            model.setNickName(this.userService.getNickNameById(model.getUserId()));
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
            //设置延时收货时间
            QueueWorkInfoModel.QueueWorkOrderCommentTimeData orderCommentTimeData
                    = QueueWorkInfoModel.QueueWorkOrderCommentTimeData.newBuilder()
                    .setOrderId(orderId)
                    .setType(2)
                    .build();
            QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.USER_ORDER_COMMENT_TIME)
                    .setOrderCommentTimeData(orderCommentTimeData)
                    .build();
            DelayMQWorkSender.shareSender().send(workInfo,60*60*24*15);
            return  new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"发货失败:"+e.getMessage(),0);
        }
    }

    /**
     * 延时确认收货
     * @param orderId
     * @return
     */
    public ServiceStatusInfo<Integer> deliverOrderByUser(long orderId){
        try {
            int result = this.productOrderMapper.deliverOrderByUser(orderId);
            if (result==0)return new ServiceStatusInfo<>(1,"收货失败",0);
            return  new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"收货失败:"+e.getMessage(),0);
        }
    }

    @Transactional
    public  ServiceStatusInfo<Integer> identifyingCode(long orderId, IdentifyCodeInput input){
        try {
            ProductOrderDetailModel model = this.getOrderById(orderId).getData();
            if (model==null)return new ServiceStatusInfo<>(1,"没有此订单",0);
            String code = input.getIdentifyCode();
            String verifyCode;
            if (this.stringRedisTemplate.hasKey("orderIdVerifyCode:"+orderId)){
                verifyCode = this.stringRedisTemplate.opsForValue().get("orderIdVerifyCode:"+orderId);
            }else {
                verifyCode = this.productOrderMapper.getVerifyCode(orderId);
            }
            if (!code.equals(verifyCode))return new ServiceStatusInfo<>(1,"验证消费码不正确",0);
            //验证成功后更新订单
            long userId = JWTUtil.getCurrentId();
            AuthUser authUser = authUserManagerImpl.get(String.valueOf(userId));
            if (authUser.getLegalSubjectId()<0)return new ServiceStatusInfo<>(1,"未知正确",0);
            long storeId = model.getStoreId();
            long aStoreId = this.storeServiceImpl.selectByLegalSubjectId(authUser.getLegalSubjectId()).getData().getId();
            if (storeId!=aStoreId)return new ServiceStatusInfo<>(1,"未知错误",0);
            int result = this.productOrderMapper.updateOrderSuccess(orderId);
            if (result==0)return new ServiceStatusInfo<>(1,"验证消费码失败",0);
            //设置订单评价过期机制
            QueueWorkInfoModel.QueueWorkOrderCommentTimeData orderCommentTimeData
                    = QueueWorkInfoModel.QueueWorkOrderCommentTimeData.newBuilder()
                    .setOrderId(orderId)
                    .setType(1)
                    .build();
            QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.USER_ORDER_COMMENT_TIME)
                    .setOrderCommentTimeData(orderCommentTimeData)
                    .build();
            DelayMQWorkSender.shareSender().send(workInfo,60*60*24*30);
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

        return true;


    }
    public boolean orderUnComment(long orderId){
        try {
            ProductOrderDetailModel model = this.getOrderById(orderId).getData();
            if (model.getStatus().equals("STATE_USED")){
                this.productOrderMapper.updateOrderUnComment(orderId);
                logger.info("未评价订单:"+orderId+"更新成功");
                return true;
            }else {
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
            logger.error("更改未评价订单:"+orderId+"异常:"+e.getMessage());
        }

        return true;


    }
}
