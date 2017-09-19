package com.yxb;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by yxb on 2017/9/19.
 */
public class Utils {
    public static String getNowDate(){

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tsStr = sdf.format(ts);

        return tsStr;
    }
}
