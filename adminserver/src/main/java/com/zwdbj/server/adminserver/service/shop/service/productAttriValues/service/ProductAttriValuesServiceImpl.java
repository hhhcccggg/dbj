package com.zwdbj.server.adminserver.service.shop.service.productAttriValues.service;


import com.zwdbj.server.adminserver.service.shop.service.productAttriValues.mapper.IProductAttriValuesMapper;
import com.zwdbj.server.adminserver.service.shop.service.productAttriValues.model.ProductAttriValues;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ProductAttriValuesServiceImpl implements ProductAttriValuesService {
    @Resource
    private IProductAttriValuesMapper productAttriValuesMapper;

    @Override
    public ServiceStatusInfo<Long> createProductAttriValues(ProductAttriValues productAttriValues) {
        Long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = productAttriValuesMapper.createProductAttriValues(id,productAttriValues);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建商品属性规格值失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {
        Long result = 0L;
        try {
            result = productAttriValuesMapper.deleteById(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除商品属性规格值失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> updateProductAttriValues(ProductAttriValues productAttriValues) {
        Long result = 0L;
        try {
            result = productAttriValuesMapper.updateProductAttriValues(productAttriValues);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改商品属性规格值失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<ProductAttriValues>> select() {
        List<ProductAttriValues> list = null;
        try {
            list = productAttriValuesMapper.select();
            return new ServiceStatusInfo<>(0, "", list);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询所有商品属性规格值失败" + e.getMessage(), list);
        }
    }
}
