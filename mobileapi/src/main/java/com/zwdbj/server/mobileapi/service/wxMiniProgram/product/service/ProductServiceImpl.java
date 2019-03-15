package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service;

import com.zwdbj.server.mobileapi.config.MainKeyType;
import com.zwdbj.server.mobileapi.service.store.model.StoreModel;
import com.zwdbj.server.mobileapi.service.store.service.StoreService;
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
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
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
    private StoreService storeServiceImpl;

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
            if (productlShow == null) {
                return new ServiceStatusInfo<>(1, "查询失败,商品不存在", null);
            }
            List<Map<String, String>> mapList = new ArrayList<>();
            String time = null;
            String appointment = null;
            boolean stackUse = false;
            if (productlShow.getProductDetailType().equals("CARD") || productlShow.getProductDetailType().equals("CASHCOUPON")) {
                if (productlShow.getProductDetailType().equals("CARD")) {
                    ProductCard productCard = productCardServiceImpl.selectByProductId(id);
                    if (productCard.getValidDays() == 0) {
                        time = "长期有效";
                    } else if (productCard.getValidDays() > 0) {
                        time = "有效期" + productCard.getValidDays() + "天";
                    }
                    appointment = productCard.getAppointment();
                    stackUse = productCard.isStackUse();
                }
                if (productlShow.getProductDetailType().equals("CASHCOUPON")) {
                    ProductCashCoupon productCashCoupon = productCashCouponServiceImpl.selectByProductId(id);
                    if (productCashCoupon.getValidDays() == 0) {
                        time = "长期有效";
                    } else if (productCashCoupon.getValidDays() > 0) {
                        time = "有效期" + productCashCoupon.getValidDays() + "天";
                    }
                    appointment = productCashCoupon.getAppointment();
                    stackUse = productCashCoupon.isStackUse();
                }
            } else {
                time = "长期有效";
                appointment = "无需预约，如遇高峰期可能排队";
            }
            mapList.add(newMap("有效期", time, "text"));
            mapList.add(newMap("预约信息", appointment, "text"));
            mapList.add(newMap("规则提醒", stackUse ? "" : "不" + "可与其他优惠共享", "text"));
            mapList.add(newMap("适用范围", "适用范围", "text"));
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
                exchangeList = new ArrayList<>(userIds.size());
                List<Map<String,String>> userAvatarUrls = iUserMapper.selectUserAvatarUrl(userIds);
                Map<String,String> userAvatarUrl = new HashMap<>();
                for (Map<String,String> map : userAvatarUrls) {
                    userAvatarUrl.put( String.valueOf(map.get("id")) ,map.get("avatarUrl"));
                }
                for (long userId : userIds) {
                    exchangeList.add(userAvatarUrl.get(String.valueOf(userId)));
                }
            }
            StoreModel storeModel = storeServiceImpl.selectById(storeId).getData();
            productlShow.setStoreName(storeModel == null ? "" : storeModel.getName());
            productlShow.setExchangeList(exchangeList);
            productlShow.setStoreId(storeId);
            return new ServiceStatusInfo<>(0, "", productlShow);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    public Map<String, String> newMap(String title, String value, String type) {
        Map map = new HashMap<>();
        map.put("title", title);
        map.put("value", value);
        map.put("type", type);
        return map;
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
    public ServiceStatusInfo<ProductOut> selectByIdNoDelete(long id) {
        try {
            ProductOut productOut = iProductMapper.selectByIdNoDelete(id);
            return new ServiceStatusInfo<>(0, "", productOut);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败" + e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> updateProductNum(long productId, long productSkuId, int num) {
        int result = this.iProductMapper.updateProductSkuNum(productSkuId, num);
        if (result == 0) return new ServiceStatusInfo<>(1, "商品数量更新失败", 0);
        result = this.iProductMapper.updateProductNum(productId, num);
        if (result == 0) return new ServiceStatusInfo<>(1, "商品数量更新失败", 0);
        return new ServiceStatusInfo<>(0, "", result);
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
            if (redisTemplate.hasKey(MainKeyType.MAINPRODUCT)) {
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
            //查询产品规格
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询失败", null);
        }

    }

    @Override
    public List<Map<String, String>> selectEs() {
        List<Map<String,String>> mapList = this.iProductMapper.selectEs();
        return mapList;
    }

    @Override
    public ServiceStatusInfo<ShareProduct> shareProduct(long id) {
       try{
           ShareProduct shareProduct = this.iProductMapper.selectShareProduct(id);
           return new ServiceStatusInfo<>(0, "", shareProduct);
       }catch(Exception e){
           return new ServiceStatusInfo<>(1, e.getMessage(), null);
       }
    }
}
