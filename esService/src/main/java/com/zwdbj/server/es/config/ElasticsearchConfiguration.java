package com.zwdbj.server.es.config;

import com.zwdbj.server.config.settings.ElasticsearchConfigs;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticsearchConfiguration implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {
    private static final Logger LOG = LoggerFactory.getLogger(ElasticsearchConfiguration.class);

    @Autowired
    private ElasticsearchConfigs elasticsearchConfigs;

    private RestHighLevelClient restHighLevelClient;

    /**
     * 控制Bean的实例化过程
     *
     * @return
     * @throws Exception
     */
    @Override
    public RestHighLevelClient getObject() throws Exception {
        return restHighLevelClient;
    }

    /**
     * 获取接口返回的实例的class
     *
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public void destroy() throws Exception {
        try {
            if (restHighLevelClient != null) {
                restHighLevelClient.close();
            }
        } catch (final Exception e) {
            LOG.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        restHighLevelClient = buildClient();
    }

    private RestHighLevelClient buildClient() {
        try {
            String[] clusterNodes = elasticsearchConfigs.getClusterNodes().split(",");
            HttpHost[] httpHosts = new HttpHost[clusterNodes.length];
            for (int i = 0; i < clusterNodes.length; i++) {
                httpHosts[i] = new HttpHost(clusterNodes[i].split(":")[0],Integer.parseInt(clusterNodes[i].split(":")[1]),
                        "http");
            }
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(httpHosts));
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return restHighLevelClient;
    }
}
