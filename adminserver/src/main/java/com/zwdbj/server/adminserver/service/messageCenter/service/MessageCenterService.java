package com.zwdbj.server.adminserver.service.messageCenter.service;

import com.zwdbj.server.adminserver.middleware.mq.MQWorkSender;
import com.zwdbj.server.adminserver.middleware.mq.QueueWorkInfoModel;
import com.zwdbj.server.adminserver.service.ServiceStatusInfo;
import com.zwdbj.server.adminserver.service.messageCenter.mapper.IMessageCenterMapper;
import com.zwdbj.server.adminserver.service.messageCenter.model.MessageDispatchInput;
import com.zwdbj.server.adminserver.service.messageCenter.model.MessageInfoDto;
import com.zwdbj.server.adminserver.service.messageCenter.model.MessageInput;
import com.zwdbj.server.adminserver.service.messageCenter.model.MessageUnReadDto;
import com.zwdbj.server.adminserver.utility.UniqueIDCreater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageCenterService {
    @Autowired
    protected IMessageCenterMapper messageCenterMapper;
    protected Logger logger = LoggerFactory.getLogger(MessageCenterService.class);

    /**
     * 消息推送
     * @param message
     * @param toUserId,如果是系统通知，toUserId传0
     * @return
     */
    @Transactional
    public ServiceStatusInfo<Object> push(MessageInput message,long toUserId) {
        //TODO 性能优化
        if (message.getMessageType()!=0 && toUserId ==0) {
            return new ServiceStatusInfo<>(1,"请指定需要推送的用户ID",null);
        }
        if (message.getMsgContent()==null || message.getMsgContent().length()==0) {
            message.setMsgContent("");
        }
        if (message.getRefUrl()==null || message.getRefUrl().length()==0) {
            message.setRefUrl("");
        }
        if (message.getDataContent()==null || message.getDataContent().length()==0) {
            message.setDataContent("");
        }
        long messageId = message.getId();
        if (messageId<=0) {
            messageId = UniqueIDCreater.generateID();
            message.setId(messageId);
        }
        if(message.getMessageType()==0) {
            this.messageCenterMapper.pushMessage(message);
        } else {
            long dispatchId = UniqueIDCreater.generateID();
            MessageDispatchInput dispatchInput = new MessageDispatchInput();
            dispatchInput.setStatus(0);
            dispatchInput.setReceivedUserId(toUserId);
            dispatchInput.setRefMessageId(messageId);
            dispatchInput.setId(dispatchId);
            this.messageCenterMapper.pushMessage(message);
            this.messageCenterMapper.pushMessageDispatch(dispatchInput);
        }
        // 进入消息队列，执行离线推送
        QueueWorkInfoModel.QueueWorkPush pushData = QueueWorkInfoModel.QueueWorkPush.newBuilder()
                .setCreatorUserId(message.getCreatorUserId())
                .setMessageType(message.getMessageType())
                .setMsgContent(message.getMsgContent())
                .setDataContent(message.getDataContent())
                .setRefUrl(message.getRefUrl())
                .setToUserId(toUserId)
                .setPushId(messageId)
                .build();
        QueueWorkInfoModel.QueueWorkInfo workInfo = QueueWorkInfoModel.QueueWorkInfo.newBuilder()
                .setWorkType(QueueWorkInfoModel.QueueWorkInfo.WorkTypeEnum.PUSH)
                .setPushData(pushData)
                .build();
        try {
            MQWorkSender.shareSender().send(workInfo);
        } catch (Exception ex) {
            logger.error("发送消息队列失败"+message.toString());
        }
        return new ServiceStatusInfo<>(0,"",null);
    }
    public MessageUnReadDto unRead(long userId) {
        //TODO 性能优化
        MessageUnReadDto readDto = new MessageUnReadDto();
        Long lastMessageId = this.messageCenterMapper.messageUserBroadcast(userId);
        if (lastMessageId == null) {
            readDto.setSystemCount(this.messageCenterMapper.getUnReadCountBroadcast(0));
        } else {
            readDto.setSystemCount(this.messageCenterMapper.getUnReadCountBroadcast(lastMessageId));
        }
        readDto.setCommentCount(this.messageCenterMapper.getUnReadCountPersonal(userId,3));
        readDto.setFollowerCount(this.messageCenterMapper.getUnReadCountPersonal(userId,2));
        readDto.setHeartCount(this.messageCenterMapper.getUnReadCountPersonal(userId,1));
        return readDto;
    }

    @Transactional
    public boolean readAll(long userId,int type) {
        if (type!=0) {
            this.messageCenterMapper.readAllPersonal(userId,type);
        } else {
            Long lastMessageId = this.messageCenterMapper.messageUserBroadcast(userId);
            if (lastMessageId==null) {
                this.messageCenterMapper.pushMessageBroadcast(userId,this.messageCenterMapper.maxMessageBroadcastId());
            } else {
                this.messageCenterMapper.readAllBroadcast(userId,this.messageCenterMapper.maxMessageBroadcastId());
            }
        }
        return true;
    }

    public List<MessageInfoDto> systemMessage(long userId,long lastMessageId) {
        this.readAll(userId,0);
        List<MessageInfoDto> dtos = this.messageCenterMapper.systemMessage(lastMessageId);
        return dtos;
    }

}
