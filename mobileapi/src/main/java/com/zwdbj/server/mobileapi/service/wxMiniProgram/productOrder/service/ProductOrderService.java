package com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.service;

import com.ecwid.consul.v1.ConsulClient;
import com.zwdbj.server.mobileapi.service.userAssets.service.UserAssetServiceImpl;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service.ProductService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.mapper.IProductOrderMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model.AddOrderInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.model.OrderOut;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.model.ProductSKUs;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.service.ProductSKUsService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.service.ReceiveAddressService;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.consulLock.unit.Lock;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductOrderService {
    @Autowired
    private IProductOrderMapper productOrderMapper;
    @Autowired
    private UserAssetServiceImpl userAssetServiceImpl;
    @Autowired
    private ProductService productServiceImpl;
    @Autowired
    private ProductSKUsService productSKUsServiceImpl;
    @Autowired
    private ReceiveAddressService receiveAddressServiceImpl;


    @Transactional
    public ServiceStatusInfo<Integer> createOrder(AddOrderInput input) {
        String key = String.valueOf(input.getProductskuId());
        ConsulClient consulClient = new ConsulClient("localhost", 8500);    // 创建与Consul的连接
        Lock lock = new Lock(consulClient, "mobileapi", "productOrder:" + key);
        try {
            if (lock.lock(true, 500L, 2)) {
                long userId = JWTUtil.getCurrentId();
                //查看此商品的sku信息
                ProductSKUs productSKUs =  this.productSKUsServiceImpl.selectById(input.getProductskuId()).getData();
                if (productSKUs==null)return new ServiceStatusInfo<>(1, "兑换失败", 0);

                //如果有限购，则要查看订单表，看兑换次数是否已经用完
                if (input.getLimitPerPerson() != 0) {
                    int account = this.productOrderMapper.userBuyProductAccounts(userId, input.getProductId());
                    if (account >= input.getLimitPerPerson()) {
                        return new ServiceStatusInfo<>(1, "您只能兑换此商品" + input.getLimitPerPerson() + "个", null);
                    } else if ((account + input.getNum()) > input.getLimitPerPerson()) {
                        return new ServiceStatusInfo<>(1, "您只能兑换此商品" + input.getLimitPerPerson() + "个", null);
                    }

                }
                //查看商品的库存是否满足
                long inventoryNum = this.productServiceImpl.getProductInventoryNum(input.getProductId());
                if (inventoryNum!=-10000L){
                    boolean a = this.productServiceImpl.getProductInventory(input.getProductId(), input.getProductskuId(), input.getNum()).getData();
                    if (!a) return new ServiceStatusInfo<>(1, "商品库存不足", null);
                }

                long orderId = UniqueIDCreater.generateID();

                //查看user的账户小饼干是否够支付订单费用
                this.userAssetServiceImpl.userIsExist(userId);
                //用户小饼干总数
                long counts = userAssetServiceImpl.getCoinsByUserId().getData();
                if (counts < 0 || counts < input.getUseCoin() || counts<productSKUs.getPromotionPrice()/10) {
                    return new ServiceStatusInfo<>(1, "您的小饼干不足，请获取足够的小饼干", null);
                }
                //创建order
                int payment = (int)productSKUs.getPromotionPrice();
                ReceiveAddressModel addressModel = this.receiveAddressServiceImpl.findById(input.getReceiveAddressId()).getData();
                this.productOrderMapper.createOrder(orderId, userId, input, payment,addressModel.getReceiverAddress(),
                        addressModel.getReceiverName(),addressModel.getReceiverMobile());
                //创建OrderItem
                long orderItemId = UniqueIDCreater.generateID();
                int price = (int)productSKUs.getPromotionPrice();
                int totalFee = price * input.getNum();
                this.productOrderMapper.createOrderItem(orderItemId, orderId, input, price, totalFee);
                // 减去商品和sku的库存并更新销量
                if (inventoryNum!=-10000L)
                this.productServiceImpl.updateProductNum(input.getProductId(), input.getProductskuId(), input.getNum());
                //兑换后减去用户所需的小饼干
                boolean flag = this.userAssetServiceImpl.minusUserCoins(totalFee/10, userId, orderId);
                if (!flag) return new ServiceStatusInfo<>(1, "兑换失败", 0);
                return new ServiceStatusInfo<>(0, "兑换成功", 1);
            }

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "兑换失败" + e.getMessage(), 0);
        }finally {
            lock.unlock();
        }
        return new ServiceStatusInfo<>(1, "兑换失败" , 0);
    }

    /**
     *
     */

    /**
     * 查询我的兑换
     *
     * @return
     */
    public ServiceStatusInfo<List<OrderOut>> findByMyOrder() {
        long userId = JWTUtil.getCurrentId();
        try {
            List<OrderOut> list = productOrderMapper.selectMyOrder(userId);
            return new ServiceStatusInfo<>(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }
}
