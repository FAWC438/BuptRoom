package fawc.buptroom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import fawc.buptroom.R;
import fawc.buptroom.Serializable.SerializableMap;
import fawc.buptroom.services.ServerData;

import fawc.buptroom.services.TimeInfo;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Random;


public class ShakeTestActivity extends AppCompatActivity {

    private final Random random = new Random();
    //    private int curClass = 0;//现在时间所处节数，用于emptyroom.get_show_content返回数组的下标
//    private String htmlBody = "";
//    private final ArrayList<String> BuildingName = new ArrayList<>();
//    private ArrayList<String> tempClass = new ArrayList<>();
    private Map<String, ?> buildingMap;
    // private final EmptyRoom emptyroom = new EmptyRoom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake_test);
        Intent intent = getIntent();
        try {
            SerializableMap serializableMap = (SerializableMap) intent.getSerializableExtra("BuildingMap");
            buildingMap = serializableMap.getMap();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //添加toolbar返回
        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        toolbar.setTitle("摇一摇");
        toolbar.setSubtitle("你摇出来的结果是");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(view -> finish());
        TextView recommendedRoom = findViewById(R.id.RecommandRoom);
        recommendedRoom.setText(Handle());
    }

    private String Handle() {
        TimeInfo timeInfo = new TimeInfo();
        String buildingName;
        String result;
        ArrayList<String> resultRoom = new ArrayList<>();
        if (timeInfo.getCurClass_int() == 0)
            return "现在是休息时间";
        else {
            if (buildingMap == null)
                return "数据错误！";
            for (String s : buildingMap.keySet()) {
                System.out.println((String) Objects.requireNonNull(buildingMap.get(s)));

                String testStr = (String) Objects.requireNonNull(buildingMap.get(s));
                if (testStr.length() != 196)
                    continue;
                int[][] emptyArr = ServerData.convertToArray(testStr, 7, 14);
                if (emptyArr[timeInfo.getDayCounter()][timeInfo.getCurClass_int() - 1] == 1) {
                    if (s.charAt(0) == '1') {
                        buildingName = "教一楼";
                    } else if (s.charAt(0) == '2') {
                        buildingName = "教二楼";
                    } else if (s.charAt(0) == '3') {
                        buildingName = "教三楼";
                    } else if (s.charAt(0) == '4') {
                        buildingName = "教四楼";
                    } else if (s.charAt(0) == 'N') {
                        buildingName = "教学实验综合楼-北楼";
                    } else if (s.charAt(0) == 'S') {
                        buildingName = "教学实验综合楼-南楼";
                    } else {
                        buildingName = "沙河校区多功能厅";
                    }
                    resultRoom.add(buildingName + "\n" + s);
                }
            }
            if (resultRoom.isEmpty())
                result = "无可用教室";
            else
                result = resultRoom.get(random.nextInt(resultRoom.size()));
            return result;
        }
    }

}
