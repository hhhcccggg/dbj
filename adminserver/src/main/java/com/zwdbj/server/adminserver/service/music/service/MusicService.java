package com.zwdbj.server.adminserver.service.music.service;

import com.zwdbj.server.adminserver.service.music.mapper.IMusicMapper;
import com.zwdbj.server.adminserver.service.music.model.*;
import com.zwdbj.server.adminserver.service.qiniu.service.QiniuService;
import com.zwdbj.server.adminserver.shiro.JWTUtil;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
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

    public List<AdMusicDto> videoMusicListAd(AdMusicInput input){
        List<AdMusicDto> musicDtos = this.musicMapper.videoMusicListAd(input);
        return musicDtos;
    }

    public Long addVideoMusicAd(AdNewMusicInput input){
        input.setMusicUrl(qiniuService.url(input.getMusicUrl()));
        input.setCoverUrl(qiniuService.url(input.getCoverUrl()));
        Long musicId =UniqueIDCreater.generateID();
        Long userId = JWTUtil.getCurrentId();
        if (userId <= 0) return 0L;
        this.musicMapper.addVideoMusicAd(musicId,userId,input);
        return musicId;
    }
}
