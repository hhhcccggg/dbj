package com.zwdbj.server.mobileapi.service.user.mapper;

import com.zwdbj.server.mobileapi.service.user.model.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class UserSqlProvider {
    private final String TBL_NAME = "core_users";
    private static final double PI = 3.14159265;
    private static final double EARTH_RADIUS = 6378137;
    private static final double RAD = Math.PI / 180.0;

    public String updateField(Map paramas) {
        Long id = (Long)paramas.get("id");
        String fields = (String)paramas.get("fields");
        SQL sql = new SQL()
                .UPDATE("core_users")
                .SET(fields)
                .WHERE("id=#{id}");
        return sql.toString();
    }


    public String updateInfo(Map params) {
        UpdateUserInfoInput input = (UpdateUserInfoInput)params.get("input");
        long userId = (long)params.get("userId");
        SQL sql = new SQL()
                .UPDATE(TBL_NAME);
        if (input.getAvatarKey()!=null&&input.getAvatarKey().length()>0) {
            sql.SET("avatarUrl=#{input.avatarKey}");
        }
        if (input.getNickName()!=null){
            sql.SET("nickName=#{input.nickName}");
        }
        sql.SET("sex=#{input.sex}");
        if(input.getBirthday()!=null){
            sql.SET("birthday=#{input.birthday}") ;
        }
        if (input.getCity()!=null){
            sql.SET("address=#{input.city}");
        }
        if (input.getSignature()!=null){
            sql.SET("signature=#{input.signature}");
        }
        sql.SET("longitude=#{input.longitude}")
        .SET("latitude=#{input.latitude}")
        .SET("occupationId=#{input.occupationId}")
        .SET("loveStatusId=#{input.loveStatusId}");
        if (input.getUserName() != null) {
            sql.SET("username=#{input.userName}");
        }
        sql.WHERE("id=#{userId}");
        return sql.toString();
    }

    public String selectUserAvatarUrl(Map map){
        List<Long> userIds = (List<Long>) map.get("userIds");
        SQL sql = new SQL().SELECT("id,avatarUrl").FROM(TBL_NAME);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < userIds.size(); i++) {
            stringBuffer.append("id="+userIds.get(i));
            if(i+1 != userIds.size())stringBuffer.append(" or ");
        }
        sql.WHERE(stringBuffer.toString());
        System.out.println(sql.toString());
        return sql.toString();
    }


    public String nearby(Map params) {
        float longitude = (float) params.get("longitude");
        float latitude = (float) params.get("latitude");
        int sex = (int)params.get("sex");
        int distance1 = (int) params.get("distance1");

        //double[] results = UserSqlProvider.getAround(latitude,longitude,distance1/1.0F);

        SQL sql = new SQL()
                .SELECT("u.id as userId,u.nickName as nickName,u.sex as sex,u.avatarUrl as avatarUrl," +
                        "(st_distance(POINT (u.longitude, u.latitude),POINT(#{longitude},#{latitude}))*95000) AS distance " )
                .FROM("core_users u");
        if (sex!=-1){
            sql.WHERE("u.sex=#{sex}");
        }
        if (distance1>0)
            sql.HAVING("distance<#{distance1}");
                /*.WHERE(String.format("longitude BETWEEN %f AND %f",results[1],results[3]))
                .AND()
                .WHERE(String.format("latitude  BETWEEN %f AND %f",results[0],results[2]))
                .AND()*/

        sql.ORDER_BY("distance");
        return sql.toString();
    }

    public static double[] getAround(double lat,double lon,float radius) {

        Double latitude = lat;
        Double longitude = lon;

        Double degree = (24901 * 1609) / 360.0;
        double radiusMile = radius;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * radiusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree * Math.cos(latitude * (PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * radiusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        return new double[]{minLat, minLng, maxLat, maxLng};
    }

}
