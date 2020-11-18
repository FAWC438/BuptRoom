package fawc.buptroom.services;


import java.util.Calendar;
import java.util.TimeZone;


public class TimeInfo {

    public String curTime = null;//现在是第几节课
    public int dayCounter = 0;//周几代号，配合MainActivity中的字符串数组显示每日问候
    public String timeString = null;//年月日星期几

    public TimeInfo() {
    }

    public void timeSetting() {
        /*
         * Created by fawc on 2016/9/28 0028 9:30
         * Parameter []
         * Return void
         * CLASS:MainActivity
         * FILE:MainActivity.java
         */

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR));
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        if (mHour >= 8 && mHour < 10) {
            curTime = "现在是12节课";
        } else if (mHour >= 10 && mHour < 12) {
            curTime = "现在是34节课";
        } else if ((mHour == 13 && mMinute >= 30) || (mHour == 14) || (mHour == 15 && mMinute < 30)) {
            curTime = "现在是56节课";
        } else if ((mHour == 15 && mMinute >= 30) || (mHour == 16) || (mHour == 17 && mMinute < 30)) {
            curTime = "现在是78节课";
        } else if ((mHour == 17 && mMinute >= 30) || (mHour == 18 && mMinute < 30)) {
            curTime = "现在是第9节课";
        } else if ((mHour == 18 && mMinute >= 30) || (mHour == 19) || (mHour == 20 && mMinute < 30)) {
            curTime = "现在是10,11节课";
        } else
            curTime = "现在是休息时间";

        switch (mWay) {
            case "1":
                mWay = "天";
                dayCounter = 6;
                break;
            case "2":
                mWay = "一";
                dayCounter = 0;
                break;
            case "3":
                mWay = "二";
                dayCounter = 1;
                break;
            case "4":
                mWay = "三";
                dayCounter = 2;
                break;
            case "5":
                mWay = "四";
                dayCounter = 3;
                break;
            case "6":
                mWay = "五";
                dayCounter = 4;
                break;
            case "7":
                mWay = "六";
                dayCounter = 5;
                break;
        }
        timeString = mYear + "年" + mMonth + "月" + mDay + "日" + "星期" + mWay;
    }
}
