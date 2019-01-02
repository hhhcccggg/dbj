package com.zwdbj.server.shop_admin_service.mq;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MQConfig {
    /**
     * 比如耗时的操作队列，比如发送验证码等操作
     */
    public static final String queueTimeConsuming = "queue_timeConsuming";
    /**
     * 数据状态交换机
     */
    public static final String exchangeTimeMachine = "exchange_timeMachine";

    @Value("${spring.rabbitmq.host}")
    protected String host;
    @Value("${spring.rabbitmq.port}")
    protected int port;
    @Value("${spring.rabbitmq.username}")
    protected String username;
    @Value("${spring.rabbitmq.password}")
    protected String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
