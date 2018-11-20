package com.zwdbj.server.service.user.service;

import com.zwdbj.server.service.user.mapper.IUserMapper;
import com.zwdbj.server.utility.common.SHAEncrypt;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserMapper userMapper;
    public void newVestUser(String phone,String avatarUrl,String nickName){
        try {
            Long id = UniqueIDCreater.generateID();
            String userName = UniqueIDCreater.generateUserName();
            String password = SHAEncrypt.encryptSHA("123456");
            this.userMapper.newVestUser(phone,id,password,userName,avatarUrl,nickName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Long> getVestUserIds1(){
        return this.userMapper.getVestUserIds1();
    }
    public List<Long> getVestUserIds2(){
        return this.userMapper.getVestUserIds2();
    }
    public Long everyIncreasedUsers(){
        return this.userMapper.everyIncreasedUsers();
    }

    public void updateField(String fields,long id) {
        this.userMapper.updateField(fields,id);
    }

}


