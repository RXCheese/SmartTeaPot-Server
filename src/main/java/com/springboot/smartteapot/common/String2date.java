package com.springboot.smartteapot.common;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class String2date {
    public String String2date(String string) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timestamp = Long.parseLong(string) * 1000;
        String date = sdf.format(new Date(timestamp));
        return date;
    }
}
