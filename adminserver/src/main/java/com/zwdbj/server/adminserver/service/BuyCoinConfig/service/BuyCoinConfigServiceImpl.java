package com.zwdbj.server.adminserver.service.BuyCoinConfig.service;

import com.zwdbj.server.adminserver.service.BuyCoinConfig.mapper.BuyCoinConfigMapper;
import com.zwdbj.server.adminserver.service.BuyCoinConfig.model.BuyCoinConfig;
import com.zwdbj.server.adminserver.service.BuyCoinConfig.model.BuyCoinConfigAdd;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class BuyCoinConfigServiceImpl implements BuyCoinConfigService {
    @Resource
    private BuyCoinConfigMapper buyCoinConfigMapper;

    @Override
    public ServiceStatusInfo<Long> create(BuyCoinConfigAdd buyCoinConfig) {
        Long id = UniqueIDCreater.generateID();
        Long result = 0L;
        try {
            result = this.buyCoinConfigMapper.create(id, buyCoinConfig);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "创建可选金币充值列表失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> update(BuyCoinConfig buyCoinConfig) {
        Long result = null;
        try {
            result = this.buyCoinConfigMapper.update(buyCoinConfig);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "修改可选金币充值列表失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<Long> deleteById(Long id) {
        Long result = null;
        try {
            result = this.buyCoinConfigMapper.deleteById(id);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "删除可选金币充值列表失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<BuyCoinConfig>> searchByType(String type) {
        List<BuyCoinConfig> result = null;
        try {
            result = this.buyCoinConfigMapper.searchByType(type);
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "查询可选金币充值列表失败" + e.getMessage(), result);
        }
    }

    @Override
    public ServiceStatusInfo<List<BuyCoinConfig>> searchAll() {
        List<BuyCoinConfig> result = null;
        try {
            result = this.buyCoinConfigMapper.searchAll();
            return new ServiceStatusInfo<>(0, "", result);
        } catch (Exception e) {
            return new ServiceStatusInfo<>(1, "通过id查询可选金币充值列表失败" + e.getMessage(), result);
        }
    }
}
