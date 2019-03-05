package com.zwdbj.server.config.settings;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.es")
public class ElasticsearchConfigs {

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodes;


}