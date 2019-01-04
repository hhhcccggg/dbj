package com.zwdbj.server.shop_admin_service.service.products.service;


import com.zwdbj.server.shop_admin_service.service.productCard.mapper.IProductCardMapper;
import com.zwdbj.server.shop_admin_service.service.productCard.model.ProductCard;
import com.zwdbj.server.shop_admin_service.service.productCashCoupon.mapper.IProductCashCouponMapper;
import com.zwdbj.server.shop_admin_service.service.productCashCoupon.model.ProductCashCoupon;
import com.zwdbj.server.shop_admin_service.service.productSKUs.mapper.IProductSKUsMapper;
import com.zwdbj.server.shop_admin_service.service.productSKUs.model.ProductSKUs;
import com.zwdbj.server.shop_admin_service.service.products.mapper.IProductsMapper;
import com.zwdbj.server.shop_admin_service.service.products.model.Products;
import com.zwdbj.server.shop_admin_service.service.products.model.SearchProducts;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Resource
    protected IProductsMapper iProductMapper;

    @Resource
    protected IProductSKUsMapper iProductSKUsMapper;

    @Resource
    protected IProductCardMapper iProductCardMapper;

    @Resource
    protected IProductCashCouponMapper iProductCashCouponMapper;

    @Override
    public ServiceStatusInfo<Long> createProducts(Products products, long originalPrice, long promotionPrice,
                                                  boolean festivalCanUse, int specHoursValid, int validDays,
                                                  Date validStartTime, Date validEndTime, String validType) {

        ServiceStatusInfo serviceStatusInfo = judgeProducts(products,validType,specHoursValid,validDays,validStartTime,validEndTime);
        if(serviceStatusInfo != null){
            return serviceStatusInfo;
        }
        //生成唯一id
        long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = this.iProductMapper.createProducts(id, products);
            if(result==0){
                return new ServiceStatusInfo<>(1, "新增失败：影响行数"+result, null);
            }
            ProductSKUs productSKUs = new ProductSKUs();
            productSKUs.setProductId(id);
            productSKUs.setWeight(products.getWeight());
            productSKUs.setInventory(products.getInventory());
            productSKUs.setOriginalPrice(originalPrice);
            productSKUs.setPromotionPrice(promotionPrice);
            iProductSKUsMapper.createProductSKUs(UniqueIDCreater.generateID(),productSKUs);
            if("CARD".equals(products.getProductDetailType())){
                ProductCard productCard = new ProductCard(festivalCanUse,validType,specHoursValid,validDays,validStartTime,validEndTime,id);
                this.iProductCardMapper.createProductCard(UniqueIDCreater.generateID(),productCard);
            }
            if("CASHCOUPON".equals(products.getProductDetailType())){
                ProductCashCoupon productCashCoupon = new ProductCashCoupon(festivalCanUse,validType,specHoursValid,validDays,validStartTime,validEndTime,id);
                this.iProductCashCouponMapper.createProductCashCoupon(UniqueIDCreater.generateID(),productCashCoupon);
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建失败：" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<Long> deleteProductsById(Long id) {
        Long result = 0L;
        try {
            result = this.iProductMapper.deleteProduct(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> updateProducts(Products products, long originalPrice, long promotionPrice,
                                                  boolean festivalCanUse, int specHoursValid, int validDays,
                                                  Date validStartTime, Date validEndTime, String validType){
        ServiceStatusInfo serviceStatusInfo = judgeProducts(products,validType,specHoursValid,validDays,validStartTime,validEndTime);
        if(serviceStatusInfo != null){
            return serviceStatusInfo;
        }
        Long result = 0L;
        try {
            result = this.iProductMapper.update(products);
            if(result==0){
                return new ServiceStatusInfo<>(1, "修改失败：影响行数"+result, null);
            }
            ProductSKUs productSKUs = new ProductSKUs();
            productSKUs.setProductId(products.getId());
            productSKUs.setWeight(products.getWeight());
            productSKUs.setInventory(products.getInventory());
            productSKUs.setOriginalPrice(originalPrice);
            productSKUs.setPromotionPrice(promotionPrice);
            iProductSKUsMapper.updateProductSKUs(productSKUs);
            if("CARD".equals(products.getProductDetailType())){
                ProductCard productCard = new ProductCard(festivalCanUse,validType,specHoursValid,validDays,validStartTime,validEndTime,products.getId());
                this.iProductCardMapper.updateByProductIdByProductCard(productCard);
            }
            if("CASHCOUPON".equals(products.getProductDetailType())){
                ProductCashCoupon productCashCoupon = new ProductCashCoupon(festivalCanUse,validType,specHoursValid,validDays,validStartTime,validEndTime,products.getId());
                this.iProductCashCouponMapper.updateByProductIdByProductCashCoupon(productCashCoupon);
            }
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改失败" + e.getMessage(), result);
        }
    }


    @Override
    public ServiceStatusInfo<List<Products>> selectAll() {
        List<Products> result=null;
        try {
            result =this.iProductMapper.selectAll();
            return new ServiceStatusInfo<>(0,"",result);
        }
        catch (Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),result);
        }
    }

    @Override
    public ServiceStatusInfo<List<Products>> searchProducts(SearchProducts searchProduct) {
        List<Products>result=null;
        try{
            result=this.iProductMapper.search(searchProduct);
            return new ServiceStatusInfo<>(0,"",result);
        }
        catch (Exception e){
            return  new ServiceStatusInfo<>(1,"搜索失败"+e.getMessage(),result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> updatePublishs(Long[] id, boolean publish) {
        try {
            long result = this.iProductMapper.updatePublishs(id,publish);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "上下架失败" + e.getMessage(), 0L);
        }
    }

    @Override
    public ServiceStatusInfo<Map<String,Object>> selectById(long id) {
        try{
            Map<String,Object> map = new HashMap<>();
            Products products =this.iProductMapper.selectById(id);
            map.put("products",products);
            map.put("productsSKU",this.iProductSKUsMapper.selectByProductId(products.getId()));
            map.put("productCard",this.iProductCardMapper.selectByProductId(products.getId()));
            map.put("productCashCoupon",this.iProductCashCouponMapper.selectByProductId(products.getId()));
            return new ServiceStatusInfo<>(0, "", map);
        }catch(Exception e){
            return new ServiceStatusInfo<>(0, "查询单个商品失败"+e.getMessage(), null);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteByProducts(Long[] id) {
        try {
            long result = this.iProductMapper.deleteByProducts(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "批量失败" + e.getMessage(), 0L);
        }
    }

    public ServiceStatusInfo judgeProducts(Products products,String validType,int specHoursValid , int validDays,Date validStartTime,Date validEndTime){
        if(products.getProductType() != 0 && products.getProductType() != 1){
            return new ServiceStatusInfo<>(1, "创建失败：产品类型不正确", null);
        }
        if(!"DELIVERY".equals(products.getProductDetailType()) && !"NODELIVERY".equals(products.getProductDetailType())
                && !"CARD".equals(products.getProductDetailType()) && !"CASHCOUPON".equals(products.getProductDetailType())){
            return new ServiceStatusInfo<>(1, "创建失败：产品详细类型不正确", null);
        }
        if("CARD".equals(products.getProductDetailType()) || "CASHCOUPON".equals(products.getProductDetailType())){
            if(!"PAY_VALIDED".equals(validType) && !"PAY_VALIDED".equals(validType) && !"PAY_SPEC_HOUR_VALIDED".equals(validType)){
                return new ServiceStatusInfo<>(1, "创建失败：validType类型不正确", null);
            }
            if("PAY_VALIDED".equals(validType) && specHoursValid <= 0 && validDays <=-1 && validStartTime == null && validEndTime==null){
                return new ServiceStatusInfo<>(1, "创建失败：PAY_VALIDED生效类型不正确", null);
            }
            if("PAY_NEXTDAY_VALIDED".equals(validType) && validDays <=-1 && validStartTime == null && validEndTime==null){
                return new ServiceStatusInfo<>(1, "创建失败：PAY_NEXTDAY_VALIDED生效类型不正确", null);
            }
            if("PAY_SPEC_HOUR_VALIDED".equals(validType) && validStartTime == null && validEndTime==null){
                return new ServiceStatusInfo<>(1, "创建失败：PAY_SPEC_HOUR_VALIDED生效类型不正确", null);
            }
        }
        return null;
    }
}
