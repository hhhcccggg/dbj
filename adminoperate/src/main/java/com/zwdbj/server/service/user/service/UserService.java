package com.zwdbj.server.service.user.service;

import com.zwdbj.server.operate.oprateService.OperateService;
import com.zwdbj.server.service.user.mapper.IUserMapper;
import com.zwdbj.server.service.user.model.UserDayGrowth;
import com.zwdbj.server.service.userBind.service.UserBindService;
import com.zwdbj.server.utility.common.SHAEncrypt;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserMapper userMapper;
    @Autowired
    OperateService operateService;
    @Autowired
    UserBindService userBindService;

    public void newVestUser(String phone, String avatarUrl, String nickName) {
        try {
            Long id = UniqueIDCreater.generateID();
            String userName = UniqueIDCreater.generateUserName();
            String password = SHAEncrypt.encryptSHA("123456");
            this.userMapper.newVestUser(phone, id, password, userName, avatarUrl, nickName);
            this.operateService.newPet(id);
            int a = this.operateService.getRandom(0, 2);
            this.operateService.newDeviceToken(id, a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newThirdUsers(String phone, String avatarUrl, String nickName, String thirdOpenId, int device, int type, String accessToken) {
        try {
            Long id = UniqueIDCreater.generateID();
            String userName = UniqueIDCreater.generateUserName();
            this.userMapper.newThirdUsers(id, userName, phone, avatarUrl, nickName, type, thirdOpenId);
            this.userBindService.newThirdBind(id, thirdOpenId, type, accessToken, nickName);
            this.operateService.newPet(id);
            this.operateService.newDeviceToken(id, device);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void newThirdUsers2(String phone, String avatarUrl, String nickName, String thirdOpenId, int device, int type, String accessToken, Date createTime) {
        try {
            Long id = UniqueIDCreater.generateID();
            String userName = UniqueIDCreater.generateUserName();
            this.userMapper.newThirdUsers2(id, userName, phone, avatarUrl, nickName, type, thirdOpenId, createTime);
            this.userBindService.newThirdBind2(id, thirdOpenId, type, accessToken, nickName, createTime);
            this.operateService.newPet(id);
            this.operateService.newDeviceToken(id, device);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Long> getVestUserIds1() {
        return this.userMapper.getVestUserIds1();
    }

    public List<Long> getVestUserIds2() {
        return this.userMapper.getVestUserIds2();
    }

    public Long everyIncreasedUsers() {
        return this.userMapper.everyIncreasedUsers();
    }

    public void updateField(String fields, long id) {
        this.userMapper.updateField(fields, id);
    }

    public List<Long> getNoFollowersUser() {
        return this.userMapper.getNoFollowersUser();
    }

    public boolean phoneIsExit(String phone) {
        int a = this.userMapper.phoneIsExist(phone);
        return a > 0;
    }

    public List<Long> getNullPhone() {
        return this.userMapper.getNullPhone();
    }

    public long userGrowth() {
        return this.userMapper.userGrowthAd();
    }

    public int userDayGrowthed(Date date) {
        return this.userMapper.userDayGrowthed(date);
    }
}


