package com.zwdbj.server.adminserver.service.pet.mapper;

import com.zwdbj.server.adminserver.model.EntityKeyModel;
import com.zwdbj.server.adminserver.service.pet.model.PetModelDto;
import com.zwdbj.server.adminserver.service.pet.model.UpdatePetModelInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IPetMapper {
    @Select("select * from core_pets where userId=#{userId}")
    List<PetModelDto> list(@Param("userId") long userId);
    @Select("select * from core_pets where id=#{id}")
    PetModelDto get(@Param("id") long id);
    @Delete("delete from core_pets where id=#{id}")
    long delete(@Param("id") long id);
    @Insert("INSERT INTO `core_pets` (`id`,`avatar`,`nickName`,`birthday`,`sex`, " +
            "`categoryId`, `userId`) VALUES(#{input.id},#{input.avatar},#{input.nickName}," +
            "#{input.birthday},#{input.sex},#{input.categoryId},#{userId})")
    long add(@Param("input") UpdatePetModelInput input,@Param("userId") long userId);
    @UpdateProvider(type = PetSqlProvider.class,method = "updateInfo")
    long update(@Param("input") UpdatePetModelInput input);
    @Select("<script>" +
            "select * from core_pets where id in " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "#{item.id}" +
            "</foreach>" +
            "</script>")
    List<PetModelDto> findMore(@Param("ids") List<EntityKeyModel<Long>> ids);

    //审核相关
    @Update("update core_pets set avatar='http://pbl2sn6wt.bkt.clouddn.com/default_avatar.png' where id=#{id}")
    int updatePetAvatar(@Param("id")Long id);

}
