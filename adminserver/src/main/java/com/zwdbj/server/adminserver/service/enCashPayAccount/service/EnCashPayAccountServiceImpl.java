package com.zwdbj.server.adminserver.service.enCashPayAccount.service;

import com.zwdbj.server.adminserver.service.enCashPayAccount.mapper.EnCashPayAccountMapper;
import com.zwdbj.server.adminserver.service.enCashPayAccount.model.EnCashPayAccount;
import com.zwdbj.server.adminserver.service.enCashPayAccount.model.ModifyEnCashPayAccount;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class EnCashPayAccountServiceImpl implements EnCashPayAccountService {
    @Resource
    private EnCashPayAccountMapper enCashPayAccountMapper;


    @Override
    public ServiceStatusInfo<Long> update(Long userId, ModifyEnCashPayAccount enCashPayAccount) {
        Long result = 0L;
        try {
            result = this.enCashPayAccountMapper.update(userId, enCashPayAccount);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改提现第三方支付账号失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteByUserId(Long userId) {
        Long result = 0L;
        try {
            result = this.enCashPayAccountMapper.deleteByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除提现第三方支付账号失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> create(EnCashPayAccount enCashPayAccount) {
        Long result = 0L;
        Long id = UniqueIDCreater.generateID();
        try {
            result = this.enCashPayAccountMapper.create(id, enCashPayAccount);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建提现第三方支付账号失败" + e.getMessage(), result);
        }

    }

    @Override
    public ServiceStatusInfo<EnCashPayAccount> findByUserId(Long userId) {
        EnCashPayAccount result = null;
        try {
            result = this.enCashPayAccountMapper.findByUserId(userId);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过用户id查找提现第三方支付账号失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<EnCashPayAccount>> findAll() {
        List<EnCashPayAccount> result = null;
        try {
            result = this.enCashPayAccountMapper.findAll();
            return new ServiceStatusInfo<>(0, "", result);

        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查找提现第三方支付账号失败" + e.getMessage(), result);
        }
    }
}
