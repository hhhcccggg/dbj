package com.zwdbj.server.adminserver.service.shop.service.productBrands.service;

import com.zwdbj.server.adminserver.service.shop.service.productBrands.mapper.IProductBrandsMapper;
import com.zwdbj.server.adminserver.service.shop.service.productBrands.model.ProductBrands;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ProductBrandsServiceImpl implements ProductBrandsService {
    @Resource
    private IProductBrandsMapper productBrandsMapper;

    @Override
    public ServiceStatusInfo<Long> createProductBrands(ProductBrands productBrands) {
        Long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = this.productBrandsMapper.createProductBrands(id, productBrands);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建品牌失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> updateProductBrands(ProductBrands productBrands) {
        Long result = 0L;
        try {
            result = this.productBrandsMapper.updateProductBrands(productBrands);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改品牌失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {
        Long result = 0L;
        try {
            result = this.productBrandsMapper.deleteById(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除品牌失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<ProductBrands>> selectAll() {
        List<ProductBrands> results = null;
        try {
            results = this.productBrandsMapper.selectAll();
            return new ServiceStatusInfo<>(0, "", results);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询品牌失败" + e.getMessage(), results);
        }


    }
}
