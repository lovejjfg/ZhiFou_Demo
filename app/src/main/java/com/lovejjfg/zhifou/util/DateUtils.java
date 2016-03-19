package com.lovejjfg.zhifou.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Aspsine on 2015/2/28.
 */
public class DateUtils {

    public static String getDate(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return String.valueOf(simpleDateFormat.format(date));
    }

    public static boolean isToday(String date) {
        String today = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        return date.equals(today);
    }

    public static String getMainPageDate(String date) {
        String mData = "";
        try {
            Date tmpDate = new SimpleDateFormat("yyyyMMdd").parse(date);
            mData = DateFormat.getDateInstance(DateFormat.FULL).format(tmpDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String thisYear = Calendar.getInstance().get(Calendar.YEAR) + "年";
        if (mData.startsWith(thisYear)) {
            return mData.replace(thisYear, "");
        }
        return mData;
    }


    public static boolean isMoreThanToday(String date) {
        Calendar mCalendar = Calendar.getInstance();
        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);
        Log.i("当前的日期：", year + "年:" + month + "月" + day + "日");
        int pickYear = Integer.valueOf(date.substring(0, 4));
        int pickMonth = Integer.valueOf(date.substring(4).substring(0, 2));
        int pickDay = Integer.valueOf(date.substring(4).substring(2));
        Log.i("选中的日期：", pickYear + "年:" + pickMonth + "月" + pickDay + "日");
        return pickYear > year || pickYear == year && pickMonth > month || pickYear == year && pickMonth == month && pickDay > day;
    }
}
