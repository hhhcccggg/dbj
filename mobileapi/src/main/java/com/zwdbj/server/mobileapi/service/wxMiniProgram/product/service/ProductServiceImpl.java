package com.zwdbj.server.mobileapi.service.wxMiniProgram.product.service;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.mapper.IProductMapper;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.product.model.ProductOut;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductServiceImpl implements  ProductService{

    @Autowired
    protected IProductMapper iProductMapper;

    @Override
    public ServiceStatusInfo<List<ProductOut>> selectWXXCXShopProduct(ProductInput productInput) {
        //TODO
        try{
            List<ProductOut> list = this.iProductMapper.selectWXXCXShopProduct(productInput);
            return new ServiceStatusInfo<>(0,"",list);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Map<String,Object>> selectWXXCXById(long id) {
        try{
            Map<String,Object> map = new HashMap<>();
            map.put("product",this.iProductMapper.selectWXXCXById(id));
            //TODO 后面把兑换的头像加上
            return new ServiceStatusInfo<>(0,"",map);
        }catch(Exception e){
            return new ServiceStatusInfo<>(1,"查询失败"+e.getMessage(),null);
        }
    }
}
