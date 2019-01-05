package com.zwdbj.server.adminserver.service.shop.service.businessSellers.service;

import com.zwdbj.server.adminserver.service.shop.service.businessSellers.mapper.IBusinessSellerMapper;
import com.zwdbj.server.adminserver.service.shop.service.businessSellers.model.BusinessSellerAddInput;
import com.zwdbj.server.adminserver.service.shop.service.businessSellers.model.BusinessSellerDto;
import com.zwdbj.server.adminserver.service.shop.service.businessSellers.model.BusinessSellerModel;
import com.zwdbj.server.adminserver.service.shop.service.businessSellers.model.BusinessSellerModifyInput;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class BusinessSellerServiceImpl implements BusinessSellerService {
    @Resource
    IBusinessSellerMapper businessSellerMapper;
    @Override
    public List<BusinessSellerDto> findAllBusinessSellers() {
        List<BusinessSellerDto> businessSellerModels = this.businessSellerMapper.findAllBusinessSellers();
        return businessSellerModels;
    }

    @Override
    public ServiceStatusInfo<BusinessSellerModel> getBusinessSellerById(long businessSellerId) {
        try {
            BusinessSellerModel businessSeller = this.businessSellerMapper.getBusinessSellerById(businessSellerId);
            return new ServiceStatusInfo<>(0,"获取商铺成功",businessSeller);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"获取商铺失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> modifyBusinessSellers(long id, BusinessSellerModifyInput input) {
        try {
            int result = this.businessSellerMapper.modifyBusinessSellers(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"修改商铺失败",result);
            return new ServiceStatusInfo<>(0,"修改商铺成功",result);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"修改商铺失败"+e.getMessage(),null);
        }

    }

    @Override
    public ServiceStatusInfo<Integer> addBusinessSellers(BusinessSellerAddInput input) {
        try {
            long id = UniqueIDCreater.generateID();
            int result = this.businessSellerMapper.addBusinessSellers(id,input);
            if (result==0)return new ServiceStatusInfo<>(1,"添加商铺失败",result);
            return new ServiceStatusInfo<>(0,"添加商铺成功",result);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"添加商铺失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> deleteBusinessSellers(long id) {
        try {
            int result = this.businessSellerMapper.deleteBusinessSellers(id);
            if (result==0)return new ServiceStatusInfo<>(1,"下架商铺失败",result);
            return new ServiceStatusInfo<>(0,"下架商铺成功",result);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"下架商铺失败"+e.getMessage(),null);
        }
    }

    @Override
    public ServiceStatusInfo<Integer> closeBusinessSellers(long id) {
        try {
            int result = this.businessSellerMapper.closeBusinessSellers(id);
            if (result==0)return new ServiceStatusInfo<>(1,"关闭商铺失败",result);
            return new ServiceStatusInfo<>(0,"关闭商铺成功",result);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"关闭商铺失败"+e.getMessage(),null);
        }
    }


}
