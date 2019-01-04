package com.zwdbj.server.shop_admin_service.service.products.service;


import com.zwdbj.server.shop_admin_service.service.productSKUs.model.ProductSKUs;
import com.zwdbj.server.shop_admin_service.service.products.model.Products;
import com.zwdbj.server.shop_admin_service.service.products.model.SearchProducts;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductService {
    ServiceStatusInfo<Long> createProducts(Products products, long originalPrice, long promotionPrice, boolean festivalCanUse, int specHoursValid, int validDays, Date validStartTime,Date validEndTime,String validType);

    ServiceStatusInfo<Long> deleteProductsById(Long id);

    ServiceStatusInfo<Long> updateProducts(Products products, long originalPrice, long promotionPrice, boolean festivalCanUse, int specHoursValid, int validDays, Date validStartTime,Date validEndTime,String validType);

    ServiceStatusInfo<List<Products>> selectAll();
    ServiceStatusInfo<List<Products>> searchProducts(SearchProducts searchProduct);

    /**
     * 批量商品上下架
     * @param id
     * @param publish
     * @return
     */
    ServiceStatusInfo<Long> updatePublishs(Long[] id , boolean publish);

    /**
     * 查询单个商品
     * @param id
     * @return
     */
    ServiceStatusInfo<Map<String,Object>> selectById(long id);

    /**
     * 批量删除商品
     * @param id
     * @return
     */
    ServiceStatusInfo<Long> deleteByProducts(Long[] id);

    /**
     * 根据条件查询数据
     * @param searchProduct
     * @param type 1销售中 2已售完 3待上架
     * @return
     */
    ServiceStatusInfo<List<Products>> searchCondition(SearchProducts searchProduct,int type);

}
