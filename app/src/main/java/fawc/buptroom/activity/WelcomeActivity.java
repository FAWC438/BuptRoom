package fawc.buptroom.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import fawc.buptroom.MainActivity;
import fawc.buptroom.R;
import fawc.buptroom.services.ServerData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class WelcomeActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        AssetManager mgr = getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Roboto-Italic.ttf");//根据路径得到Typeface
        TextView chickenSoup = findViewById(R.id.chicken_soup);
        chickenSoup.setTypeface(tf);//设置字体
        tf = Typeface.createFromAsset(mgr, "fonts/Roboto-MediumItalic.ttf");
        TextView Title = findViewById(R.id.welcome_title);
        Title.setTypeface(tf);//设置字体

        SharedPreferences sp = getSharedPreferences(getString(R.string.SharedPreferencesFileName), MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();
        ServerData serverData = new ServerData();
        final int[] netWrong = {0}; // 0为false，1为true

        new Handler().postDelayed(() -> {
            //execute the task
            ImageView img = findViewById(R.id.welcomeimg);
            Animation animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.enlarge);
            animation.setFillAfter(true);
            img.startAnimation(animation);
        }, 50);


        new Handler().postDelayed(() -> {
            //execute the task
            Map<String, int[][]> data = serverData.doPostToServer();
            Log.d("Server data", String.valueOf(data == null));
            if (data == null) {
                netWrong[0] = 1;
            } else {
                Date day = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sp_editor.putString("hasData", df.format(day));
                for (Map.Entry<String, int[][]> d : data.entrySet()) {
                    sp_editor.putString(d.getKey(), ServerData.convertToString(d.getValue(), 7, 14));
                }
                sp_editor.commit();
            }
//                if (WrongNet == 0)
//                    Notification_show("教室信息拉取完成");
//                else
//                    Notification_show("教室信息拉取失败");
        }, 2000);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            intent.putExtra("netWrong", netWrong[0]);
            startActivity(intent);
            WelcomeActivity.this.finish();

        }, 2500);
    }

}