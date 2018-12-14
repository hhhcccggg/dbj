package com.zwdbj.server.adminserver.service.user.mapper;

import com.zwdbj.server.adminserver.service.homepage.model.AdFindIncreasedInput;
import com.zwdbj.server.adminserver.service.user.model.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class UserSqlProvider {
    private final String TBL_NAME = "core_users";


    public String findIncreasedUserAd(Map params){
        AdFindIncreasedInput input = (AdFindIncreasedInput)params.get("input");
        Boolean flag = (Boolean)params.get("flag");
        SQL sql = new SQL()
                .SELECT("count(id) as userNum ")
                .FROM("core_users")
                .WHERE("isSuper=0");
        if (!flag){
            sql.WHERE("isManualData=false");
        }
        if (input.getQuantumTime()==0){
            sql.WHERE("TO_DAYS(createTime) = TO_DAYS(NOW())");
        }else if (input.getQuantumTime()==1){
            sql.WHERE("YEARWEEK(DATE_FORMAT(createTime,'%Y-%m-%d')) = YEARWEEK(NOW())");
        }else if (input.getQuantumTime()==2){
            sql.WHERE("DATE_FORMAT(createTime,'%Y-%m')=DATE_FORMAT(NOW(),'%Y-%m')");
        }else if (input.getQuantumTime()==3){
            sql.WHERE("QUARTER(createTime)=QUARTER(NOW())");
        }else if (input.getQuantumTime()==4){
            sql.WHERE("YEAR(createTime)=YEAR(NOW())");
        }else if (input.getQuantumTime()==5){
            sql.WHERE("TO_DAYS(createTime) = TO_DAYS(NOW())-1");
        }else if (input.getQuantumTime()==6){
            sql.WHERE("YEARWEEK(DATE_FORMAT(createTime,'%Y-%m-%d')) = YEARWEEK(NOW())-1");
        }else if (input.getQuantumTime()==7){
            sql.WHERE("PERIOD_DIFF(date_format(now(),'%Y%m'),date_format(createTime,'%Y%m')) =1");
        }else if (input.getQuantumTime()==8){
            sql.WHERE("year(createTime)=year(date_sub(now(),interval 1 year))");
        }
        return sql.toString();
    }

    public String manageUserListAd(Map params){
        AdManageUserInput input = (AdManageUserInput)params.get("input");
        SQL sql = new SQL()
                .SELECT("u.nickName,u.phone,u.isLocked,r.roleName,u.createTime")
                .FROM("core_users u")
                .INNER_JOIN("core_userRoles r ON r.userId=u.id")
                .WHERE("r.roleName NOT IN('admin','normal')");
        if (input.getIsLocked() != -1){
            sql.WHERE("u.isLocked=#{input.isLocked}");
        }
        if (input.getKeywords() != null && input.getKeywords().length()>0){
            sql.WHERE(String.format("u.userName like '%s'",("%"+input.getKeywords()+"%")))
                    .OR().WHERE(String.format("u.phone like '%s'",("%"+input.getKeywords()+"%")));
        }
        sql.ORDER_BY("u.createTime desc");
        return sql.toString();
    }

    public  String searchComplainUserListAd(Map params){
        AdUserComplainInput input = (AdUserComplainInput)params.get("input");
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("core_users");
        try {
            if (input.getIsReviewed() != -1){
                sql.WHERE("isReviewed=#{input.isReviewed}");
            }
            if (input.getLoginType() != -1){
                sql.WHERE("loginType=#{input.loginType}");
            }
            if (input.getUserId() != null && input.getUserId().length()>0){
                sql.WHERE(String.format("id like '%s'",("%"+input.getUserId()+"%")));
            }
        }catch (RuntimeException e){
            throw  new RuntimeException("请输入正确的需要查询的用户ID");
        }
        return sql.toString();

    }

    public String updateField(Map paramas) {
        Long id = (Long)paramas.get("id");
        String fields = (String)paramas.get("fields");
        SQL sql = new SQL()
                .UPDATE("core_users")
                .SET(fields)
                .WHERE("id=#{id}");
        return sql.toString();
    }

    public String marketListAd(Map params){
        AdMarketUserInput input = (AdMarketUserInput)params.get("input");
        SQL sql = new SQL()
                .SELECT("u.*")
                .FROM("core_users u")
                .LEFT_OUTER_JOIN("core_userRoles r on r.userId=u.id");
        sql.WHERE("r.roleName='market'");
        try {
            if (input.getStatus() != -1){
                sql.WHERE("u.isLocked=#{input.status}");
            }
            if (input.getKeywords() != null && input.getKeywords().length()>0){
                sql.WHERE(String.format("u.id like '%s'",("%"+input.getKeywords()+"%")));
                sql.OR().WHERE(String.format("u.phone like '%s'",("%"+input.getKeywords()+"%")));
            }
        }catch (RuntimeException e){
            throw  new RuntimeException("请输入要查询的用户ID或者手机号");

        }
        return sql.toString();
    }

    public String queryUser(Map params) {
        UserSearchForAdInput model = (UserSearchForAdInput)params.get("model");
        Boolean flag = (Boolean) params.get("flag");
        SQL sql = new SQL()
                .SELECT("u.*,(SELECT COUNT(id) FROM core_pets AS pet WHERE pet.userId = u.id) AS petCount,(SELECT COUNT(id) FROM core_videos AS vd WHERE vd.userId = u.id) AS videoCount")
                .FROM("core_users u")
                .LEFT_OUTER_JOIN("core_userRoles r on r.userId=u.id");
        if (!flag){
            sql.WHERE("u.isManualData=false");
        }
        if (model.getKeyWords() !=null && model.getKeyWords().length()>0 ) {
            sql.WHERE(String.format("u.id like '%s'", ("%" + model.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("u.phone like '%s'", ("%" + model.getKeyWords() + "%")))
                    .OR()
                    .WHERE(String.format("u.nickName like '%s'", ("%" + model.getKeyWords() + "%")));
        }
        if (model.getLoginType()!=-1) {
            sql.WHERE("u.loginType=#{model.loginType}");
        }
        if (model.getIsLocked()!=-1) {
            sql.WHERE(String.format("u.isLocked=%d",model.getIsLocked()));
        }
        if (model.getIsReviewed()!=-1) {
            sql.WHERE(String.format("u.isReviewed=%d",model.getIsReviewed()));
        }
        if (model.getRoleName() !=null && model.getRoleName().length()>0){
            sql.WHERE(String.format("r.roleName='%s'",model.getRoleName()));
        }
        if (model.getStartTime() !=null && model.getEndTime() !=null){
            sql.WHERE(String.format("u.createTime between '%s' and '%s'",model.getStartTime(),model.getEndTime()));
        }
        sql.ORDER_BY("u.id desc");
        return sql.toString();
    }
}
