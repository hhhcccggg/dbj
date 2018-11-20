package com.zwdbj.server.service.pet.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IPetMapper {

    @Insert("insert into core_pets(id,avatar,nickName,categoryId,userId,isManualData) values(#{id}," +
            "#{avatar},#{nickName},#{categoryId},#{userId},true)")
    int newPet(@Param("id")long id,@Param("avatar")String avatar,@Param("userId")long userId,@Param("nickName")String nickName,@Param("categoryId")long categoryId);

}
