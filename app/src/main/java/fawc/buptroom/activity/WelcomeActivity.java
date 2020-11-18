package fawc.buptroom.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import fawc.buptroom.MainActivity;
import fawc.buptroom.R;
import fawc.buptroom.services.Webget;


public class WelcomeActivity extends Activity {

    private Webget webget;
    private int HaveNetFlag = 0;
    private String htmlBody = null;
    private int WrongNet = 2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        WebView webView = findViewById(R.id.GetHtml);
        AssetManager mgr = getAssets();//得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/Roboto-Italic.ttf");//根据路径得到Typeface
        TextView chickenSoup = findViewById(R.id.chicken_soup);
        chickenSoup.setTypeface(tf);//设置字体
        tf = Typeface.createFromAsset(mgr, "fonts/Roboto-MediumItalic.ttf");
        TextView Title = findViewById(R.id.welcome_title);
        Title.setTypeface(tf);//设置字体
        webget = new Webget();


        webget.init(webView);
        HaveNetFlag = webget.WebInit();


        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                ImageView img = findViewById(R.id.welcomeimg);
                Animation animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.enlarge);
                animation.setFillAfter(true);
                img.startAnimation(animation);
            }
        }, 50);


        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                WrongNet = webget.getWrongnet();
                HaveNetFlag = webget.getHaveNetFlag();
                htmlBody = webget.getHtmlbody();
                Log.i("welcome", "2HaveNetFlag: " + HaveNetFlag);
                Log.i("welcome", "2Wrongnet: " + WrongNet);
                Log.i("welcome", "2html: " + htmlBody);
//                if (WrongNet == 0)
//                    Notification_show("教室信息拉取完成");
//                else
//                    Notification_show("教室信息拉取失败");
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                intent.putExtra("WrongNet", WrongNet);
                intent.putExtra("HtmlBody", htmlBody);
                startActivity(intent);
                WelcomeActivity.this.finish();

            }

        }, 2500);
    }

}