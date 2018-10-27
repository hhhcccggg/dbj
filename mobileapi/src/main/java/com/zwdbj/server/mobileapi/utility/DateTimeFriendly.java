package com.zwdbj.server.mobileapi.utility;

import java.util.Date;

public class DateTimeFriendly {
    public static String friendlyShow(Date date) {
        if (date == null) return "未知";
        Date nowDate = new Date();
        long timeStamp = (nowDate.getTime() - date.getTime())/1000;
        String showValue = "未知";
        if (timeStamp<=59) {
            if (timeStamp < 10) {
                showValue = "刚刚";
            } else {
                showValue = String.format("%d秒前", timeStamp);
            }
        } else if (timeStamp>=60 && timeStamp<3600) {
            long minutes = timeStamp / 60;
            showValue = String.format("%d分前",minutes);
        } else if (timeStamp>=3600 && timeStamp < 24 *3600) {
            long hours = timeStamp / 3600;
            showValue = String.format("%d小时前",hours);
        } else if (timeStamp >= 24 *3600 && timeStamp < 30 * 24 * 3600) {
            long days = timeStamp / (24 * 3600);
            showValue = String.format("%d天前",days);
        } else if (timeStamp >= 30 *24 * 3600 && timeStamp < 30 * 24 *3600 * 12) {
            long months = timeStamp / (30 *24 * 3600);
            showValue = String.format("%d个月前",months);
        } else if (timeStamp >= 30 * 24 *3600 * 12) {
            long years = timeStamp / (30 * 24 *3600 * 12);
            showValue = String.format("%d年前",years);
        }
        return showValue;
    }


}
