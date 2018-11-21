package com.zwdbj.server.service.dataVideos.service;

import com.zwdbj.server.service.dataVideos.mapper.IDataVideosMapper;
import com.zwdbj.server.service.dataVideos.model.DataVideosDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DataVideosService {
    @Autowired
    IDataVideosMapper dataVideosMapper;

    public int getDataVideos(){
        return  this.dataVideosMapper.getDataVideos();
    }
    public DataVideosDto getOneDataVideo(){
        DataVideosDto dataVideosDto = this.dataVideosMapper.getOneDataVideo();
        dataVideosDto.setFirstFrameUrl(dataVideosDto.getCoverImageUrl());
        dataVideosDto.setFirstFrameHeight(dataVideosDto.getCoverImageHeight());
        dataVideosDto.setFirstFrameWidth(dataVideosDto.getCoverImageWidth());
        return  dataVideosDto;
    }
    public void updateDataVideoStatus(String id){
        this.dataVideosMapper.updateDataVideoStatus(id);
    }
}
