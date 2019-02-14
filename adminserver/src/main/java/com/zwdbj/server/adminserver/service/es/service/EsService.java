package com.zwdbj.server.adminserver.service.es.service;

import com.alibaba.fastjson.JSON;
import com.zwdbj.server.utility.model.ServiceStatusInfo;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EsService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private Logger logger = LoggerFactory.getLogger(EsService.class);

    //添加单个文档
    public ServiceStatusInfo<Long> index(Object data, String index, String type, String id) {
        try {

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
        } catch (Exception e) {
            logger.error("创建文档到es失败" + e.getMessage());
            return new ServiceStatusInfo<>(1, "创建文档出错" + e.getMessage(), 1L);
        }
    }

//    //更新文档
//    public ServiceStatusInfo<Long> update()


}
