package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * 小程序查询兑换商城列表
     * @return
     */
    ServiceStatusInfo<List<ProductOut>> selectWXXCXShopProduct(ProductInput productInput);

    /**
     * 根据ID查询单个商品
     * @param id
     * @return
     */
    ServiceStatusInfo<Map<String,Object>> selectWXXCXById(long id);

    /**
     * 根据productId和productSkuId更新相对应的库存
     */
    ServiceStatusInfo<Integer> updateProductNum(long productId,long productSkuId,int num);
}
