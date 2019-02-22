package com.zwdbj.server.adminserver.service.userTenant.service;

import com.zwdbj.server.adminserver.service.shop.service.legalSubject.model.ShopTenantModel;
import com.zwdbj.server.adminserver.service.shop.service.legalSubject.service.ILegalSubjectService;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.userTenant.mapper.IUserTenantMapper;
import com.zwdbj.server.adminserver.service.userTenant.model.*;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserTenantService {
    @Autowired
    IUserTenantMapper userTenantMapper;
    @Autowired
    UserService userService;
    @Autowired
    ILegalSubjectService legalSubjectServiceImpl;

    public List<UserTenantModel> getUserTenants(UserTenantSearchInput input){
        List<UserTenantModel> userTenantModels = this.userTenantMapper.getUserTenants(input);
        return  userTenantModels;
    }
    @Transactional
    public ServiceStatusInfo<Integer> addUserTenant(UserTenantInput input){
            //检验租户标识是否存在
        String identifyName = UniqueIDCreater.generateUserName();
        input.setIdentifyName(identifyName);
        input.setExpireTime("2030-01-01");

        input.setLeagalRepresentativeName(input.getUsername());
        int result = this.userTenantMapper.identifyNameIsExsit(input.getIdentifyName());
        if (result!=0)return new ServiceStatusInfo<>(1,"租户标识已经存在，请更换标识后再添加",null);
        long id = UniqueIDCreater.generateID();
        long legalSubjectId = UniqueIDCreater.generateID();
        int a = this.userTenantMapper.addUserTenant(id,input.getName(),input.getIdentifyName(),legalSubjectId);
        if (a==0)return new ServiceStatusInfo<>(1,"租户创建失败",null);
        int b = this.userService.greateUserByTenant(input.getUsername(),input.getPhone(),id,1,"店主");
        if (b==0)return new ServiceStatusInfo<>(1,"租户用户创建失败",null);
        int c = this.legalSubjectServiceImpl.addLegalSubject(legalSubjectId,input);
        if (c==0)return new ServiceStatusInfo<>(1,"租户商户创建失败",null);
        return  new ServiceStatusInfo<>(0,"租户创建成功",1);
    }

    @Transactional
    public ServiceStatusInfo<Integer> modifyUserTenant(long id,ModifyUserTenantInput input){
        int a =  this.userTenantMapper.modifyUserTenant(id,input.getName());
        if (a==0)return new ServiceStatusInfo<>(1,"租户修改失败",null);
        long legalSubjectId = this.userTenantMapper.findLegalSubjectIdById(id);
        int b = this.userService.modifyUserByTenantId(id);
        if (b==0)return new ServiceStatusInfo<>(1,"租户修改失败",null);
        int c = this.userService.greateUserByTenant(input.getUsername(),input.getPhone(),id,1,"店主");
        if (c==0)return new ServiceStatusInfo<>(1,"租户修改失败",null);
        int d = this.legalSubjectServiceImpl.modifyBasicLegalSubject(legalSubjectId,input);
        if (d==0)return new ServiceStatusInfo<>(1,"租户商户修改失败",null);
        return  new ServiceStatusInfo<>(0,"修改租户成功",1);

    }
    public UserTenantModel getUserTenantById(long id){
        return this.userTenantMapper.getUserTenantById(id);
    }
    public ServiceStatusInfo<TenantDetailModel> getDetailTenantById(long id){
        try {
            TenantDetailModel model = this.userTenantMapper.getDetailTenantById(id);
            ShopTenantModel shopTenantModel= this.legalSubjectServiceImpl.getDetailTenant(model.getLegalSubjectId());
            if (shopTenantModel!=null){
                model.setCategoryId(shopTenantModel.getCategoryId());
                model.setCityId(shopTenantModel.getCityId());
                model.setExpireTime(shopTenantModel.getExpireTime());
                model.setLeagalRepresentativeID(shopTenantModel.getLeagalRepresentativeID());
                model.setLeagalRepresentativeName(shopTenantModel.getLeagalRepresentativeName());
                model.setLegalType(shopTenantModel.getLegalType());
                model.setStoreType(shopTenantModel.getStoreType());
                model.setNickName(shopTenantModel.getContactName());
                model.setPhone(shopTenantModel.getContactPhone());
            }
            return new ServiceStatusInfo<>(0,"",model);
        }catch (Exception e){
            return new ServiceStatusInfo<>(1,"出现异常:"+e.getMessage(),null);
        }


    }

    @Transactional
    public ServiceStatusInfo<Integer> deleteUserTenant(long id){
            UserTenantModel model = this.getUserTenantById(id);
            if (model==null)return new ServiceStatusInfo<>(1,"该租户不存在",null);
            int b = this.userService.delUserByTenantId(id);
            if (b==0)return new ServiceStatusInfo<>(1,"租户用户修改失败",null);
            //假删
            int d  = this.userTenantMapper.delTenantById(id);
            if (d==0)return new ServiceStatusInfo<>(1,"租户删除失败",null);
            //假删
            int c = this.legalSubjectServiceImpl.delLegalSubject(model.getLegalSubjectId());
            if (c==0)return new ServiceStatusInfo<>(1,"租户商户修改失败",null);
            return  new ServiceStatusInfo<>(0,"删除租户成功",1);

    }

}
