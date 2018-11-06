package com.zwdbj.server.mobileapi.service.video.mapper;


import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class VideoSqlProvider {

    private static final double PI = 3.14159265;
    private static final double EARTH_RADIUS = 6378137;
    private static final double RAD = Math.PI / 180.0;

    public String hotVideo(Map params) {
        String conditionValue = (String)params.get("conditionValue");
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("core_videos");
        if (conditionValue!=null&&!conditionValue.equals("")) {
            sql = sql.WHERE(conditionValue);
        }
        sql = sql.WHERE("status=0");
        sql = sql.ORDER_BY("createTime desc");
        return sql.toString();
    }

    public String videoByIds(Map params) {
        String ids = (String)params.get("ids");
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("core_videos")
                .WHERE("id in ("+ids+")")
                .AND()
                .WHERE("status=0");
        return sql.toString();
    }

    public String nearby(Map params) {
        Double longitude = (Double) params.get("longitude");
        Double latitude = (Double) params.get("latitude");
        float distance = (float) params.get("distance");

        double[] results = VideoSqlProvider.getAround(latitude,longitude,distance);

        SQL sql = new SQL()
                .SELECT("*")
                .FROM("core_videos")
                .WHERE(String.format("longitude BETWEEN %f AND %f",results[1],results[3]))
                .AND()
                .WHERE(String.format("latitude  BETWEEN %f AND %f",results[0],results[2]))
                .AND()
                .WHERE("status=0")
                .ORDER_BY("createTime desc");
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
