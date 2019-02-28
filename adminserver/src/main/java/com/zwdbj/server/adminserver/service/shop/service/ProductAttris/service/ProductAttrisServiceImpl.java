package com.zwdbj.server.adminserver.service.shop.service.ProductAttris.service;

import com.zwdbj.server.adminserver.service.shop.service.ProductAttris.mapper.IProductAttrisMapper;
import com.zwdbj.server.adminserver.service.shop.service.ProductAttris.model.ProductAttris;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ProductAttrisServiceImpl implements ProductAttrisService {
    @Resource
    private IProductAttrisMapper productAttrisMapper;

    @Override
    public ServiceStatusInfo<Long> createProductAttris(ProductAttris productAttris) {
        Long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = productAttrisMapper.createProductAttris(id, productAttris);
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建商品属性规格失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {
        Long result = 0L;
        try {
            result = productAttrisMapper.deleteById(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除商品属性规格失败" + e.getMessage(), result);

        }
    }

    @Override
    public ServiceStatusInfo<Long> updateProductAttris(ProductAttris productAttris) {
        Long result = 0L;
        try {
            result = productAttrisMapper.updateProductAttris(productAttris);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改商品属性规格失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<ProductAttris>> select() {
        List<ProductAttris> list = null;
        try {
            list = productAttrisMapper.selectAll();
            return new ServiceStatusInfo<>(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询商品属性规格失败" + e.getMessage(), null);
        }
    }
}
