package com.zwdbj.server.utility.common;

public class YearAgeHelper {
    /**
     * @param year
     * @return
     */
    public static String ageShowFriendly(float year) {
        if (year<1.0f) {
            return ((int)(year * 12))+"个月";
        } else if (year==1.0f) {
            return "1岁";
        }
        int yearAge = (int)year;
        float month = year - yearAge;
        return yearAge+"岁"+((int)(month * 12))+"个月";
    }
}
