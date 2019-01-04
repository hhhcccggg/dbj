package com.zwdbj.server.adminserver.service.shop.service.receiveAddress.mapper;

import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressAddInput;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressModel;
import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressModifyInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IReceiveAddressMapper {
    @Select("select * from shop_receiveAddresses where isDeleted=0")
    List<ReceiveAddressModel> findAllReceiveAddresses();
    @Select("select * from shop_receiveAddresses where id=#{id} and isDeleted=0")
    ReceiveAddressModel getReceiveAddressById(@Param("id") long id);
    @Insert("insert into shop_receiveAddresses(id,receiverName,receiverPhone,receiverMobile,reveiverState,receiverCity," +
             "receiverCountry,receiverAddress,receiverZip,cityId,cityLevel,updateTime,isDefault) values(#{id},#{input.receiverName}," +
            "#{input.receiverPhone},#{input.receiverMobile},#{input.reveiverState},#{input.receiverCity}," +
            "#{input.receiverCountry},#{input.receiverAddress},#{input.receiverZip},#{input.cityId},#{input.cityLevel}," +
            "#{input.updateTime}),#{input.isDefault}")
    int addReceiveAddress(@Param("id") long id, @Param("input") ReceiveAddressAddInput input);
    @Update("update shop_receiveAddresses set receiverName=#{input.receiverName}," +
            "receiverPhone=#{input.receiverPhone},receiverMobile=#{input.receiverMobile},reveiverState=#{input.reveiverState}," +
            "receiverCity=#{input.receiverCity},receiverCountry=#{input.receiverCountry},receiverAddress=#{input.receiverAddress}," +
            "receiverZip=#{input.receiverZip},cityId=#{input.cityId},cityLevel=#{input.cityLevel}," +
            "updateTime=#{input.updateTime},isDefault=#{input.isDefault} where id=#{id}")
    int modifyReceiveAddress(@Param("id") long id, @Param("input") ReceiveAddressModifyInput input);
    @Update("update shop_receiveAddresses set deleteTime=true,deleteTime=now() where id=#{id}")
    int notRealDeleteReceiveAddress(@Param("id") long id);
}
