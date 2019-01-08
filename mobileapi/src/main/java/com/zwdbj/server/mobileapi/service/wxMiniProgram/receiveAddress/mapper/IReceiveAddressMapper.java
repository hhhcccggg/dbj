package com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.mapper;

import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressInput;
import com.zwdbj.server.mobileapi.service.wxMiniProgram.receiveAddress.model.ReceiveAddressModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IReceiveAddressMapper {

    /**
     * 新增
     * @param id
     * @param input
     * @return
     */
    @Insert("insert into shop_receiveAddresses(id,receiverName,receiverPhone,receiverMobile,reveiverState,receiverCity," +
            "receiverCountry,receiverAddress,receiverZip,cityId,cityLevel,updateTime,defaultAddr,userId) values(#{id},#{input.receiverName}," +
            "#{input.receiverPhone},#{input.receiverMobile},#{input.reveiverState},#{input.receiverCity}," +
            "#{input.receiverCountry},#{input.receiverAddress},#{input.receiverZip},#{input.cityId},#{input.cityLevel}," +
            "now(),#{input.defaultAddr},#{input.userId})")
    long createReceiveAddress(@Param("id") long id, @Param("input") ReceiveAddressInput input);

    /**
     * 修改
     * @param input
     * @return
     */
    @Update("update shop_receiveAddresses set receiverName=#{input.receiverName}," +
            "receiverPhone=#{input.receiverPhone},receiverMobile=#{input.receiverMobile},reveiverState=#{input.reveiverState}," +
            "receiverCity=#{input.receiverCity},receiverCountry=#{input.receiverCountry},receiverAddress=#{input.receiverAddress}," +
            "receiverZip=#{input.receiverZip},cityId=#{input.cityId},cityLevel=#{input.cityLevel}," +
            "updateTime=now(),defaultAddr=#{input.defaultAddr} where id=#{id} and userId=#{input.userId} and isDeleted=0")
    long updateReceiveAddress(@Param("input") ReceiveAddressInput input,@Param("id") long id);

    /**
     * 刪除
     * @param id
     * @param userId
     * @return
     */
    @Update("update shop_receiveAddresses set isDeleted=1,deleteTime=now() where id=#{id} and userId=#{userId} and isDeleted=0")
    long deleteReceiveAddress(@Param("id")long id , @Param("userId") long userId);

    /**
     * 取消所有默認地址
     * @param userId
     * @return
     */
    @Update("update shop_receiveAddresses set defaultAddr=0 where userId=#{userId} and isDeleted=0")
    long updateCancelAllDefalue(@Param("userId") long userId);

    /**
     * 设置默认地址
     * @param id
     * @param userId
     * @return
     */
    @Update("update shop_receiveAddresses set defaultAddr=1 where id=#{id} and userId=#{userId} and isDeleted=0")
    int updateDefalue(@Param("id")long id , @Param("userId") long userId);

    /**
     * 查询有效收货地址
     * @param userId
     * @return
     */
    @Select("select * from shop_receiveAddresses where userId=#{userId} and isDeleted=0 order by defaultAddr desc")
    List<ReceiveAddressModel> selectUserId(@Param("userId") long userId);

    /**
     * 根据ID查询
     * @param userId
     * @return
     */
    @Select("select * from shop_receiveAddresses where userId=#{userId} and isDeleted=0 and id = #{id}")
    ReceiveAddressModel selectById(@Param("id") long id , @Param("userId") long userId);
}
