package com.zwdbj.server.mobileapi.config;

import com.alibaba.fastjson.JSON;
import com.zwdbj.server.mobileapi.service.user.service.UserService;
import com.zwdbj.server.mobileapi.service.video.service.VideoService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
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
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("初始化es");
//        createVideo();
//        createUser();
    }

    /**
     * 创建视频索引和数据
     * @throws IOException
     */
    private void createVideo() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices("video");
        getIndexRequest.local(false);
        getIndexRequest.humanReadable(true);
        if(restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT)){
            return ;
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest();
        createIndexRequest.index("video");
        Map<String,Object> mapping = new HashMap<>();
        Map<String,Object> properties = new HashMap<>();

        Map<String,Object> longt = new HashMap<>();
        longt.put("type","long");

        Map<String,Object> booleant = new HashMap<>();
        booleant.put("type","boolean");

        Map<String,Object> text = new HashMap<>();
        text.put("type","text");

        Map<String,Object> date = new HashMap<>();
        date.put("type","date");
        date.put("format","yyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");

        Map<String,Object> geo_point = new HashMap<>();
        geo_point.put("type","geo_point");

        Map<String,Object> ik_max_word = new HashMap<>();
        ik_max_word.put("type","text");
        ik_max_word.put("analyzer","ik_max_word");

        properties.put("id",longt);
        properties.put("createTime",date);
        properties.put("title",ik_max_word);
        properties.put("coverImageUrl",text);
        properties.put("coverImageWidth",text);
        properties.put("coverImageHeight",text);
        properties.put("firstFrameUrl",text);
        properties.put("firstFrameWidth",text);
        properties.put("firstFrameHeight",text);
        properties.put("videoUrl",text);
        properties.put("linkPets",text);
        properties.put("tags",ik_max_word);
        properties.put("longitude",text);
        properties.put("latitude",text);
        properties.put("location",geo_point);
        properties.put("address",text);
        properties.put("heartCount",longt);

        properties.put("userId",longt);
        properties.put("username",ik_max_word);
        properties.put("nickName",text);
        properties.put("avatarUrl",text);

        properties.put("type",text);
        mapping.put("properties",properties);
        createIndexRequest.mapping("doc",mapping);
        restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        BulkRequest bulkRequest = new BulkRequest();
        createIndexRequest("video","doc",videoService.selectAll(),bulkRequest);
        restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
    }

    /**
     * 创建用户索引和数据
     * @throws IOException
     */
    private void createUser() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices("user");
        getIndexRequest.local(false);
        getIndexRequest.humanReadable(true);
        if(restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT)){
            return ;
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest();
        createIndexRequest.index("user");
        Map<String,Object> mapping = new HashMap<>();
        Map<String,Object> properties = new HashMap<>();

        Map<String,Object> longt = new HashMap<>();
        longt.put("type","long");

        Map<String,Object> booleant = new HashMap<>();
        booleant.put("type","boolean");

        Map<String,Object> text = new HashMap<>();
        text.put("type","text");

        Map<String,Object> date = new HashMap<>();
        date.put("type","date");
        date.put("format","yyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis");

        Map<String,Object> geo_point = new HashMap<>();
        geo_point.put("type","geo_point");

        Map<String,Object> ik_max_word = new HashMap<>();
        ik_max_word.put("type","text");
        ik_max_word.put("analyzer","ik_max_word");

        properties.put("id",longt);
        properties.put("createTime",date);
        properties.put("deleteTime",date);
        properties.put("isDeleted",booleant);
        properties.put("username",ik_max_word);
        properties.put("nickName",text);
        properties.put("avatarUrl",text);
        properties.put("password",text);
        properties.put("phone",text);
        properties.put("email",text);
        properties.put("birthday",date);
        properties.put("sex",text);
        properties.put("isLocked",booleant);
        properties.put("isEmailVerification",booleant);
        properties.put("isPhoneVerification",booleant);
        properties.put("isLivingOpen",booleant);
        properties.put("isLivingWatch",booleant);
        properties.put("isLiving",booleant);
        properties.put("livingId",longt);
        properties.put("totalHearts",text);
        properties.put("totalFans",text);
        properties.put("totalMyFocuses",date);
        properties.put("isSuper",booleant);
        properties.put("isReviewed",booleant);
        properties.put("loginType",longt);
        properties.put("thirdOpenId",text);
        properties.put("longitude",text);
        properties.put("latitude",text);
        properties.put("address",text);
        properties.put("complainCount",longt);
        properties.put("isManager",booleant);
        properties.put("occupationId",longt);
        properties.put("loveStatusId",longt);
        properties.put("hxUserName",text);
        properties.put("hxPwd",text);
        properties.put("isManualData",booleant);
        properties.put("tenantId",longt);
        properties.put("recommendUserId",longt);
        mapping.put("properties",properties);
        createIndexRequest.mapping("doc",mapping);
        restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        int page = 0;
        int row=100000;
        while (true){
            BulkRequest bulkRequest = new BulkRequest();
            List<Map<String,String>> list = userService.pageSelectAll(++page,row);
            if(list ==null || list.size()==0)
                return;
            createIndexRequest("user","doc",list,bulkRequest);
            restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
        }
    }

    /**
     * 创建数据
     * @param index
     * @param type
     * @param maps
     * @return
     */
    public BulkRequest createIndexRequest(String index,String type,List<Map<String,String>> maps,BulkRequest bulkRequest){
        for (Map map:maps) {
            IndexRequest indexRequest = new IndexRequest(index, type);
            indexRequest.source(JSON.toJSONString(map),XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        return bulkRequest;
    }
}
