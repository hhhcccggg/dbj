package com.zwdbj.server.adminserver.service.userAssets.mapper;


import com.zwdbj.server.adminserver.service.userAssets.model.UserAssets;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserAssetsMapper {
    @Select("select *  from core_userAssets where userId=#{userId} ")
    UserAssets searchByUserId(@Param("userId") Long userId);

    @Select("select * from core_userAssets ")
    List<UserAssets> searchAll();

}
