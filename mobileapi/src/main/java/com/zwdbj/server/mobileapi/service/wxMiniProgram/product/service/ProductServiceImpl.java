package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service;

import com.zwdbj.server.mobileapi.config.MainKeyType;
import com.zwdbj.server.mobileapi.service.user.mapper.IUserMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.mapper.IProductMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductMainDto;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductlShow;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.*;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCard.model.ProductCard;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCard.service.ProductCardService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCashCoupon.model.ProductCashCoupon;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productCashCoupon.service.ProductCashCouponService;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.mapper.IProductOrderMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.model.ProductSKUs;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.service.ProductSKUsService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    protected IProductMapper iProductMapper;

    @Autowired
    private ProductCardService productCardServiceImpl;

    @Autowired
    private ProductCashCouponService productCashCouponServiceImpl;

    @Autowired
    protected IProductOrderMapper iProductOrderMapper;

    @Autowired
    private ProductSKUsService productSKUsServiceImpl;

    @Autowired
    protected IUserMapper iUserMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ServiceStatusInfo<List<ProductOut>> selectShopProduct(ProductInput productInput) {
        //TODO 后期可能会换成缓存
        try {
            long userId = JWTUtil.getCurrentId();
            List<ProductOut> list = this.iProductMapper.selectShopProduct(productInput);
            for (ProductOut productOut : list) {
                ProductSKUs productSKUs = productSKUsServiceImpl.selectByProductId(productOut.getId()).getData();
                if (productSKUs != null) {
                    productOut.setProductSKUId(productSKUs.getId());
                    productOut.setPromotionPrice(productSKUs.getPromotionPrice());
                    productOut.setOriginalPrice(productSKUs.getOriginalPrice());
                }
                productOut.setExchange(iProductOrderMapper.userBuyProductAccounts(userId, productOut.getId()));
            }
            return new ServiceStatusInfo<>(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<ProductlShow> selectByIdByStoreId(long id, long storeId) {
        try {
            ProductlShow productlShow = this.iProductMapper.selectByIdByStoreId(id, storeId);
            if ( productlShow == null ) {
                return new ServiceStatusInfo<>(1, "查询失败,商品不存在", null);
            }
            List<Map<String,String>> mapList = new ArrayList<>();
            Map<String,String> map = new HashMap<>();
            String time=null;
            map.put("title","有效期");
            if(productlShow.getProductDetailType().equals("CARD") || productlShow.getProductDetailType().equals("CASHCOUPON")){
                if(productlShow.getProductDetailType().equals("CARD")){
                    ProductCard productCard = productCardServiceImpl.selectByProductId(id);
                    if(productCard.getValidDays() == 0){
                        time ="长期有效";
                    }else if(productCard.getValidDays() > 0 ){
                        time ="有效期"+productCard.getValidDays()+"天";
                    }

                }
                if(productlShow.getProductDetailType().equals("CASHCOUPON")){
                    ProductCashCoupon productCashCoupon = productCashCouponServiceImpl.selectByProductId(id);
                    if(productCashCoupon.getValidDays() == 0){
                        time ="长期有效";
                    }else if(productCashCoupon.getValidDays() > 0 ){
                        time ="有效期"+productCashCoupon.getValidDays()+"天";
                    }
                }

                map.put("value",time);
            }else{
                map.put("value","长期有效");
            }
            map.put("type","text");
            mapList.add(map);
            map = new HashMap<>();
            map.put("title","预约信息");
            map.put("value ","无需预约，如遇高峰期可能排队");
            map.put("type","text");
            mapList.add(map);
            map = new HashMap<>();
            map.put("title","规则提醒");
            map.put("value","可与其他优惠共享");
            map.put("type","text");
            mapList.add(map);
            map = new HashMap<>();
            map.put("title","适用范围");
            map.put("value","适用范围");
            map.put("type","text");
            mapList.add(map);
            productlShow.setSpecification(mapList);
            ServiceStatusInfo<ProductSKUs> serviceStatusInfo = this.productSKUsServiceImpl.selectByProductId(id);
            if (!serviceStatusInfo.isSuccess()) {
                return new ServiceStatusInfo<>(1, "查询失败,SUK不存在", null);
            }
            ProductSKUs productSKUs = serviceStatusInfo.getData();
            productlShow.setProductSKUId(productSKUs.getId());
            productlShow.setPromotionPrice(productSKUs.getPromotionPrice());
            productlShow.setInventory(productSKUs.getInventory());
            productlShow.setOriginalPrice(productSKUs.getOriginalPrice());
            List<Long> userIds = iProductOrderMapper.selectByOrder(id);
            List<String> exchangeList = null;
            if (userIds.size() > 0) {
                exchangeList = iUserMapper.selectUserAvatarUrl(userIds);
            }
            productlShow.setExchangeList(exchangeList);
            return new ServiceStatusInfo<>(0, "", productlShow);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<ProductlShow>> selectByStoreId(long storeId) {
        try {
            List<ProductlShow> productlShowList = this.iProductMapper.selectByStoreId(storeId);
            for (ProductlShow productlShow : productlShowList) {
                ServiceStatusInfo<ProductSKUs> serviceStatusInfo = this.productSKUsServiceImpl.selectByProductId(productlShow.getId());
                if (!serviceStatusInfo.isSuccess()) {
                    continue;
                }
                ProductSKUs productSKUs = serviceStatusInfo.getData();
                productlShow.setProductSKUId(productSKUs.getId());
                productlShow.setPromotionPrice(productSKUs.getPromotionPrice());
                productlShow.setInventory(productSKUs.getInventory());
                productlShow.setOriginalPrice(productSKUs.getOriginalPrice());
            }
            return new ServiceStatusInfo<>(0, "", productlShowList);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<ProductOut> selectById(long id) {
        try {
            ProductOut productOut = iProductMapper.selectById(id);
            return new ServiceStatusInfo<>(0, "", productOut);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }

    }

    @Override
    public ServiceStatusInfo<Integer> updateProductNum(long productId, long productSkuId, int num) {
        try {
            int result = this.iProductMapper.updateProductSkuNum(productSkuId, num);
            if (result == 0) return new ServiceStatusInfo<>(1, "商品数量更新失败", 0);
            result = this.iProductMapper.updateProductNum(productId, num);
            if (result == 0) return new ServiceStatusInfo<>(1, "商品数量更新失败", 0);
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "商品数量更新失败:" + e.getMessage(), 0);
        }

    }

    @Override
    public long getProductInventoryNum(long productId) {
        long allInventory = this.iProductMapper.getProductInventory(productId);
        return allInventory;
    }

    @Override
    public ServiceStatusInfo<Boolean> getProductInventory(long productId, long productSkuId, int num) {
        try {
            long allInventory = this.iProductMapper.getProductInventory(productId);
            if (allInventory <= 0 || allInventory < num) return new ServiceStatusInfo<>(1, "该商品库存不足", false);
            int inventory = this.iProductMapper.getProductSkuInventory(productSkuId);
            if (inventory <= 0 || inventory < num) return new ServiceStatusInfo<>(1, "该商品规格库存不足", false);
            return new ServiceStatusInfo<>(0, "", true);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "出现异常：" + e.getMessage(), false);
        }
    }

    @Override
    public ServiceStatusInfo<List<ProductMainDto>> mainProduct() {
        try {
            List<ProductMainDto> list;
            //TODO 未更新缓存和推荐
            if (redisTemplate.hasKey("MAINPRODUCT")) {
                list = (List<ProductMainDto>) redisTemplate.opsForValue().get(MainKeyType.MAINPRODUCT);
                return new ServiceStatusInfo<>(0, "", list);
            }
            list = this.iProductMapper.mainSelectProduct();
            for (ProductMainDto productMainDto : list) {
                ServiceStatusInfo<ProductSKUs> serviceStatusInfo = this.productSKUsServiceImpl.selectByProductId(productMainDto.getId());
                productMainDto.setProductSKUId(serviceStatusInfo.getData().getId());
            }
            redisTemplate.opsForValue().set(MainKeyType.MAINPRODUCT, list);
            return new ServiceStatusInfo<>(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<List<ProductInfo>> selectProductByStoreId(Long storeId) {
        List<ProductInfo> result = null;
        try {
            result = iProductMapper.selectProductByStoreId(storeId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败", null);
        }

    }
}
