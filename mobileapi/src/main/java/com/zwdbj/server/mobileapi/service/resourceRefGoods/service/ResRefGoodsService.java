package com.zwdbj.server.mobileapi.service.resourceRefGoods.service;

import com.zwdbj.server.mobileapi.service.resourceRefGoods.mapper.IResRefGoodsMapper;
import com.zwdbj.server.mobileapi.utility.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResRefGoodsService {
    @Autowired
    IResRefGoodsMapper resRefGoodsMapper;
    public String getGoods(long resId) {
        return this.resRefGoodsMapper.getGoods(resId);
    }
    public void update(long resId,String goods) {
        this.resRefGoodsMapper.update(resId,goods);
    }
    public void add(long resId,String goods,int type) {
        long id=UniqueIDCreater.generateID();
        this.resRefGoodsMapper.add(id,resId,goods,type);
    }
}
