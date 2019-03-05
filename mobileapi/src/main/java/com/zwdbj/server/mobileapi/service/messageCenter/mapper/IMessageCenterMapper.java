package com.zwdbj.server.mobileapi.service.messageCenter.mapper;

import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageDispatchInput;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageInfoDetailDto;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageInfoDto;
import com.zwdbj.server.mobileapi.service.messageCenter.model.MessageInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IMessageCenterMapper {
    @Insert("INSERT INTO `core_messages` (`id`," +
            "`creatorUserId`," +
            "`msgContent`," +
            "`messageType`," +
            "`refUrl`," +
            "`dataContent`) VALUES (" +
            "#{input.id}," +
            "#{input.creatorUserId}," +
            "#{input.msgContent}," +
            "#{input.messageType}," +
            "#{input.refUrl}," +
            "#{input.dataContent})")
    long pushMessage(@Param("input") MessageInput input);
    @Insert("INSERT INTO `core_messageDispatchs` (`id`," +
            "`refMessageId`," +
            "`receivedUserId`," +
            "`status`) VALUES (" +
            "#{input.id}," +
            "#{input.refMessageId}," +
            "#{input.receivedUserId}," +
            "#{input.status})")
    long pushMessageDispatch(@Param("input") MessageDispatchInput input);
    @Insert("insert into core_messageBroadcasts(receivedUserId,lastMessageId) values(#{userId},#{messageId})")
    long pushMessageBroadcast(@Param("userId") long userId,@Param("messageId") long messageId);
    @Select("select lastMessageId from core_messageBroadcasts where receivedUserId=#{userId}")
    Long messageUserBroadcast(@Param("userId") long userId);
    @Select("select max(id) from core_messages where messageType=0")
    Long maxMessageBroadcastId();
    @Select("select * from core_messages where messageType=0 order by id desc")
    List<MessageInfoDto> systemMessage();
    @Select("select count(*) from core_messageDispatchs as dispatch1 " +
            "inner join core_messages as msg on dispatch1.refMessageId = msg.id where dispatch1.receivedUserId=#{userId}" +
            " and dispatch1.status=0 and msg.messageType=#{type}")
    long getUnReadCountPersonal(@Param("userId") long userId,@Param("type") int type);
    @Select("select count(*) from core_messages where id>#{id} and messageType=0")
    long getUnReadCountBroadcast(@Param("id") long messageId);
    @Update("update core_messageDispatchs as dis inner join core_messages as msg" +
            " on dis.refMessageId=msg.id set dis.status=1 where dis.receivedUserId=#{userId} and msg.messageType=#{type}")
    long readAllPersonal(@Param("userId") long userId,@Param("type") int type);
    @Update("update core_messageBroadcasts set lastMessageId=#{messageId} where receivedUserId=#{userId}")
    long readAllBroadcast(@Param("userId") long userId,@Param("messageId") long messageId);
    @Select("select m.*,md.status as status from core_messages m left join core_messageDispatchs md on md.refMessageId=m.id  " +
            "where md.receivedUserId=#{userId} and m.messageType=#{type} order by m.createTime desc")
    List<MessageInfoDetailDto> getMyAllMessageByType(@Param("userId") long userId,@Param("type") int type);

}
