package com.zwdbj.server.mobileapi.service.es.service;


import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * ES常规操作
 */
@Service
public class ESService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 删除深度分页ID
     * @param scroll_id
     * @return
     */
    public void closeScroll(String scroll_id) {
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        try {
            clearScrollRequest.addScrollId(scroll_id);
            restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
