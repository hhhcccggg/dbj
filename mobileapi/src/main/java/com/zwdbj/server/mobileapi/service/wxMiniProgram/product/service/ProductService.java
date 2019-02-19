package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.*;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * 小程序查询兑换商城列表
     *
     * @return
     */
    ServiceStatusInfo<List<ProductOut>> selectShopProduct(ProductInput productInput);

    /**
     * 根据ID查询单个商品
     *
     * @param id
     * @return
     */
    ServiceStatusInfo<ProductlShow> selectByIdByStoreId(long id, long storeId);

    /**
     * 根据商品类型,产品详细类型,店铺id查询商品
     *
     * @param type
     * @param storeId
     * @return
     */
    ServiceStatusInfo<List<ProductInfo>> selectProductByStoreId( Long storeId);

    /**
     * 根据storeId商品
     *
     * @param id
     * @return
     */
    ServiceStatusInfo<List<ProductlShow>> selectByStoreId(long storeId);

    /**
     * 查询单个商品
     *
     * @param id
     * @return
     */
    ServiceStatusInfo<ProductOut> selectById(long id);

    /**
     * 根据productId和productSkuId更新相对应的库存
     */
    ServiceStatusInfo<Integer> updateProductNum(long productId, long productSkuId, int num);

    /**
     * 根据productId和productSkuId查询相对应的库存
     */
    ServiceStatusInfo<Boolean> getProductInventory(long productId, long productSkuId, int num);

    /**
     * 主页的兑换列表3个
     *
     * @return
     */
    ServiceStatusInfo<List<ProductMainDto>> mainProduct();
}
