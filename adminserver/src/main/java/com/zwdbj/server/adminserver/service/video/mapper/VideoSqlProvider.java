package com.zwdbj.server.adminserver.service.video.mapper;



import com.zwdbj.server.adminserver.service.video.model.AdVideoSearchComplainInput;
import com.zwdbj.server.adminserver.service.video.model.AdVideoVerityInfoInput;
import com.zwdbj.server.adminserver.service.video.model.SearchVideoAdInput;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class VideoSqlProvider {

    private static final double PI = 3.14159265;
    private static final double EARTH_RADIUS = 6378137;
    private static final double RAD = Math.PI / 180.0;


    public String findIncreasedVideoingAd(Map params){
        int input = (int)params.get("input");
        SQL sql = new SQL()
                .SELECT("count(id)")
                .FROM("core_videos")
                .WHERE("status=1");
        if (input==0){
            sql.WHERE("TO_DAYS(createTime) = TO_DAYS(NOW())");
        }else if (input==1){
            sql.WHERE("YEARWEEK(DATE_FORMAT(createTime,'%Y-%m-%d')) = YEARWEEK(NOW())");
        }else if (input==2){
            sql.WHERE("DATE_FORMAT(createTime,'%Y-%m')=DATE_FORMAT(NOW(),'%Y-%m')");
        }else if (input==3){
            sql.WHERE("QUARTER(createTime)=QUARTER(NOW())");
        }else if (input==4){
            sql.WHERE("YEAR(createTime)=YEAR(NOW())");
        }else if (input==5){
            sql.WHERE("TO_DAYS(createTime) = TO_DAYS(NOW())-1");
        }else if (input==6){
            sql.WHERE("YEARWEEK(DATE_FORMAT(createTime,'%Y-%m-%d')) = YEARWEEK(NOW())-1");
        }else if (input==7){
            sql.WHERE("PERIOD_DIFF(date_format(now(),'%Y%m'),date_format(createTime,'%Y%m')) =1");
        }else if (input==8){
            sql.WHERE("year(createTime)=year(date_sub(now(),interval 1 year))");
        }
        return sql.toString();
    }

    public String findIncreasedVideoAd(Map params){
        int input = (int)params.get("input");
        SQL sql = new SQL()
                .SELECT("count(id)")
                .FROM("core_videos")
                .WHERE("status=0");
        if (input==0){
            sql.WHERE("TO_DAYS(createTime) = TO_DAYS(NOW())");
        }else if (input==1){
            sql.WHERE("YEARWEEK(DATE_FORMAT(createTime,'%Y-%m-%d')) = YEARWEEK(NOW())");
        }else if (input==2){
            sql.WHERE("DATE_FORMAT(createTime,'%Y-%m')=DATE_FORMAT(NOW(),'%Y-%m')");
        }else if (input==3){
            sql.WHERE("QUARTER(createTime)=QUARTER(NOW())");
        }else if (input==4){
            sql.WHERE("YEAR(createTime)=YEAR(NOW())");
        }else if (input==5){
            sql.WHERE("TO_DAYS(createTime) = TO_DAYS(NOW())-1");
        }else if (input==6){
            sql.WHERE("YEARWEEK(DATE_FORMAT(createTime,'%Y-%m-%d')) = YEARWEEK(NOW())-1");
        }else if (input==7){
            sql.WHERE("PERIOD_DIFF(date_format(now(),'%Y%m'),date_format(createTime,'%Y%m')) =1");
        }else if (input==8){
            sql.WHERE("year(createTime)=year(date_sub(now(),interval 1 year))");
        }
        return sql.toString();
    }

    public String verityListAd(Map params){
        AdVideoVerityInfoInput model = (AdVideoVerityInfoInput)params.get("model");
        SQL sql = new SQL()
                .SELECT("v.*,u.userName,u.nickName")
                .FROM("core_videos v")
                .INNER_JOIN("core_users u on u.id=v.userId");
        if (model.getStatus()!=-1){
            sql.WHERE("status=#{model.status}");
        }
        if (model.getKeywords()!=null && model.getKeywords().length()>0){
            sql.WHERE(String.format("title like '%s'",("%"+model.getKeywords()+"%")))
                    .OR()
                    .WHERE(String.format("id like '%s'",("%"+model.getKeywords()+"%")));
        }
        sql.ORDER_BY("createTime desc");
        return sql.toString();
    }

    public String searchComplainVideosSql(Map params){
        AdVideoSearchComplainInput model = (AdVideoSearchComplainInput)params.get("model");
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("core_videos")
                .WHERE("complainCount!=0 and status=0");
        if (model.getKeyword()!=null && model.getKeyword().length()>0){
            sql.WHERE(String.format("title like '%s'",("%"+model.getKeyword()+"%")));
        }
        sql.ORDER_BY("complainCount desc");
        return sql.toString();
    }

    public String searchSql(Map params) {
        SearchVideoAdInput model = (SearchVideoAdInput)params.get("model");
        SQL sql = new SQL()
                .SELECT("v.*")
                .FROM("core_videos v")
                .LEFT_OUTER_JOIN("core_userRoles r on r.userId=v.userId")
                .LEFT_OUTER_JOIN("core_users u on u.id=v.userId");
        if(model.getStatus()!=-1) {
            sql.WHERE("v.status=#{model.status}");
        }
        if (model.getIsLinkProduct()!=-1) {
            if (model.getIsLinkProduct()==0){
                sql.WHERE("v.linkProductCount=0");
            }else {
                sql.WHERE("v.linkProductCount!=0");
            }

        }
        if (model.getIsHaveTag()!=-1) {
            if (model.getIsHaveTag()==0) {
                sql.WHERE("(v.tags is NULL or tags='')");
            } else {
                sql.WHERE("v.tags is not NULL")
                        .WHERE("v.tags<>''");
            }
        }
        if (model.getKeywords()!=null && model.getKeywords().length()>0) {
            sql.WHERE(String.format("u.id like '%s'", ("%" + model.getKeywords() + "%")))
                    .OR()
                    .WHERE(String.format("u.nickName like '%s'", ("%" + model.getKeywords() + "%")))
                    .OR()
                    .WHERE(String.format("v.title like '%s'", ("%" + model.getKeywords() + "%")));
        }
        if (model.getRoleName()!=null && model.getRoleName().length()>0) {
            sql.WHERE(String.format("r.roleName='%s'",model.getRoleName()));
        }
        sql.ORDER_BY("v.id desc");
        return sql.toString();
    }

    public String updateVideoField(Map params) {
        Long id = (Long)params.get("id");
        String fields = (String)params.get("fields");
        SQL sql = new SQL()
                .UPDATE("core_videos")
                .SET(fields)
                .WHERE("id=#{id}");
        return sql.toString();
    }

}
