package com.zwdbj.server.common.mq;

import com.rabbitmq.client.*;
import com.zwdbj.server.config.settings.RabbitmqConfigs;
import com.zwdbj.server.utility.common.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

public class MQConnection implements ShutdownListener {
    protected ConnectionFactory connectionFactory;
    protected Connection connection;
    protected Channel channel;
    protected Timer timer;
    protected TimerTask task;

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected void connect() {
        try {
            RabbitmqConfigs mqConfig = SpringContextUtil.getBean(RabbitmqConfigs.class);
            this.connectionFactory = new ConnectionFactory();
            this.connectionFactory.setUsername(mqConfig.getUsername());
            this.connectionFactory.setPassword(mqConfig.getPassword());
            this.connectionFactory.setHost(mqConfig.getHost());
            this.connectionFactory.setPort(mqConfig.getPort());
            try {
                this.connection = this.connectionFactory.newConnection();
                this.connection.addShutdownListener(this);
                this.channel = this.connection.createChannel();
            } catch (TimeoutException ex) {
                logger.error("[MQ]"+ex.getStackTrace());
                logger.error("[MQ]"+ex.getMessage());
                asyncWaitAndReConnect();
            }
        } catch (IOException ex) {
            logger.error("[MQ]"+ex.getStackTrace());
            logger.error("[MQ]"+ex.getMessage());
            asyncWaitAndReConnect();
        }
    }


    @Override
    public void shutdownCompleted(ShutdownSignalException cause) {
        if (!cause.isInitiatedByApplication()) {
            logger.info("[MQ]Lost connection to "+this.connectionFactory.getHost()+":"+this.connectionFactory.getPort()+":::"+cause);
            this.connection = null;
            asyncWaitAndReConnect();
        }
    }

    protected void asyncWaitAndReConnect() {
        try {
            if (this.connection!=null&&this.connection.isOpen()) {
                this.timer.cancel();
                this.task.cancel();
                this.timer = null;
                this.task = null;
                return;
            }
            if (timer!=null) {
                this.timer.cancel();
                this.task.cancel();
            }
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    if (connection!=null&&connection.isOpen()) {
                        timer.cancel();
                        task.cancel();
                        timer = null;
                        task = null;
                    } else {
                        connect();
                    }
                }
            };
            timer.schedule(task,3*1000,3 * 1000);
        }catch (Exception e){
            logger.info("MQ异步异常:"+e.getMessage());
        }

    }
}
