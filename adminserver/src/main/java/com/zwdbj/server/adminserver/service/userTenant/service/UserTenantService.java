package com.zwdbj.server.adminserver.service.userTenant.service;

import com.zwdbj.server.adminserver.middleware.mq.MQWorkSender;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.userTenant.mapper.IUserTenantMapper;
import com.zwdbj.server.adminserver.service.userTenant.model.ModifyUserTenantInput;
import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantInput;
import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantModel;
import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantSearchInput;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTenantService {
    @Autowired
    IUserTenantMapper userTenantMapper;
    @Autowired
    UserService userService;

    public List<UserTenantModel> getUserTenants(UserTenantSearchInput input){
        List<UserTenantModel> userTenantModels = this.userTenantMapper.getUserTenants(input);
        return  userTenantModels;
    }
    public ServiceStatusInfo<Integer> addUserTenant(UserTenantInput input){
        try {
            //检验租户标识是否存在
            int result = this.userTenantMapper.identifyNameIsExsit(input.getIdentifyName());
            if (result!=0)return new ServiceStatusInfo<>(1,"租户标识已经存在，请更换标识后再添加",null);
            long id = UniqueIDCreater.generateID();
            long legalSubjectId = UniqueIDCreater.generateID();
            int a = this.userTenantMapper.addUserTenant(id,input.getName(),input.getIdentifyName(),legalSubjectId);
            if (a==0)return new ServiceStatusInfo<>(1,"租户创建失败",null);
            int b = this.userService.greateUserByTenant(input.getUsername(),input.getPhone(),id,true);
            if (b==0)return new ServiceStatusInfo<>(1,"租户用户创建失败",null);
            //  加入消息队列
            this.putMQ(input,legalSubjectId,1);
            return  new ServiceStatusInfo<>(0,"租户创建成功",1);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"租户创建出现异常"+e.getMessage(),null);
        }
    }

    public ServiceStatusInfo<Integer> modifyUserTenant(long id,ModifyUserTenantInput input){
        try {
            int a =  this.userTenantMapper.modifyUserTenant(id,input.getName());
            if (a==0)return new ServiceStatusInfo<>(1,"租户修改失败",null);
            long legalSubjectId = this.userTenantMapper.findLegalSubjectIdById(id);
            int b = this.userService.modifyUserByTenantId(id);
            if (b==0)return new ServiceStatusInfo<>(1,"租户修改失败",null);
            int c = this.userService.greateUserByTenant(input.getUsername(),input.getPhone(),id,true);
            if (c==0)return new ServiceStatusInfo<>(1,"租户修改失败",null);
            //  加入消息队列
            this.putMQ(input,legalSubjectId,2);
            return  new ServiceStatusInfo<>(0,"修改租户成功",1);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"修改租户失败"+e.getMessage(),null);
        }

    }
    public UserTenantModel getUserTenantById(long id){
        return this.userTenantMapper.getUserTenantById(id);
    }

    public ServiceStatusInfo<Integer> deleteUserTenant(long id){
        try {
            UserTenantModel model = this.getUserTenantById(id);
            if (model==null)return new ServiceStatusInfo<>(1,"租户修改失败",null);
            int b = this.userService.modifyUserByTenantId(id);
            if (b==0)return new ServiceStatusInfo<>(1,"租户修改失败",null);
            ModifyUserTenantInput input = new ModifyUserTenantInput();
            input.setContactNumber("");
            input.setName(model.getName());
            input.setPhone(model.getPhone());
            input.setUsername(model.getNickName());
            //加入消息队列
            this.putMQ(input,model.getLegalSubjectId(),3);
            return  new ServiceStatusInfo<>(0,"删除租户成功",1);

        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"删除租户失败"+e.getMessage(),null);
        }
    }

    private void putMQ(ModifyUserTenantInput input,long legalSubjectId,int type){
        try {
            QueueWorkInfoModel.QueueWorkShopLegalSubjectData data = QueueWorkInfoModel.QueueWorkShopLegalSubjectData.newBuilder()
                    .setContactNumber(input.getContactNumber())
                    .setContactPerson(input.getUsername())
                    .setLegalSubjectId(legalSubjectId)
                    .setName(input.getName())
                    .setPhone(input.getPhone())
                    .setType(type)
                    .build();
            QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.SHOP_LEGAL_SUBJECT)
                    .setShopLegalSubjectData(data)
                    .build();
            MQWorkSender.shareSender().send(workInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
