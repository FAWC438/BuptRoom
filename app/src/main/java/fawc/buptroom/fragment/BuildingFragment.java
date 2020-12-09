package fawc.buptroom.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.PagerTabStrip;
import androidx.viewpager.widget.ViewPager;
import fawc.buptroom.R;
import fawc.buptroom.Serializable.SerializableMap;
import fawc.buptroom.services.ServerData;
import fawc.buptroom.services.TimeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class BuildingFragment extends Fragment {
    // private final EmptyRoom emptyroom = new EmptyRoom();
    // Set<Map.Entry<String, Object>> roomData;
    View view1, view2, view3, view4, view5, view6, view7;
    ViewPager viewpager;
    Map<String, ?> buildingMap;
    private TimeInfo timeinfo;
    private int curClass = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_building, container, false);
        viewpager = v.findViewById(R.id.viewpager);
        PagerTabStrip strip = v.findViewById(R.id.pager_title);
        strip.setDrawFullUnderline(false);
        strip.setTextSpacing(5);
        strip.setTabIndicatorColorResource(R.color.colorPrimary);

        timeinfo = new TimeInfo();

        //把各个layout转成view,加入ViewPager中
        view1 = inflater.inflate(R.layout.building_1, container, false);
        view1.setScrollContainer(true);
        SetPage(view1, "1");

        view2 = inflater.inflate(R.layout.building_2, container, false);
        view2.setScrollContainer(true);
        SetPage(view2, "2");

        view3 = inflater.inflate(R.layout.building_3, container, false);
        view3.setScrollContainer(true);
        SetPage(view3, "3");

        view4 = inflater.inflate(R.layout.building_4, container, false);
        view4.setScrollContainer(true);
        SetPage(view4, "4");

        view5 = inflater.inflate(R.layout.building_n, container, false);
        view5.setScrollContainer(true);
        SetPage(view5, "N");

        view6 = inflater.inflate(R.layout.building_s, container, false);
        view6.setScrollContainer(true);
        SetPage(view6, "S");

        view7 = inflater.inflate(R.layout.building_activity, container, false);
        view7.setScrollContainer(true);
        SetPage(view7, "办");

        ArrayList<View> views = new ArrayList<>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
        views.add(view7);

        //加标题
        ArrayList<String> titles = new ArrayList<>();
        titles.add("教一楼");
        titles.add("教二楼");
        titles.add("教三楼");
        titles.add("教四楼");
        titles.add("教学实验综合楼-北楼");
        titles.add("教学实验综合楼-南楼");
        titles.add("沙河校区多功能厅");

        MYViewPagerAdapter adapter = new MYViewPagerAdapter(views, titles);
        viewpager.setAdapter(adapter);
        return v;
    }

    public void Init() {
        try {
            assert getArguments() != null;
            SerializableMap serializableMap = (SerializableMap) getArguments().getSerializable("BuildingMap");
            buildingMap = serializableMap.getMap();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static class MYViewPagerAdapter extends PagerAdapter {
        private final ArrayList<View> views;
        private final ArrayList<String> titles;

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(@NonNull View container, int position, @NonNull Object object) {

            ((ViewPager) container).removeView(views.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull View container, int position) {
            ((ViewPager) container).addView(views.get(position));
            return views.get(position);
        }

        //设置view和标题
        MYViewPagerAdapter(ArrayList<View> views, ArrayList<String> titles) {
            this.views = views;
            this.titles = titles;
        }

        @Override
        //设置标题字体和颜色
        public CharSequence getPageTitle(int position) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(titles.get(position));
            ssb.setSpan(new RelativeSizeSpan(1.4f), 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(242, 242, 242));
            ssb.setSpan(fcs, 0, ssb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ssb;
        }
    }

    public void SetPage(View view, String buildingName) {

        TextView t;
        // ArrayList<String> tempclass = new ArrayList<>();
        // tempclass = emptyroom.get_show_content(buildingName, htmlBody);

        t = view.findViewById(R.id.jie1);
        t.setText(getShowContent(buildingName, 1));
        if (timeinfo.getCurClass_int() == 1)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie2);
        t.setText(getShowContent(buildingName, 2));
        if (timeinfo.getCurClass_int() == 2)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie3);
        t.setText(getShowContent(buildingName, 3));
        if (timeinfo.getCurClass_int() == 3)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie4);
        t.setText(getShowContent(buildingName, 4));
        if (timeinfo.getCurClass_int() == 4)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie5);
        t.setText(getShowContent(buildingName, 5));
        if (timeinfo.getCurClass_int() == 5)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));


        t = view.findViewById(R.id.jie6);
        t.setText(getShowContent(buildingName, 6));
        if (timeinfo.getCurClass_int() == 6)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie7);
        t.setText(getShowContent(buildingName, 7));
        if (timeinfo.getCurClass_int() == 7)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie8);
        t.setText(getShowContent(buildingName, 8));
        if (timeinfo.getCurClass_int() == 8)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie9);
        t.setText(getShowContent(buildingName, 9));
        if (timeinfo.getCurClass_int() == 9)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie10);
        t.setText(getShowContent(buildingName, 10));
        if (timeinfo.getCurClass_int() == 10)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie11);
        t.setText(getShowContent(buildingName, 11));
        if (timeinfo.getCurClass_int() == 11)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie12);
        t.setText(getShowContent(buildingName, 12));
        if (timeinfo.getCurClass_int() == 12)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie13);
        t.setText(getShowContent(buildingName, 13));
        if (timeinfo.getCurClass_int() == 13)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

        t = view.findViewById(R.id.jie14);
        t.setText(getShowContent(buildingName, 14));
        if (timeinfo.getCurClass_int() == 14)
            t.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));

//        t12 = view.findViewById(R.id.jie1);
//        t12.setText(tempclass.get(0));
//        if (curClass == 0)
//            t12.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));
//        t34 = view.findViewById(R.id.jie34);
//        t34.setText(tempclass.get(1));
//        if (curClass == 1)
//            t34.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));
//        t56 = view.findViewById(R.id.jie56);
//        t56.setText(tempclass.get(2));
//        if (curClass == 2)
//            t56.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));
//        t78 = view.findViewById(R.id.jie78);
//        t78.setText(tempclass.get(3));
//        if (curClass == 3)
//            t78.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));
//        t9 = view.findViewById(R.id.jie9);
//        t9.setText(tempclass.get(4));
//        if (curClass == 4)
//            t9.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));
//        t1011 = view.findViewById(R.id.jie1011);
//        t1011.setText(tempclass.get(5));
//        if (curClass == 5)
//            t1011.setBackgroundColor(getResources().getColor(R.color.TextNowCLassBackGroundColor));
    }

    private String getShowContent(String buildingName, int curClass) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("第").append(curClass).append("节课（").append(timeinfo.getCurClass_time_arr()[curClass]).append("）\n");
        stringBuilder.append("暂无教室可用");
        for (String s : buildingMap.keySet()) {
            if (buildingName.equals(s.substring(0, 1))) {
                int[][] emptyArr = ServerData.convertToArray((String) Objects.requireNonNull(buildingMap.get(s)), 7, 14);
                TimeInfo timeInfo = new TimeInfo();
                if (emptyArr[timeInfo.getDayCounter()][curClass - 1] == 1) {
                    if (stringBuilder.charAt(stringBuilder.length() - 1) == '用')
                        stringBuilder.delete(stringBuilder.length() - 6, stringBuilder.length());
                    stringBuilder.append(s.substring(0, s.indexOf('('))).append('\n');
                }
            }
        }
        return stringBuilder.toString();
    }
}
