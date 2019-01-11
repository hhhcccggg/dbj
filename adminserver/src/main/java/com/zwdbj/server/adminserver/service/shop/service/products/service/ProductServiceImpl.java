package com.zwdbj.server.adminserver.service.shop.service.products.service;


import com.zwdbj.server.adminserver.service.shop.service.productCard.mapper.IProductCardMapper;
import com.zwdbj.server.adminserver.service.shop.service.productCard.model.ProductCard;
import com.zwdbj.server.adminserver.service.shop.service.productCashCoupon.mapper.IProductCashCouponMapper;
import com.zwdbj.server.adminserver.service.shop.service.productCashCoupon.model.ProductCashCoupon;
import com.zwdbj.server.adminserver.service.shop.service.productSKUs.mapper.IProductSKUsMapper;
import com.zwdbj.server.adminserver.service.shop.service.productSKUs.model.ProductSKUs;
import com.zwdbj.server.adminserver.service.shop.service.products.mapper.IProductsMapper;
import com.zwdbj.server.adminserver.service.shop.service.products.model.CreateProducts;
import com.zwdbj.server.adminserver.service.shop.service.products.model.Products;
import com.zwdbj.server.adminserver.service.shop.service.products.model.SearchProducts;
import com.zwdbj.server.adminserver.service.shop.service.products.model.UpdateProducts;
import com.zwdbj.server.tokencenter.TokenCenterManager;
import com.zwdbj.server.tokencenter.model.AuthUser;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    protected TokenCenterManager tokenCenterManager;

    @Override
    public ServiceStatusInfo<Long> createProducts(CreateProducts createProducts) {
        AuthUser authUser = tokenCenterManager.fetchUser(JWTUtil.getCurrentId()+"").getData();
        if(authUser == null){
            return new ServiceStatusInfo(1,"查询失败:用户不存在",null);
        }
        createProducts.setStoreId(authUser.getLegalSubjectId());
        ServiceStatusInfo serviceStatusInfo = judgeProducts(createProducts);
        if(serviceStatusInfo != null){
            return serviceStatusInfo;
        }
        //生成唯一id
        long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = this.iProductMapper.createProducts(id, createProducts);
            if(result==0){
                return new ServiceStatusInfo<>(1, "新增失败：影响行数"+result, null);
            }
            ProductSKUs productSKUs = new ProductSKUs();
            productSKUs.setProductId(id);
            productSKUs.setInventory(createProducts.getInventory());
            productSKUs.setOriginalPrice(createProducts.getOriginalPrice());
            productSKUs.setPromotionPrice(createProducts.getPromotionPrice());
            iProductSKUsMapper.createProductSKUs(UniqueIDCreater.generateID(),productSKUs);
            if("CARD".equals(createProducts.getProductDetailType())){
                ProductCard productCard = new ProductCard(createProducts,id);
                this.iProductCardMapper.createProductCard(UniqueIDCreater.generateID(),productCard);
            }
            if("CASHCOUPON".equals(createProducts.getProductDetailType())){
                ProductCashCoupon productCashCoupon = new ProductCashCoupon(createProducts,id);
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
    public ServiceStatusInfo<Long> updateProducts(UpdateProducts updateProducts){
//        ServiceStatusInfo serviceStatusInfo = judgeProducts(createProducts);
//        if(serviceStatusInfo != null){
//            return serviceStatusInfo;
//        }
        Long result = 0L;
        try {
            result = this.iProductMapper.update(updateProducts);
            if(result==0){
                return new ServiceStatusInfo<>(1, "修改失败：影响行数"+result, null);
            }
            ProductSKUs productSKUs = new ProductSKUs();
            productSKUs.setProductId(updateProducts.getId());
            productSKUs.setInventory(updateProducts.getInventory());
            productSKUs.setOriginalPrice(updateProducts.getOriginalPrice());
            productSKUs.setPromotionPrice(updateProducts.getPromotionPrice());
            iProductSKUsMapper.updateProductSKUs(productSKUs);
            Products createProducts = iProductMapper.selectById(updateProducts.getId());
            if("CARD".equals(createProducts.getProductDetailType())){
                ProductCard productCard = new ProductCard(updateProducts,updateProducts.getId());
                this.iProductCardMapper.updateByProductIdByProductCard(productCard);
            }
            if("CASHCOUPON".equals(createProducts.getProductDetailType())){
                ProductCashCoupon productCashCoupon = new ProductCashCoupon(updateProducts,updateProducts.getId());
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

    @Override
    public ServiceStatusInfo<List<Products>> searchCondition(SearchProducts searchProduct, int type) {
        List<Products> result=null;
        try {
            result =this.iProductMapper.searchCondition(searchProduct,type);
            return new ServiceStatusInfo<>(0,"",result);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),result);
        }
    }

    /**
     * 判断数据
     * @param createProducts
     * @return
     */
    public ServiceStatusInfo judgeProducts(CreateProducts createProducts){
        if(createProducts.getProductType() != 0 && createProducts.getProductType() != 1){
            return new ServiceStatusInfo<>(1, "创建失败：产品类型不正确", null);
        }
        if(!"DELIVERY".equals(createProducts.getProductDetailType()) && !"NODELIVERY".equals(createProducts.getProductDetailType())
                && !"CARD".equals(createProducts.getProductDetailType()) && !"CASHCOUPON".equals(createProducts.getProductDetailType())){
            return new ServiceStatusInfo<>(1, "创建失败：产品详细类型不正确", null);
        }
        if("CARD".equals(createProducts.getProductDetailType()) || "CASHCOUPON".equals(createProducts.getProductDetailType())){
            if(!"PAY_VALIDED".equals(createProducts.getValidType()) && !"PAY_VALIDED".equals(createProducts.getValidType()) && !"PAY_SPEC_HOUR_VALIDED".equals(createProducts.getValidType())){
                return new ServiceStatusInfo<>(1, "创建失败：validType类型不正确", null);
            }
            if("PAY_VALIDED".equals(createProducts.getValidType()) && createProducts.getSpecHoursValid() <= 0 && createProducts.getValidDays() <=-1 && createProducts.getValidStartTime() == null && createProducts.getValidEndTime()==null){
                return new ServiceStatusInfo<>(1, "创建失败：PAY_VALIDED生效类型不正确", null);
            }
            if("PAY_NEXTDAY_VALIDED".equals(createProducts.getValidType()) && createProducts.getValidDays() <=-1 && createProducts.getValidStartTime() == null && createProducts.getValidEndTime()==null){
                return new ServiceStatusInfo<>(1, "创建失败：PAY_NEXTDAY_VALIDED生效类型不正确", null);
            }
            if("PAY_SPEC_HOUR_VALIDED".equals(createProducts.getValidType()) && createProducts.getValidStartTime() == null && createProducts.getValidEndTime()==null){
                return new ServiceStatusInfo<>(1, "创建失败：PAY_SPEC_HOUR_VALIDED生效类型不正确", null);
            }
        }
        return null;
    }
}
