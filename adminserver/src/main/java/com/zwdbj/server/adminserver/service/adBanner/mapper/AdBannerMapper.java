package com.zwdbj.server.adminserver.service.adBanner.mapper;

import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerDto;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerInfo;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerInput;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdBannerMapper {

    @Select("select id,title,imageUrl,refUrl,type,platform,state,createTime from core_adBanners where isDeleted=0 order by createTime ")
    List<AdBannerInfo> searchAllAdBanners();

    @SelectProvider(type = AdBannerSqlProvider.class, method = "search")
    List<AdBannerInfo> searchAdBanners(@Param("input") AdBannerInput input);

    @UpdateProvider(type = AdBannerSqlProvider.class, method = "modify")
    Long modifyAdBanner(@Param("input") AdBannerDto input, @Param("id") long id);

    @Insert("insert into core_adBanners (id,title,imageUrl,refUrl,type,platform,state) " +
            "values(#{id},#{dto.title},#{dto.imageUrl},#{dto.refUrl},#{dto.type}," +
            "#{dto.platform},#{dto.state})")
    Long createAdBanner(@Param("id") long id, @Param("dto") AdBannerDto dto);

    @Update("update core_adBanners set isDeleted=1 ,deleteTime=now() where id=#{id}")
    Long deleteAdBanner(@Param("id") long id);
}
