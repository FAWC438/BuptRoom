package fawc.buptroom.services;


import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.TimeZone;

@Getter
@Setter
public class TimeInfo {

    private String curClass_str = null;//现在是第几节课
    private String curClass_str_time = null;
    private String[] curClass_time_arr;
    private int curClass_int = 0;
    private int dayCounter = 0;//周几代号，配合MainActivity中的字符串数组显示每日问候
    private String timeString = null;//年月日星期几

    public TimeInfo() {
        curClass_time_arr = new String[]{"-", "8:00-8:30", "8:50-9:35", "9:50-10:35", "10:40-11:25", "11:30-12:15", "13:00-13:45", "13:50-14:35", "14:45-15:30", "15:40-16:25", "16:35-17:20", "17:25-18:10", "18:30-19:15", "19:20-20:05", "20:10-20:55"};
        timeSetting();
    }

    private void timeSetting() {

        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR));
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        if (mHour == 8 && mMinute < 50) {
            curClass_str = "现在是第1节课";
            curClass_int = 1;
        } else if (mHour == 8 || (mHour == 9 && mMinute < 50)) {
            curClass_str = "现在是第2节课";
            curClass_int = 2;
        } else if (mHour == 9 || (mHour == 10 && mMinute < 40)) {
            curClass_str = "现在是第3节课";
            curClass_int = 3;
        } else if (mHour == 10 || (mHour == 11 && mMinute < 30)) {
            curClass_str = "现在是第4节课";
            curClass_int = 4;
        } else if (mHour == 11 || (mHour == 12 && mMinute < 15)) {
            curClass_str = "现在是第5节课";
            curClass_int = 5;
        } else if (mHour == 13 && mMinute < 50) {
            curClass_str = "现在是第6节课";
            curClass_int = 6;
        } else if (mHour == 13 || (mHour == 14 && mMinute < 45)) {
            curClass_str = "现在是第7节课";
            curClass_int = 7;
        } else if (mHour == 14 || (mHour == 15 && mMinute < 40)) {
            curClass_str = "现在是第8节课";
            curClass_int = 8;
        } else if (mHour == 15 || (mHour == 16 && mMinute < 35)) {
            curClass_str = "现在是第9节课";
            curClass_int = 9;
        } else if (mHour == 16 || (mHour == 17 && mMinute < 25)) {
            curClass_str = "现在是第10节课";
            curClass_int = 10;
        } else if (mHour == 17 || (mHour == 18 && mMinute < 10)) {
            curClass_str = "现在是第11节课";
            curClass_int = 11;
        } else if ((mHour == 18 && mMinute >= 30) || (mHour == 19 && mMinute < 20)) {
            curClass_str = "现在是第12节课";
            curClass_int = 12;
        } else if (mHour == 19 || (mHour == 20 && mMinute < 10)) {
            curClass_str = "现在是第13节课";
            curClass_int = 13;
        } else if (mHour == 20) {
            curClass_str = "现在是第14节课";
            curClass_int = 14;
        } else {
            curClass_str = "现在是休息时间";
            curClass_int = 0;
        }
        curClass_str_time = curClass_time_arr[curClass_int];

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
