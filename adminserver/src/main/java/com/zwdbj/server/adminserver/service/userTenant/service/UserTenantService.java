package com.zwdbj.server.adminserver.service.userTenant.service;

import com.zwdbj.server.adminserver.middleware.mq.MQWorkSender;
import com.zwdbj.server.adminserver.service.user.service.UserService;
import com.zwdbj.server.adminserver.service.userTenant.mapper.IUserTenantMapper;
import com.zwdbj.server.adminserver.service.userTenant.model.UserTenantInput;
import com.zwdbj.server.probuf.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTenantService {
    @Autowired
    IUserTenantMapper userTenantMapper;
    @Autowired
    UserService userService;
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
            QueueWorkInfoModel.QueueWorkShopLegalSubjectData data = QueueWorkInfoModel.QueueWorkShopLegalSubjectData.newBuilder()
                    .setContactNumber(input.getContactNumber())
                    .setContactPerson(input.getUsername())
                    .setLegalSubjectId(legalSubjectId)
                    .setName(input.getName())
                    .setPhone(input.getPhone())
                    .build();
            QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                    .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.SHOP_LEGAL_SUBJECT)
                    .setShopLegalSubjectData(data)
                    .build();
            MQWorkSender.shareSender().send(workInfo);
            return  new ServiceStatusInfo<>(0,"租户创建成功",1);
        }catch (Exception e){
            return  new ServiceStatusInfo<>(1,"租户创建出现异常"+e.getMessage(),null);
        }

    }

}
