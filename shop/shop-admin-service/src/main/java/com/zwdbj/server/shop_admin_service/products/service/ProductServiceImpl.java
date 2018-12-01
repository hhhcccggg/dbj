package com.zwdbj.server.shop_admin_service.products.service;


import com.zwdbj.server.shop_admin_service.products.mapper.IProductsMapper;
import com.zwdbj.server.shop_admin_service.products.model.Products;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Resource
    protected IProductsMapper iProductMapper;

    @Override
    public ServiceStatusInfo<Long> createProducts(Products products) {
        //生成唯一id
        long id = UniqueIDCreater.generateID();
        Long result = 0L;

        try {
            result = this.iProductMapper.createProducts(id, products);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建失败：" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<Long> deleteProductsById(Products  products) {
        Long result = 0L;
        try {
            result = this.iProductMapper.deleteProduct(products);
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
    public ServiceStatusInfo<List<Products>> selectAll() {
        List<Products> result=null;
        try {
            result =this.iProductMapper.selectAll();
            System.out.println(result);
            return new ServiceStatusInfo<>(0,"",result);
        }
        catch (Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),result);
        }


    }
}
