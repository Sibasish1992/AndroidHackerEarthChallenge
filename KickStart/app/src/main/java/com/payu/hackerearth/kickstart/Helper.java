package com.payu.hackerearth.kickstart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sibasish Mohanty on 13/08/17.
 */

public class Helper {
    public static Date stringToDate(String date_str) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date;
        try {
            date = format.parse(date_str);
        } catch (ParseException e) {
            date = Calendar.getInstance().getTime();
        }

        return date;

    }

    public static String dateToString(Date start_time) {
        String str = "";
        DateFormat df = new SimpleDateFormat("dd");
        String a = df.format(start_time);
        df = new SimpleDateFormat("MMMM");
        String b = df.format(start_time);
        df = new SimpleDateFormat("yyyy");
        String y = df.format(start_time);
        df = new SimpleDateFormat("hh:mm a");
        String d = df.format(start_time);
        str = d + " "  + a + "th " + b+","+y;
        return str;
    }
}

