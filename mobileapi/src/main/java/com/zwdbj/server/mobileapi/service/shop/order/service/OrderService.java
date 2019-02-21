package com.zwdbj.server.mobileapi.service.shop.order.service;

import com.ecwid.consul.v1.ConsulClient;
import com.zwdbj.server.mobileapi.service.shop.order.mapper.IOrderMapper;
import com.zwdbj.server.mobileapi.service.shop.order.model.AddNewOrderInput;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderDetailModel;
import com.zwdbj.server.mobileapi.service.shop.order.model.ProductOrderModel;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service.ProductService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model.AddOrderInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.model.ProductSKUs;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.service.ProductSKUsService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.service.ReceiveAddressService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.userDiscountCoupon.service.UserDiscountCouponService;
import com.zwdbj.server.pay.settlement.protocol.Coupon;
import com.zwdbj.server.pay.settlement.protocol.ISettlement;
import com.zwdbj.server.pay.settlement.protocol.SettlementResult;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.consulLock.unit.Lock;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    @Autowired
    private ISettlement settlement;
    @Autowired
    private UserDiscountCouponService userDiscountCouponServiceImpl;
    @Autowired
    private ProductSKUsService productSKUsServiceImpl;
    private Logger logger = LoggerFactory.getLogger(OrderService.class);

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
    public ServiceStatusInfo<Long> createOrder(AddNewOrderInput input){
        String key = String.valueOf(input.getProductskuId());
        ConsulClient consulClient = new ConsulClient("localhost", 8500);    // 创建与Consul的连接
        Lock lock = new Lock(consulClient, "mobileapi", "productOrder:" + key);
        try {
            if (lock.lock(true, 500L, 3)){
                // TODO 考虑加锁
                long userId = JWTUtil.getCurrentId();
                //如果有限购，则要查看订单表，看兑换次数是否已经用完
                //查看此商品的sku信息
                ProductSKUs productSKUs =  this.productSKUsServiceImpl.selectById(input.getProductskuId()).getData();
                if (productSKUs==null)return new ServiceStatusInfo<>(1, "购买失败", null);
                if (input.getLimitPerPerson()!=0){
                    int account = this.orderMapper.userBuyProductAccounts(userId,input.getProductId());
                    if (account>=input.getLimitPerPerson()){
                        return new ServiceStatusInfo<>(1, "您只能购买此商品"+input.getLimitPerPerson()+"个", null);
                    }else if ((account+input.getNum())>input.getLimitPerPerson()){
                        return new ServiceStatusInfo<>(1, "您只能购买此商品"+input.getLimitPerPerson()+"个", null);
                    }

                }
                //查看商品的库存是否满足
                long inventoryNum = this.productServiceImpl.getProductInventoryNum(input.getProductId());
                if (inventoryNum!=-10000L){
                    boolean a = this.productServiceImpl.getProductInventory(input.getProductId(),input.getProductskuId(),input.getNum()).getData();
                    if (!a)return new ServiceStatusInfo<>(1, "商品库存不足", null);
                }

                long orderId = UniqueIDCreater.generateID();
                if (input.getUseCoin()!=0){
                    //查看user的账户小饼干是否够支付订单费用
                    this.userAssetServiceImpl.userIsExist(userId);
                    //用户小饼干总数
                    long counts = userAssetServiceImpl.getCoinsByUserId().getData();
                    if (counts<0 || counts<input.getUseCoin()){
                        return new ServiceStatusInfo<>(1, "您的小饼干不足，请获取足够的小饼干", null);
                    }
                }


                //创建order
                int payment = (int)productSKUs.getPromotionPrice()*input.getNum()+input.getDeliveryFee();
                this.orderMapper.createOrder(orderId,userId,input,payment);
                //创建OrderItem
                long orderItemId = UniqueIDCreater.generateID();
                int price = (int)productSKUs.getPromotionPrice();
                int totalFee = price*input.getNum();
                this.orderMapper.createOrderItem(orderItemId,orderId,input,price,totalFee);
                // 减去商品和sku的库存并更新销量
                if (inventoryNum!=-10000L)
                this.productServiceImpl.updateProductNum(input.getProductId(),input.getProductskuId(),input.getNum());
                //设置订单过期机制
                /*QueueWorkInfoModel.QueueWorkOrderTimeData orderTimeData
                        = QueueWorkInfoModel.QueueWorkOrderTimeData.newBuilder()
                        .setOrderId(orderId)
                        .setUserId(userId)
                        .build();
                QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                        .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.USER_ORDER_TIME)
                        .setOrderTimeData(orderTimeData)
                        .build();
                DelayMQWorkSender.shareSender().send(workInfo,30);*/

                return new ServiceStatusInfo<>(0,"下单成功",orderId);
            }


        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"下单失败"+e.getMessage(),null);
        }finally {
            lock.unlock();
        }

        return new ServiceStatusInfo<>(1,"下单失败",null);
    }

    @Transactional
    public void updateOrderPay(long id,String paymentType,String tradeNo,String thirdPaymentTradeNotes){
        ProductOrderDetailModel model = this.getOrderById(id).getData();
        long userId = JWTUtil.getCurrentId();
        if (model==null)return;
        if (!model.getStatus().equals("STATE_WAIT_BUYER_PAY"))return;
        if (model.getUseCoin()!=0){
            //处理金币
            this.userAssetServiceImpl.minusUserCoins(model.getUseCoin(),userId,id);
        }
        String coupons = model.getCouponids();
        if (coupons!=null && (!coupons.equals("") )){
            String[] couponIds = coupons.split(",");
            for (String coupon:couponIds){
                long couponId = Long.valueOf(coupon);
                if (couponId==0)continue;
                //更新优惠券的状态
                this.userDiscountCouponServiceImpl.updateUserDiscountCouponState(userId,couponId);
            }
        }
        this.orderMapper.updateOrderPay(id,paymentType,tradeNo,thirdPaymentTradeNotes);
    }
    @Transactional
    public void updateOrderState(long id,String tradeNo,String status){
        ProductOrderDetailModel model = this.getOrderById(id).getData();
        if (status.equals("STATE_REFUNDING")){
            if (model.getStatus().equals("STATE_BUYER_PAYED") || model.getStatus().equals("STATE_SELLER_DELIVERIED")
                    || model.getStatus().equals("STATE_UNUSED")){
                this.orderMapper.updateOrderState(id,tradeNo,status);
            }
        }else if (status.equals("STATE_REFUND_SUCCESS")){
            if (model.getStatus().equals("STATE_BUYER_PAYED") || model.getStatus().equals("STATE_SELLER_DELIVERIED")
                    || model.getStatus().equals("STATE_REFUNDING") || model.getStatus().equals("STATE_UNUSED")){
                this.orderMapper.updateOrderState(id,tradeNo,status);
                //更新商品的库存  是否有使用金币抵扣
            }
        }

    }

    @Transactional
    public ServiceStatusInfo<Integer> takeOverGoods(long orderId,long userId){
            int result = this.orderMapper.takeOverGoods(orderId,userId);
            if (result==0)return new ServiceStatusInfo<>(1,"确认收货失败",result);
            return new ServiceStatusInfo<>(0,"确认收货成功",result);
    }

    public ServiceStatusInfo<SettlementResult> settlementOrder(int amount, long coins, Coupon coupon){
        long allCoins = this.userAssetServiceImpl.getCoinsByUserId().getData();
        if (allCoins<coins)return new ServiceStatusInfo<>(1,"你的小饼干不够",null);
        int userCoins = (int)coins;
        SettlementResult settlementResult = this.settlement.settlement(amount,0L,userCoins,coupon);
        return new ServiceStatusInfo<>(0,"",settlementResult);

    }
}
