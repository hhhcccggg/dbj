package com.zwdbj.server.shop_admin_service.productAttriLinks.service;

import com.zwdbj.server.shop_admin_service.productAttriLinks.mapper.IProductAttriLinksMapper;
import com.zwdbj.server.shop_admin_service.productAttriLinks.model.ProductAttriLinks;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ProductAttriLinksServiceImpl implements ProductAttriLinksService {
    @Resource
    private IProductAttriLinksMapper iProductAttriLinksMapper;


    @Override
    public ServiceStatusInfo<Long> updateProductAttriLinks(ProductAttriLinks productAttriLinks) {
        Long result = 0L;
        try {
            result = this.iProductAttriLinksMapper.updateProductAttriLinks(productAttriLinks);
            return new ServiceStatusInfo<Long>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<Long>(1, "更新商品属性关系失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> createProductAttriLinks(ProductAttriLinks productAttriLinks) {
        Long result = 0L;
        try {
            result = this.iProductAttriLinksMapper.createProductAttriLinks(productAttriLinks);
            return new ServiceStatusInfo<Long>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<Long>(1, "创建商品属性关系失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {
        Long result = 0L;
        try {
            result = this.iProductAttriLinksMapper.deleteById(id);
            return new ServiceStatusInfo<Long>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<Long>(1, "删除商品属性关系失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<ProductAttriLinks>> select() {
        List<ProductAttriLinks> list = null;
        try {
            list = this.iProductAttriLinksMapper.select();
            return new ServiceStatusInfo<>(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询商品属性关系失败" + e.getMessage(), list);
        }
    }
}
