package fawc.buptroom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import fawc.buptroom.services.EmptyRoom;
import fawc.buptroom.R;
import fawc.buptroom.services.TimeInfo;

/**
 * Created by think on 20162016/10/12 001211:10
 * PACKAGE:fawc.buptroom
 * PROJECT:BuptRoom
 */

public class ShakeTestActivity extends AppCompatActivity {

    private final Random random = new Random();
    private int curClass = 0;//现在时间所处节数，用于emptyroom.get_show_content返回数组的下标
    private final TimeInfo timeinfo = new TimeInfo();
    private String htmlBody = "";
    private final ArrayList<String> BuildingName = new ArrayList<>();
    private ArrayList<String> tempClass = new ArrayList<>();
    private final EmptyRoom emptyroom = new EmptyRoom();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shake_test);
        Intent intent = getIntent();
        htmlBody = intent.getStringExtra("htmlbody");
        //添加toolbar返回
        Toolbar toolbar = findViewById(R.id.toolbar_setting);
        toolbar.setTitle("摇一摇");
        toolbar.setSubtitle("你摇出来的结果是");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        TextView recommendedRoom = findViewById(R.id.RecommandRoom);
        Init();
        recommendedRoom.setText(Handle());
    }

    private void Init() {
        BuildingName.add("教一楼");
        BuildingName.add("教二楼");
        BuildingName.add("教三楼");
        BuildingName.add("教四楼");
        BuildingName.add("图书馆");
        timeinfo.timeSetting();
        if (timeinfo.curTime.contains("12")) {
            curClass = 0;
        } else if (timeinfo.curTime.contains("34")) {
            curClass = 1;
        } else if (timeinfo.curTime.contains("56")) {
            curClass = 2;
        } else if (timeinfo.curTime.contains("78")) {
            curClass = 3;
        } else if (timeinfo.curTime.contains("9")) {
            curClass = 4;
        } else if (timeinfo.curTime.contains("10")) {
            curClass = 5;
        } else if (timeinfo.curTime.contains("休息")) {
            curClass = 6;
        }
    }

    private String Handle() {
        int countmax = 0;
        int maxi = 0;
        String temp;
        String result;
        int roomcount = 0;
        ArrayList<String> resultRoom = new ArrayList<>();
        if (curClass == 6)
            return "现在是休息时间";
        else {
            for (int i = 0; i < 5; i++) {
                tempClass.clear();
                tempClass = emptyroom.get_show_content(BuildingName.get(i), htmlBody);
                if (tempClass.get(curClass).length() > countmax) {
                    countmax = tempClass.get(curClass).length();
                    maxi = i;
                }
            }
            tempClass.clear();
            tempClass = emptyroom.get_show_content(BuildingName.get(maxi), htmlBody);
            temp = tempClass.get(curClass);
            for (int i = 0; i < countmax; i++) {
                if (temp.charAt(i) == '-') {
                    resultRoom.add(temp.substring(i + 1, i + 4));
                    roomcount++;
                }
            }
            result = BuildingName.get(maxi) + "\n" + resultRoom.get(random.nextInt(roomcount));
            return result;
        }
    }

}
