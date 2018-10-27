package com.zwdbj.server.mobileapi.service.music.service;

import com.zwdbj.server.mobileapi.service.music.mapper.IMusicMapper;
import com.zwdbj.server.mobileapi.service.music.model.*;
import com.zwdbj.server.mobileapi.service.qiniu.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService {
    @Autowired
    private IMusicMapper musicMapper;
    @Autowired
    protected QiniuService qiniuService;
    public List<MusicModel> list() {
        return musicMapper.list();
    }

    public MusicDto get(long id) {
        return musicMapper.get(id);
    }
}
