package com.zwdbj.server.adminserver.service.heart.service;

import com.zwdbj.server.adminserver.service.heart.mapper.IHeartMapper;
import com.zwdbj.server.adminserver.service.heart.model.HeartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartService {
    @Autowired
    IHeartMapper heartMapper;

    public long heart(long id,long userId,long resourceOwnerId,int type) {
        return this.heartMapper.heart(id, userId, resourceOwnerId,type);
    }

    public long unHeart(long userId,long resourceOwnerId) {
        return this.heartMapper.unHeart(userId, resourceOwnerId);
    }

    public HeartModel findHeart(long userId,long resourceOwnerId) {
        return this.heartMapper.findHeart(userId, resourceOwnerId);
    }
}
