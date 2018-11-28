package com.zwdbj.server.shopservice.product.service;


import com.zwdbj.server.shopservice.product.mapper.IProductsMapper;
import com.zwdbj.server.shopservice.product.model.Products;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    protected IProductsMapper iProductMapper;

    @Override
    public ServiceStatusInfo<Long> createProducts(Products products) {
        //生成唯一id
        long id = UniqueIDCreater.generateID();
        Long result = 0L;

        try {
            result = this.iProductMapper.createProducts(id, products);
            return new ServiceStatusInfo<Long>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<Long>(1, "创建失败：" + e.getMessage(), result);
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
    public ServiceStatusInfo<Long> updateProducts(Products products) {
        Long result = 0L;
        try {
            result = this.iProductMapper.update(products);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改失败" + e.getMessage(), result);
        }
    }


    @Override
    public ServiceStatusInfo<List<Products>> selectAll(int currentPage, int pageSize) {
        List<Products> result=null;
        try {
            result =this.iProductMapper.selectAll();
            return new ServiceStatusInfo<>(0,"",result);
        }
        catch (Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),result);
        }


    }
}
