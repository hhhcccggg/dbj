package com.zwdbj.server.mobileapi.service.pet.mapper;

import com.zwdbj.server.mobileapi.model.EntityKeyModel;
import com.zwdbj.server.mobileapi.service.pet.model.PetModelDto;
import com.zwdbj.server.mobileapi.service.pet.model.UpdatePetModelInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IPetMapper {
    @Select("select pet.*,cate.name as categoryName from core_pets as pet " +
            "left join core_categories as cate on pet.categoryId=cate.id where pet.userId=#{userId} and pet.isDeleted=0")
    List<PetModelDto> list(@Param("userId") long userId);
    @Select("select count(id) from core_pets where userId=#{userId}")
    int findAllMyPets(@Param("userId")long userId);
    @Select("select pet.*,cate.name as categoryName from core_pets as pet left join core_categories as cate on pet.categoryId=cate.id where pet.id=#{id}")
    PetModelDto get(@Param("id") long id);
    @Update("update core_pets set isDeleted=1,deleteTime=now() where id=#{id} and userId=#{userId}")
    long delete(@Param("id") long id,@Param("userId")long userId);
    @Insert("INSERT INTO `core_pets` (`id`,`avatar`,`nickName`,`birthday`,`sex`, " +
            "`categoryId`, `userId`) VALUES(#{input.id},#{input.avatar},#{input.nickName}," +
            "#{input.birthday},#{input.sex},#{input.categoryId},#{userId})")
    long add(@Param("input") UpdatePetModelInput input,@Param("userId") long userId);
    @UpdateProvider(type = PetSqlProvider.class,method = "updateInfo")
    long update(@Param("input") UpdatePetModelInput input);
    @Select("<script>" +
            "select pet.*,cate.name as categoryName from core_pets as pet left join core_categories as cate on pet.categoryId=cate.id where pet.id in " +
            "<foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>" +
            "#{item.id}" +
            "</foreach>" +
            "</script>")
    List<PetModelDto> findMore(@Param("ids") List<EntityKeyModel<Long>> ids);

    @Select("select count(id) from `core_pets` where  userId=#{userId}")
    long firstAddPet(@Param("userId") long userId);

    @Select("select userId from core_pets where id=#{id}")
    long findUserIdByPetId(@Param("id")long petId);
    @Select("select nickName from core_pets where id=#{id}")
    String getPetNameById(@Param("id")long petId);



}
