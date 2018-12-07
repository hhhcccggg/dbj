package com.zwdbj.server.adminserver.service.userCoinDetail.mapper;


import com.zwdbj.server.adminserver.service.userCoinDetail.model.UserCoinDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserCoinDetailMapper {

    @Select("select * from core_userCoinDetails ")
    List<UserCoinDetail> searchAll();

    @Select("select * from core_userCoinDetails where userId=#{userId}")
    UserCoinDetail searchByUserId(@Param("userId") Long userId);


}
