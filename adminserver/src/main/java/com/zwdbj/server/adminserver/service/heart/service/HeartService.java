package com.zwdbj.server.adminserver.service.heart.service;

import com.zwdbj.server.adminserver.service.heart.mapper.IHeartMapper;
import com.zwdbj.server.adminserver.service.heart.model.HeartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartService {
    @Autowired
    IHeartMapper heartMapper;

    public HeartModel findHeart(long userId,long resourceOwnerId) {
        return this.heartMapper.findHeart(userId, resourceOwnerId);
    }
}
