package com.pro.huyfood.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    private static final String DEFAULT_FORMAT_DATE = "dd-MM-yyyy,hh:mm a";
    private static final String DEFAULT_FORMAT_DATE_2 = "dd/MM/yyyy";

    public static String convertTimeStampToDate(long timeStamp) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE, Locale.ENGLISH);
            Date date = (new Date(timeStamp));
            result = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertTimeStampToDate_2(long timeStamp) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE_2, Locale.ENGLISH);
            Date date = (new Date(timeStamp));
            result = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertDate2ToTimeStamp(String strDate) {
        String result = "";
        if (strDate != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT_DATE_2, Locale.ENGLISH);
                Date date = format.parse(strDate);
                if (date != null) {
                    Long timestamp = date.getTime() / 1000;
                    result = String.valueOf(timestamp);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
