package com.zwdbj.server.adminserver.service.es.service;

import com.alibaba.fastjson.JSON;
import com.zwdbj.server.adminserver.service.shop.service.store.model.StoreInfo;
import com.zwdbj.server.basemodel.model.ServiceStatusInfo;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EsService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private Logger logger = LoggerFactory.getLogger(EsService.class);

    //添加单个文档
    public ServiceStatusInfo<Long> index(StoreInfo data, String index, String type, String id) throws IOException {

        IndexRequest request = new IndexRequest(index, type, id);
        request.opType(DocWriteRequest.OpType.CREATE);//指定操作类型为创建
        String jsonStr = JSON.toJSONString(data);
        request.source(jsonStr, XContentType.JSON);

        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            //处理首次创建文档的情况
            logger.info("创建文档到es成功");
            return new ServiceStatusInfo<>(0, "创建文档成功", 1L);
        }
        return new ServiceStatusInfo<>(1, "创建文档失败" + indexResponse.status().getStatus(), 1L);

    }

    //更新文档
    public ServiceStatusInfo<Long> update(Object data, String index, String type, String id) throws IOException {

        IndexRequest request = new IndexRequest(index, type, id);

        String jsonStr = JSON.toJSONString(data);
        request.source(jsonStr, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            logger.info("更新文档成功");
            return new ServiceStatusInfo<>(0, "更新文档成功", 1L);
        }
        return new ServiceStatusInfo<>(1, "更新文档失败" + indexResponse.status().getStatus(), 0L);


    }

    //删除文档
    public ServiceStatusInfo<Long> delete(String index, String type, String id) throws IOException {

        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            logger.info("删除文档成功");
            return new ServiceStatusInfo<>(0, "删除文档成功", 1L);

        }
        return new ServiceStatusInfo<>(1, "删除文档失败", 0L);


    }
}