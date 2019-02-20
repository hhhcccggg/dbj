package com.zwdbj.server.adminserver.service.adBanner.mapper;

import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerDto;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerInput;
import com.zwdbj.server.adminserver.service.adBanner.model.AdBannerSerchInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class AdBannerSqlProvider {

    public String search(Map paras) {
        AdBannerSerchInput input = (AdBannerSerchInput) paras.get("input");
        SQL sql = new SQL().SELECT("id,title,imageUrl,refUrl,type,platform,state,createTime").
                FROM("core_adBanners").WHERE("isDeleted=0");
        if (input.getState() != null && !"".equals(input.getState())) {
            sql.WHERE("state=#{input.state}");
        }
        if (input.getKeyWords()!=null && input.getKeyWords().length()>0){
            sql.WHERE(String.format("title like '%s'",("%"+input.getKeyWords()+"%")));
        }
        sql.ORDER_BY("createTime desc");
        return sql.toString();


    }

    public String modify(Map paras) {
        AdBannerDto input = (AdBannerDto) paras.get("input");
        long id = (long) paras.get("id");
        SQL sql = new SQL().UPDATE("core_adBanners").WHERE("id=#{id}");
        if (input.getPlatform() != null && !"".equals(input.getPlatform())) {
            sql.SET("platform=#{input.platform}");
        }
        if (input.getState() != null && !"".equals(input.getState())) {
            sql.SET("state=#{input.state}");

        }
        if (input.getType() != null && !"".equals(input.getType())) {
            sql.SET("type=#{input.type}");
        }
        if (input.getImageUrl() != null && !"".equals(input.getImageUrl())) {
            sql.SET("imageUrl=#{input.imageUrl}");
        }
        if (input.getTitle() != null && !"".equals(input.getTitle())) {
            sql.SET("title=#{input.title}");
        }
        if (input.getRefUrl() != null && !"".equals(input.getRefUrl())) {
            sql.SET("refUrl=#{input.refUrl}");
        }


        return sql.toString();
    }
}
