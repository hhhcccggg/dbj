package com.zwdbj.server.service.user.service;

import com.zwdbj.server.service.user.mapper.IUserMapper;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserMapper userMapper;
    public void newVestUser(String phone,String avatarUrl,String nickName){
        Long id = UniqueIDCreater.generateID();
        String userName = UniqueIDCreater.generateUserName();
        this.userMapper.newVestUser(phone,id,userName,avatarUrl,nickName);
    }

    public List<Long> getVestUserIds1(){
        return this.userMapper.getVestUserIds1();
    }
    public List<Long> getVestUserIds2(){
        return this.userMapper.getVestUserIds2();
    }

}


