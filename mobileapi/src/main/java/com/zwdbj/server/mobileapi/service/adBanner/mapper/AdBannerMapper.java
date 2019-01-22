package com.zwdbj.server.mobileapi.service.adBanner.mapper;

import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerDto;
import com.zwdbj.server.mobileapi.service.adBanner.moder.AdBannerInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdBannerMapper {
    @Select("select title,imageUrl,refUrl from core_adBanners where type=#{adBannerInput.type} and " +
            "platform=#{adBannerInput.platform} and state='ONLINE'")
    List<AdBannerDto> searchAdBanner(@Param("adBannerInput") AdBannerInput adBannerInput);

}
