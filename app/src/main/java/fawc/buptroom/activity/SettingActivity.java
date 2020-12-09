package fawc.buptroom.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;
import fawc.buptroom.R;
import fawc.buptroom.utils.ColorPicker;

import java.util.ArrayList;


public class SettingActivity extends AppCompatActivity {

    private Button colorBtn;
    private Button saveBtn;
    private ColorPicker colorpicker;
    private int getColorInt;
    private LinearLayout settingLayout;
    private String red;
    private String green;
    private String blue;
    private Toolbar toolbar;
    private static final String TAG = "COLOR";
    private final int[] colorR = {161, 241, 179, 194, 71, 63, 23, 236, 168, 202, 139, 117, 216, 210, 177, 167, 255, 157, 65, 239, 216, 118};
    private final int[] colorG = {226, 248, 93, 40, 65, 80, 125, 87, 130, 105, 66, 101, 183, 242, 109, 133, 51, 42, 74, 223, 237, 146};
    private final int[] colorB = {196, 240, 68, 42, 103, 100, 174, 54, 119, 36, 85, 75, 20, 231, 98, 98, 0, 49, 79, 174, 242, 97};
    private int minDis = 195075;//255*255*3
    private int maxNum = 0;
    private int btnflag = 0;
    private int first = 0;

    private final ArrayList<Integer> wallpapers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        this.setTitle("主题选择");
        wallpapersInit();

        //添加toolbar返回
        toolbar = findViewById(R.id.toolbar_setting);
        toolbar.setTitle("主题设置");
        toolbar.setSubtitle("好看吗");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(view -> finish());
        colorBtn = findViewById(R.id.getcolor);
        saveBtn = findViewById(R.id.savecolor);
        colorpicker = findViewById(R.id.colorPicker);
        settingLayout = findViewById(R.id.settinglayout);
        colorBtn.setOnClickListener(view -> {
            first = 1;
            if (btnflag == 0) {
                getColorInt = colorpicker.getColor();
                Log.i(TAG, convertToARGB(getColorInt)); //不可以删除,用于产生十六进制red blue green设置标题栏
                minDis = 195075;
                maxNum = findMaxNum(Color.red(getColorInt), Color.green(getColorInt), Color.blue(getColorInt));
                settingLayout.setBackgroundResource(wallpapers.get(maxNum));
                toolbar.setBackgroundColor(Color.parseColor("#" + red + green + blue));

                Window window = SettingActivity.this.getWindow();
                //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //设置状态栏颜色
                window.setStatusBarColor(Color.parseColor("#" + red + green + blue));

                colorBtn.setText("再选一次");
                colorpicker.setVisibility(View.INVISIBLE);
                btnflag = 1;
            } else {
                colorpicker.setVisibility(View.VISIBLE);
                colorBtn.setText("选取颜色");
                btnflag = 0;
            }
        });
    }

    public int findMaxNum(int r, int g, int b) {

        int i;
        int maxi = 0;
        int temp;
        for (i = 0; i < 22; i++) {
            temp = (r - colorR[i]) * (r - colorR[i]) + (g - colorG[i]) * (g - colorG[i]) + (b - colorB[i]) * (b - colorB[i]);
            if (temp < minDis) {
                maxi = i;
                minDis = temp;
            }
        }
        return maxi;
    }

    public void wallpapersInit() {
        wallpapers.add(R.drawable.ailv);
        wallpapers.add(R.drawable.chabai);
        wallpapers.add(R.drawable.chase);
        wallpapers.add(R.drawable.chi);
        wallpapers.add(R.drawable.dai);
        wallpapers.add(R.drawable.dailan);
        wallpapers.add(R.drawable.dianqing);
        wallpapers.add(R.drawable.feise);
        wallpapers.add(R.drawable.guan);
        wallpapers.add(R.drawable.hupo);
        wallpapers.add(R.drawable.jiangzi);
        wallpapers.add(R.drawable.li);
        wallpapers.add(R.drawable.qiuxiangse);
        wallpapers.add(R.drawable.shuilv);
        wallpapers.add(R.drawable.tan);
        wallpapers.add(R.drawable.tuose);
        wallpapers.add(R.drawable.yan);
        wallpapers.add(R.drawable.yanzhi);
        wallpapers.add(R.drawable.yaqing);
        wallpapers.add(R.drawable.yase);
        wallpapers.add(R.drawable.yuebai);
        wallpapers.add(R.drawable.zhuqing);
    }

    /**
     * 转化为ARGB格式字符串
     * For custom purposes. Not used by ColorPickerPreferrence
     */
    public String convertToARGB(int color) {
        String alpha = Integer.toHexString(Color.alpha(color));
        red = Integer.toHexString(Color.red(color));
        green = Integer.toHexString(Color.green(color));
        blue = Integer.toHexString(Color.blue(color));

        if (alpha.length() == 1) {
            alpha = "0" + alpha;
        }

        if (red.length() == 1) {
            red = "0" + red;
        }

        if (green.length() == 1) {
            green = "0" + green;
        }

        if (blue.length() == 1) {
            blue = "0" + blue;
        }

        return "#" + alpha + " " + red + " " + green + " " + blue;
    }

    public void SaveColorNow(View view) {
        if (first == 0) {
            Snackbar.make(saveBtn, "请先选取颜色", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        } else {
            Snackbar.make(saveBtn, "修改主题成功，重启后生效", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SharedPreferences sharedPreferences = getSharedPreferences("colorsave", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maincolor", Color.parseColor("#" + red + green + blue));
            editor.putInt("imgnum", maxNum);
            Log.i(TAG, Integer.toString(maxNum));
            editor.apply();//提交修改
        }


    }

}
