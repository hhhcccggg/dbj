package com.zwdbj.server.adminserver.service.pet.mapper;

import com.zwdbj.server.adminserver.service.pet.model.PetModelDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface IPetMapper {

    //审核相关
    @Update("update core_pets set avatar='http://pbl2sn6wt.bkt.clouddn.com/default_avatar.png' where id=#{id}")
    int updatePetAvatar(@Param("id")Long id);

    @Select("select * from core_pets where id=#{id}")
    PetModelDto get(@Param("id") long id);

}
