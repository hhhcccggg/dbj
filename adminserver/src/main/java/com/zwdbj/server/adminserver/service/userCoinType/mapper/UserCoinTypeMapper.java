package com.zwdbj.server.adminserver.service.userCoinType.mapper;


import com.zwdbj.server.adminserver.service.userCoinType.model.UserCoinType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserCoinTypeMapper {
    @Select("select * from core_userCoinTypes")
    List<UserCoinType> searchAll();

    @Select("select * from core_userCoinTypes where userId=#{userId}")
    UserCoinType searchByUserId(@Param("userId") Long userId);


}

