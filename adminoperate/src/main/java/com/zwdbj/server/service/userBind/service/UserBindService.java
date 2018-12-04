package com.zwdbj.server.service.userBind.service;

import com.zwdbj.server.service.userBind.mapper.IUserBindMapper;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserBindService {
    @Autowired
    IUserBindMapper userBindMapper;
    public void newThirdBind(long userId,String thirdOpenId,int type,String accessToken,String nickName){
        long id = UniqueIDCreater.generateID();
        this.userBindMapper.newThirdBind(id,userId,thirdOpenId,type,accessToken,nickName);
    }
    public void newThirdBind2(long userId, String thirdOpenId, int type, String accessToken, String nickName, Date createTime){
        long id = UniqueIDCreater.generateID();
        this.userBindMapper.newThirdBind2(id,userId,thirdOpenId,type,accessToken,nickName,createTime);
    }

}
