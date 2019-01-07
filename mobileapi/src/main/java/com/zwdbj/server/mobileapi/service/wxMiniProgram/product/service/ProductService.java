package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
import com.zwdbj.server.utility.model.ServiceStatusInfo;

import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * 小程序查询兑换商城列表
     * @return
     */
    ServiceStatusInfo<List<ProductOut>> selectWXXCXShopProduct();

    /**
     * 根据ID查询单个商品
     * @param id
     * @return
     */
    ServiceStatusInfo<Map<String,Object>> selectWXXCXById(long id);
}
