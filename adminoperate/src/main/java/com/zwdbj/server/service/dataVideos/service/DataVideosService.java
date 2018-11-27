package com.zwdbj.server.service.dataVideos.service;

import com.zwdbj.server.service.dataVideos.mapper.IDataVideosMapper;
import com.zwdbj.server.service.dataVideos.model.DataVideosDto;
import com.zwdbj.server.utility.common.UniqueIDCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class DataVideosService {
    @Autowired
    IDataVideosMapper dataVideosMapper;

    public int getDataVideos(){
        return  this.dataVideosMapper.getDataVideos();
    }
    public DataVideosDto getOneDataVideo1(){
        DataVideosDto dataVideosDto = this.dataVideosMapper.getOneDataVideo1();
        String title = dataVideosDto.getTitle();
        Pattern r = Pattern.compile("@");
        title = r.split(title)[0];
        Pattern r1 = Pattern.compile("抖音小助手");
        title = r1.split(title)[0];
        if (title.length()==0)title= UniqueIDCreater.generatePhoneCode().substring(1,3);
        dataVideosDto.setTitle(title);
        dataVideosDto.setFirstFrameUrl(dataVideosDto.getCoverImageUrl());
        dataVideosDto.setFirstFrameHeight(dataVideosDto.getCoverImageHeight());
        dataVideosDto.setFirstFrameWidth(dataVideosDto.getCoverImageWidth());
        return  dataVideosDto;
    }

    public DataVideosDto getOneDataVideo2(){
        DataVideosDto dataVideosDto = this.dataVideosMapper.getOneDataVideo2();
        String title = dataVideosDto.getTitle();
        Pattern r = Pattern.compile("@");
        title = r.split(title)[0];
        Pattern r1 = Pattern.compile("抖音小助手");
        title = r1.split(title)[0];
        if (title.length()==0)title= UniqueIDCreater.generatePhoneCode().substring(1,3);
        dataVideosDto.setTitle(title);
        dataVideosDto.setFirstFrameUrl(dataVideosDto.getCoverImageUrl());
        dataVideosDto.setFirstFrameHeight(dataVideosDto.getCoverImageHeight());
        dataVideosDto.setFirstFrameWidth(dataVideosDto.getCoverImageWidth());
        return  dataVideosDto;
    }


    public void updateDataVideoStatus(String id){
        this.dataVideosMapper.updateDataVideoStatus(id);
    }

    public int getTopVideosNum(){
        return this.dataVideosMapper.getTopVideosNum();
    }

}
