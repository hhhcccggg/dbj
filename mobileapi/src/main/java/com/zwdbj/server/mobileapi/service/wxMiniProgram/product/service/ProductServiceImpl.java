package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service;

import com.zwdbj.server.mobileapi.service.user.mapper.IUserMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.mapper.IProductMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductlShow;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productOrder.mapper.IProductOrderMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.model.ProductSKUs;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.productSKUs.service.ProductSKUsService;
import com.zwdbj.server.utility.common.shiro.JWTUtil;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements  ProductService{

    @Autowired
    protected IProductMapper iProductMapper;

    @Autowired
    protected IProductOrderMapper iProductOrderMapper;

    @Autowired
    private ProductSKUsService productSKUsServiceImpl;

    @Autowired
    protected IUserMapper iUserMapper;

    @Override
    public ServiceStatusInfo<List<ProductOut>> selectShopProduct(ProductInput productInput) {
        //TODO 后期可能会换成缓存
        try{
            long userId = JWTUtil.getCurrentId();
            List<ProductOut> list = this.iProductMapper.selectShopProduct(productInput);
            for(ProductOut productOut:list){
                ProductSKUs productSKUs = productSKUsServiceImpl.selectByProductId(productOut.getId()).getData();
                if(productSKUs != null){
                    productOut.setProductSKUId(productSKUs.getId());
                    productOut.setPromotionPrice(productSKUs.getPromotionPrice());
                    productOut.setOriginalPrice(productSKUs.getOriginalPrice());
                }
                productOut.setExchange(iProductOrderMapper.userBuyProductAccounts(userId,productOut.getId()));
            }
            return new ServiceStatusInfo<>(0,"",list);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<ProductlShow> selectByIdByStoreId(long id, long storeId) {
        try{
            ProductlShow productlShow = this.iProductMapper.selectByIdByStoreId(id,storeId);
            ServiceStatusInfo<ProductSKUs> serviceStatusInfo = this.productSKUsServiceImpl.selectByProductId(id);
            if(!serviceStatusInfo.isSuccess()){
                return new ServiceStatusInfo<>(1,"查询失败,SUK不存在",null);
            }
            ProductSKUs productSKUs = serviceStatusInfo.getData();
            productlShow.setProductSKUId(productSKUs.getId());
            productlShow.setPromotionPrice(productSKUs.getPromotionPrice());
            productlShow.setInventory(productSKUs.getInventory());
            productlShow.setOriginalPrice(productSKUs.getOriginalPrice());
            List<Long> userIds = iProductOrderMapper.selectByOrder(id);
            List<String> exchangeList =  null;
            if(userIds.size()>0){
                exchangeList = iUserMapper.selectUserAvatarUrl(userIds);
            }
            productlShow.setExchangeList(exchangeList);
            return new ServiceStatusInfo<>(0,"",productlShow);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<ProductOut> selectById(long id) {
        try{
            ProductOut productOut = iProductMapper.selectById(id);
            return new ServiceStatusInfo<>(0,"",productOut);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }

    }

    @Override
    public ServiceStatusInfo<Integer> updateProductNum(long productId, long productSkuId, int num) {
        try {
            int result = this.iProductMapper.updateProductSkuNum(productSkuId, num);
            if (result==0)return new ServiceStatusInfo<>(1,"商品数量更新失败",0);
            result = this.iProductMapper.updateProductNum(productId, num);
            if (result==0)return new ServiceStatusInfo<>(1,"商品数量更新失败",0);
            return new ServiceStatusInfo<>(0,"",result);

        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"商品数量更新失败:"+e.getMessage(),0);
        }

    }

    @Override
    public ServiceStatusInfo<Boolean> getProductInventory(long productId, long productSkuId, int num) {
        try {
            long allInventory = this.iProductMapper.getProductInventory(productId);
            if (allInventory<=0 || allInventory<num)return new ServiceStatusInfo<>(1,"该商品库存不足",false);
            int inventory = this.iProductMapper.getProductSkuInventory(productSkuId);
            if (inventory<=0 || inventory<num)return new ServiceStatusInfo<>(1,"该商品规格库存不足",false);
            return new ServiceStatusInfo<>(0,"",true);

        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"出现异常："+e.getMessage(),false);
        }
    }
}
