package com.zwdbj.server.es.service;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ES操作
 */
@Component
public class ESService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     * @param mapping 自定义索引map
     * @param index 创建索引的名称
     * @param type 类型
     * @return
     * @throws IOException
     */
    public void createIndex(Map<String,Object> mapping , String index ,String type) throws IOException {
        if( !isIndex(index)){
            return;
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest();
        createIndexRequest.index(index);
        createIndexRequest.mapping(type, mapping);
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 创建索引,并且批量添加数据
     * @param mapping 自定义索引map
     * @param data 导入的数据
     * @param index 创建索引的名称
     * @param type 类型
     * @param idKey 自定义ID,取自data中的哪个key
     * @throws IOException
     */
    public void createIndexImportData(Map<String,Object> mapping , List<Map<String,String>> data , String index ,String type, String idKey) throws IOException {
        if( !isIndex(index)){
            return;
        }
        CreateIndexRequest createIndexRequest = new CreateIndexRequest();
        createIndexRequest.index(index);
        createIndexRequest.mapping(type, mapping);
        restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        BulkRequest bulkRequest = new BulkRequest();
        createIndexRequest(index, type, idKey, data, bulkRequest);
        restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
    }

    /**
     * 更新数据 根据ID
     * @param index
     * @param type
     * @param idKey
     * @param map
     * @throws IOException
     */
    public void updateIndexData(String index ,String type, String idKey, Map<String,String> map) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index,type,idKey);
        indexRequest.source(JSON.toJSONString(map), XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除数据
     * @param index
     * @param type
     * @param idKey
     */
    public void deleteIndexData(String index ,String type, String idKey) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, idKey);
        restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
    }

    /**
     * 删除深度分页ID
     * @param scroll_id
     * @return
     */
    public void closeScroll(String scroll_id) throws IOException {
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scroll_id);
        restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
    }

    /**
     * 判断索引是否存在
     * @param index
     * @return
     */
    public boolean isIndex(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(index);
        getIndexRequest.local(false);
        getIndexRequest.humanReadable(true);
        if(restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT)){
            return false;
        }
        return true;
    }

    /**
     * 创建数据
     * @param index
     * @param type
     * @param idKey
     * @param maps
     * @param bulkRequest
     * @return
     */
    private BulkRequest createIndexRequest(String index,String type, String idKey,List<Map<String,String>> maps,BulkRequest bulkRequest){
        for (Map map:maps) {
            IndexRequest indexRequest = new IndexRequest(index, type, String.valueOf(map.get(idKey)));
            indexRequest.source(JSON.toJSONString(map), XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        return bulkRequest;
    }

}
