package com.zwdbj.server.adminserver.service.shop.service.products.service;


import com.zwdbj.server.adminserver.service.shop.service.products.model.*;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface ProductService {
    ServiceStatusInfo<Long> createProducts(CreateProducts createProducts);

    ServiceStatusInfo<Long> deleteProductsById(Long id);
    long getProductInventoryNum(long productId);
    /**
     * 根据productId和productSkuId更新相对应的库存
     */
    ServiceStatusInfo<Integer> updateProductNum(long productId, long productSkuId, int num);

    ServiceStatusInfo<Long> updateProducts(UpdateProducts updateProducts);

    ServiceStatusInfo<List<Products>> selectAll();

    ServiceStatusInfo<List<Products>> searchProducts(SearchProducts searchProduct);

    /**
     * 批量商品上下架
     *
     * @param id
     * @param publish
     * @return
     */
    ServiceStatusInfo<Long> updatePublishs(Long[] id, boolean publish);

    /**
     * 批量商品上下架
     *
     * @param id
     * @param publish
     * @return
     */
    ServiceStatusInfo<Long> updatePublishs(String id, boolean publish);

    /**
     * 查询单个商品
     *
     * @param id
     * @return
     */
    ServiceStatusInfo<ProductsOut> selectById(long id);

    /**
     * 查询单个商品
     * @param id
     * @return
     */
    ServiceStatusInfo<ProductOut> selectByIdPartial(long id);
    /**
     * 批量删除商品
     *
     * @param id
     * @return
     */
    ServiceStatusInfo<Long> deleteByProducts(Long[] id);

    /**
     * 批量删除商品
     *
     * @param id
     * @return
     */
    ServiceStatusInfo<Long> deleteByProducts(String id);

    /**
     * 根据条件查询数据
     *
     * @param searchProduct
     * @param type          1销售中 2已售完 3待上架
     * @return
     */
    ServiceStatusInfo<List<Products>> searchCondition(SearchProducts searchProduct, int type, int pageNo, int rows);

    ServiceStatusInfo<List<StoreProduct>> selectProductByStoreId(long storeId);
}
