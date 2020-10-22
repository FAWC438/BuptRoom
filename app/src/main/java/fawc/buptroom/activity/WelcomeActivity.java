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


/*
 * Created by think on 20162016/10/9 000920:57
 * PACKAGE:fawc.buptroom
 * PROJECT:BuptRoom
 */

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

//    public void Notification_show(String Notification_content) {
//        /**
//         * Created by fawc on 2016/9/28 0028 9:26
//         * Parameter [Notification_content] 显示的正文
//         * Return void
//         * CLASS:MainActivity
//         * FILE:MainActivity.java
//         */
//
//        Notification.Builder mBuilder = new Notification.Builder(this);
//        Bitmap LB = BitmapFactory.decodeResource(getResources(), R.drawable.logoko);
//        Intent resultIntent = new Intent(WelcomeActivity.this, MainActivity.class);
//        resultIntent.putExtra("WrongNet", WrongNet);
//        resultIntent.putExtra("HtmlBody", htmlbody);
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(
//                WelcomeActivity.this, 0, resultIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentTitle("BuptRoom")                        //标题
//                .setContentText(Notification_content)             //内容
//                .setTicker("BuptRoom提醒")                      //收到信息后状态栏显示的文字信息
//                .setWhen(System.currentTimeMillis())              //设置通知时间
//                .setSmallIcon(R.drawable.logoko)             //设置小图标
//                .setLargeIcon(LB)                     //设置大图标
//                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
//                .setAutoCancel(true)                           //设置点击后取消Notification
//                .setOngoing(false)                        //设置正在进行
//                .setContentIntent(resultPendingIntent);
//        NotificationManager mNManager;
//        mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        mNManager.notify(1, mBuilder.build());
//    }

}