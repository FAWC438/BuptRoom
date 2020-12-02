package fawc.buptroom.services;

import android.content.SharedPreferences;
import android.os.Build;
import androidx.annotation.RequiresApi;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class EmptyRoom {


//    private String buildingName;
//    private int curClass;
//    private Map<String, ?> data;
//
//
//    public EmptyRoom(String buildingName, int curClass, Map<String, ?> data) {
//        this.buildingName = buildingName;
//        this.curClass = curClass;
//        this.data = data;
//    }

//    public static String getShowContent(String buildingName, int curClass, Map<String, ?> data) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String s : data.keySet()) {
//            if (buildingName.equals(s.substring(0, 1))) {
//                int[][] emptyArr = ServerData.convertToArray((String) Objects.requireNonNull(data.get(s)), 7, 14);
//                TimeInfo timeInfo = new TimeInfo();
//                if (emptyArr[timeInfo.getDayCounter()][curClass] == 1) {
//                    stringBuilder.append(s).append('\n');
//                }
//            }
//        }
//        return stringBuilder.toString();
//    }

//    public ArrayList<String> get_show_content(String keyword, String htmlbody) {
//        /*
//         * Created by fawc on 2016/9/29 0029 17:54
//         * Parameter [keyword, htmlbody] 楼名，网页拉取到的html_body内容
//         * Return java.lang.String
//         * CLASS:EmptyRoom
//         * FILE:EmptyRoom.java
//         */
//
//
//        String htmlbodytemp = htmlbody;
//        ArrayList<String> result = new ArrayList<>();
//        htmlbody = "";
//        String[] contentstemp = htmlbodytemp.split(",");
//        StringBuilder htmlbodyBuilder = new StringBuilder(htmlbody);
//        for (String temp : contentstemp) {
//            htmlbodyBuilder.append(temp);
//        }
//        htmlbody = htmlbodyBuilder.toString();
//        RawRoomInfo = htmlbody.split("[ :]");
//        int count = 0;
//        int tsgflag = 0;
//        int cishu = 0;
//        j12.clear();
//        j34.clear();
//        j56.clear();
//        j78.clear();
//        j9.clear();
//        j1011.clear();
//        if (keyword.contains("图书馆")) tsgflag = 1;
//        for (String temp : RawRoomInfo) {
//            if (temp.contains("2节")) cishu = 1;
//            else if (temp.contains("4节")) cishu = 2;
//            else if (temp.contains("6节")) cishu = 3;
//            else if (temp.contains("8节")) cishu = 4;
//            else if (temp.contains("9节")) cishu = 5;
//            else if (temp.contains("11节")) cishu = 6;
//            if (temp.contains(keyword)) {
//                if (tsgflag == 1 && temp.length() < 5) {
//                    switch (cishu) {
//                        case 1:
//                            j12.add("图书馆一层");
//                            break;
//                        case 2:
//                            j34.add("图书馆一层");
//                            break;
//                        case 3:
//                            j56.add("图书馆一层");
//                            break;
//                        case 4:
//                            j78.add("图书馆一层");
//                            break;
//                        case 5:
//                            j9.add("图书馆一层");
//                            break;
//                        case 6:
//                            j1011.add("图书馆一层");
//                            break;
//                        default:
//                            break;
//                    }
//                } else if (tsgflag == 0) SaveBuidlingInfo(count, cishu);
//            }
//            count++;
//        }
//        result.add("第1|2节\n" + setTLEContent(j12));
//        result.add("第3|4节\n" + setTLEContent(j34));
//        result.add("第5|6节\n" + setTLEContent(j56));
//        result.add("第7|8节\n" + setTLEContent(j78));
//        result.add("第9节\n" + setTLEContent(j9));
//        result.add("第10|11节\n" + setTLEContent(j1011));
//        return result;
//    }
//
//
//    private void SaveBuidlingInfo(int k, int c) {
//        /*
//         * Created by fawc on 2016/9/28 0028 9:28
//         * Parameter [k, c]当前字符串数组下标，节次
//         * Return void
//         * CLASS:MainActivity
//         * FILE:MainActivity.java
//         */
//
//        k++;
//        while (k < RawRoomInfo.length) {
//            if (RawRoomInfo[k].contains("楼") || RawRoomInfo[k].contains("节") || RawRoomInfo[k].contains("图"))
//                break;
//            switch (c) {
//                case 1:
//                    j12.add(RawRoomInfo[k]);
//                    break;
//                case 2:
//                    j34.add(RawRoomInfo[k]);
//                    break;
//                case 3:
//                    j56.add(RawRoomInfo[k]);
//                    break;
//                case 4:
//                    j78.add(RawRoomInfo[k]);
//                    break;
//                case 5:
//                    j9.add(RawRoomInfo[k]);
//                    break;
//                case 6:
//                    j1011.add(RawRoomInfo[k]);
//                    break;
//                default:
//                    break;
//            }
//            k++;
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private String setTLEContent(ArrayList<String> target) {
//
//        /*
//         * Created by fawc on 2016/9/28 0028 9:30
//         * Parameter [target]
//         * Return java.lang.String
//         * CLASS:MainActivity
//         * FILE:MainActivity.java
//         */
//
//
//        if (target.isEmpty()) return "无空闲教室";
//
//        int len = target.size();
//        StringBuilder f1 = new StringBuilder();
//        StringBuilder f2 = new StringBuilder();
//        StringBuilder f3 = new StringBuilder();
//        StringBuilder f4 = new StringBuilder();
//        StringBuilder f5 = new StringBuilder();
//        String result = "";
//
//        for (int i = 0; i < len; i++) {
//            if (target.get(i).contains("-1")) f1.append(target.get(i)).append(" ");
//            else if (target.get(i).contains("-2")) f2.append(target.get(i)).append(" ");
//            else if (target.get(i).contains("-3")) f3.append(target.get(i)).append(" ");
//            else if (target.get(i).contains("-4")) f4.append(target.get(i)).append(" ");
//            else if (target.get(i).contains("-5")) f5.append(target.get(i)).append(" ");
//            else if (target.get(i).contains("图书")) result = "图书馆一楼";
//        }
//        if (!Objects.equals(f1.toString(), "")) result = result + "一楼：" + f1 + "\n";
//        if (!Objects.equals(f2.toString(), "")) result = result + "二楼：" + f2 + "\n";
//        if (!Objects.equals(f3.toString(), "")) result = result + "三楼：" + f3 + "\n";
//        if (!Objects.equals(f4.toString(), "")) result = result + "四楼：" + f4 + "\n";
//        if (!Objects.equals(f5.toString(), "")) result = result + "五楼：" + f5 + "\n";
//        return result;
//
//    }
}
