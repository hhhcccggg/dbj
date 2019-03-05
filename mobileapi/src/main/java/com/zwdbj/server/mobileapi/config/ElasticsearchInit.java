package com.zwdbj.server.mobileapi.config;

import com.zwdbj.server.es.common.ESType;
import com.zwdbj.server.es.service.ESUtilService;
import com.zwdbj.server.mobileapi.service.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * es初始化
 */
@Component
public class ElasticsearchInit implements ApplicationRunner {

    @Autowired
    private ESUtilService esUtilService;

    @Autowired
    private VideoService videoService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        createVideo();
    }

    /**
     * 创建视频索引和数据
     * @throws IOException
     */
    private void createVideo() throws IOException {
        String index = "video";
        String type = "doc";
        if( !esUtilService.isIndex(index) ){
            return ;
        }
        Map<String,Object> mapping = new HashMap<>();
        Map<String,Object> properties = new HashMap<>();
        properties.put("id", ESType.getLongt());
        properties.put("createTime",ESType.getDate());
        properties.put("title",ESType.getIk_max_word());
        properties.put("coverImageUrl",ESType.getText());
        properties.put("coverImageWidth",ESType.getText());
        properties.put("coverImageHeight",ESType.getText());
        properties.put("firstFrameUrl",ESType.getText());
        properties.put("firstFrameWidth",ESType.getText());
        properties.put("firstFrameHeight",ESType.getText());
        properties.put("videoUrl",ESType.getText());
        properties.put("linkPets",ESType.getText());
        properties.put("tags",ESType.getIk_max_word());
        properties.put("longitude",ESType.getText());
        properties.put("latitude",ESType.getText());
        properties.put("location",ESType.getGeo_point());
        properties.put("address",ESType.getText());
        properties.put("recommendIndex",ESType.getLongt());
        properties.put("playCount",ESType.getLongt());
        properties.put("commentCount",ESType.getLongt());
        properties.put("heartCount",ESType.getLongt());
        properties.put("shareCount",ESType.getLongt());
        properties.put("musicId",ESType.getLongt());
        properties.put("status",ESType.getLongt());
        properties.put("categoryId",ESType.getLongt());
        properties.put("linkProductCount",ESType.getLongt());
        properties.put("rejectMsg",ESType.getText());
        properties.put("userId",ESType.getLongt());
        properties.put("userNickName",ESType.getIk_max_word());
        properties.put("userAvatarUrl",ESType.getText());
        properties.put("type",ESType.getText());
        mapping.put("properties",properties);
        List<Map<String,String>> maps = videoService.selectES();
        esUtilService.createIndexImportData(mapping , maps , index , type , "id");
    }

}
