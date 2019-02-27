package com.zwdbj.server.adminserver.service.enCashPayAccount.service;

import com.zwdbj.server.adminserver.service.enCashPayAccount.model.EnCashPayAccount;
import com.zwdbj.server.adminserver.service.enCashPayAccount.model.ModifyEnCashPayAccount;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;

import java.util.List;

public interface EnCashPayAccountService {
    ServiceStatusInfo<Long> update(Long userId, ModifyEnCashPayAccount enCashPayAccount);

    ServiceStatusInfo<Long> deleteByUserId(Long userId);

    ServiceStatusInfo<Long> create(EnCashPayAccount enCashPayAccount);

    ServiceStatusInfo<EnCashPayAccount> findByUserId(Long userId);

    ServiceStatusInfo<List<EnCashPayAccount>> findAll();


}
