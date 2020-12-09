package fawc.buptroom;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.*;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.hugeterry.updatefun.UpdateFunGO;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import fawc.buptroom.Serializable.SerializableMap;
import fawc.buptroom.services.ServerData;
import fawc.buptroom.services.ShakeService;
import fawc.buptroom.services.TimeInfo;
import fawc.buptroom.fragment.AboutFragment;
import fawc.buptroom.fragment.BuildingFragment;
import fawc.buptroom.fragment.HomePageFragment;
import fawc.buptroom.fragment.ShakeFragment;
import fawc.buptroom.fragment.VersionFragment;
import fawc.buptroom.services.UpdateUrl;
import fawc.buptroom.utils.CustomPopDialog;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String[] interesting = {" ヾ(o◕∀◕)ﾉ新的一周新的开始!",
            " π__π默默学习不说话",
            " （╯－＿－）╯╧╧ 学海无涯苦作舟",
            " (´･ω･｀)转眼间一周就过了一半了呢",
            " ( ￣ ▽￣) o╭╯明天就是周末了！",
            " o(*≧▽≦)ツ周六浪起来~",
            " (╭￣3￣)╭♡ 忘记明天是周一吧"};

    private int netWrong = 1;//网络状况标志位 0为网络无错误，1为网络错误
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Button snackBarTemp;
    public static final String TAG = "MainActivity";
    private int startCounts = 0;

    private TimeInfo timeShow;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 保证主线程能够访问http协议
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent intent = getIntent();
        netWrong = intent.getIntExtra("netWrong", 1);

        if (netWrong == 0) {
            showAlertDialog(8);
        } else {
            showAlertDialog(9);
        }

        this.setTitle("首页");
        HomePageFragment homepagefragment = new HomePageFragment();
        manager = this.getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frame, homepagefragment);
        transaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        snackBarTemp = findViewById(R.id.snackbar_temp);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        ImageButton profileBt = headView.findViewById(R.id.profile);

        SharedPreferences sharedPreferences = getSharedPreferences("colorSave", Context.MODE_PRIVATE);
        String mainColor = sharedPreferences.getString("mainColor", null);
        Log.i("mainColor", mainColor + "");
        SharedPreferences sp = getSharedPreferences(getString(R.string.SharedPreferencesFileName), MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();

        Window window = MainActivity.this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色

        if (mainColor != null) {
            window.setStatusBarColor(Color.parseColor(mainColor));
            toolbar.setBackgroundColor(Color.parseColor(mainColor));
        }

        profileBt.setOnClickListener(view -> {
            Intent intent1 = new Intent();
            intent1.putExtra("StartCounts", startCounts);
            intent1.setClassName(MainActivity.this, "fawc.buptroom.activity.ProfileActivity");
            startActivity(intent1);
        });
        AppStartCounts();
        if (netWrong == 1 && sp.contains("hasData"))
            showAlertDialog(1);
        /*用于支持自动更新*/

//        UpdateKey.API_TOKEN = "656c59bd6468ce6ab95b9013a8657b09";
//        UpdateKey.APP_ID = "5fd0a3edb2eb4609be495aa8";
//        UpdateKey.DialogOrNotification = UpdateKey.WITH_DIALOG;
//        UpdateFunGO.init(this);

    }

    /**
     * 从服务器更新数据
     */
    public void refreshData() {
        SharedPreferences sp = getSharedPreferences(getString(R.string.SharedPreferencesFileName), MODE_PRIVATE);
        SharedPreferences.Editor sp_editor = sp.edit();

        SimpleDateFormat df_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date day = new Date();
        sp_editor.putString("hasData", df_time.format(day));
        Map<String, int[][]> data = new ServerData().doPostToServer();
        if (data != null)
            netWrong = 0;
        else {
            netWrong = 1;
            return;
        }
        for (Map.Entry<String, int[][]> d : data.entrySet()) {
            sp_editor.putString(d.getKey(), ServerData.convertToString(d.getValue(), 7, 14));
        }
        sp_editor.commit();
    }

    public void AppStartCounts() {

        SharedPreferences sharedPreferences = getSharedPreferences("startcount", Context.MODE_PRIVATE);
        startCounts = sharedPreferences.getInt("startcount", 0);
        startCounts++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("startcount", startCounts);
        editor.commit();//提交修改
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharedPreferences sp = getSharedPreferences(getString(R.string.SharedPreferencesFileName), MODE_PRIVATE);

        if (id == R.id.feedback) {
            String[] email = {"kevin_lgh@outlook.com"}; // 需要注意，email必须以数组形式传入
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822"); // 设置邮件格式
            intent.putExtra(Intent.EXTRA_EMAIL, email); // 接收人
            intent.putExtra(Intent.EXTRA_SUBJECT, "BuptRoom反馈"); // 主题
            intent.putExtra(Intent.EXTRA_TEXT, "Hi~ o(*￣▽￣*)ブ我在使用BuptRoom时遇到了以下问题，请尽快解决:\n"); // 正文
            startActivity(Intent.createChooser(intent, "请选择邮件类应用以发送错误报告"));
            return true;
        } else if (id == R.id.timeinfo) {
            timeShow = new TimeInfo();
            showAlertDialog(2);
        } else if (id == R.id.download) {
            refreshData();
            if (netWrong == 1 || !sp.contains("hasData")) {
//                Log.i(TAG, "离线下载错误");
                showAlertDialog(3);
            } else {
                Date day = new Date();
                SimpleDateFormat df_day = new SimpleDateFormat("yyyy-MM-dd");
                String dayStr = df_day.format(day);

                if (sp.getString("hasData", null).split(" ")[0].equals(dayStr)) {
//                        Log.i(TAG, "今日已经离线");
                    showAlertDialog(4);
                } else {
//                        Log.i(TAG, "离线成功");
                    showAlertDialog(5);
                }
            }
        } else if (id == R.id.update) {
//            UpdateFunGO.manualStart(this);
            double v = Double.MAX_VALUE;
            try {
                v = Double.parseDouble(this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("v: " + v);
            System.out.println("Info: " + UpdateUrl.getUpdateInfo());
            if (UpdateUrl.getUpdateInfo() > v) {
                AlertDialog alertDialog;
                AlertDialog.Builder adBd = new AlertDialog.Builder(MainActivity.this);
                adBd.setTitle("检测到新版本");
                adBd.setMessage("是否更新？");
                adBd.setPositiveButton("是", (dialog, which) -> {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(UpdateUrl.getDownloadUrl());//此处填链接
                    intent.setData(content_url);
                    startActivity(intent);
                });
                adBd.setNegativeButton("否", (dialog, which) -> {
                    // do nothing
                });
                alertDialog = adBd.create();
                alertDialog.show();
            } else if (UpdateUrl.getUpdateInfo() == 0) {
                Toast.makeText(MainActivity.this, "网络错误，更新失败", 1).show();
            } else {
                Toast.makeText(MainActivity.this, "应用已经是最新啦！", 1).show();
            }
        } else if (id == R.id.share) {
            Resources r = MainActivity.this.getResources();
            Bitmap bmp = BitmapFactory.decodeResource(r, R.drawable.share_app);
            CustomPopDialog.Builder dialogBuild = new CustomPopDialog.Builder(MainActivity.this);
            dialogBuild.setImage(bmp);
            CustomPopDialog dialog = dialogBuild.create();
            dialog.setCanceledOnTouchOutside(true);// 点击外部区域关闭
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            showAlertDialog(6);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        SharedPreferences sp = getSharedPreferences(getString(R.string.SharedPreferencesFileName), MODE_PRIVATE);
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.jiaoshi) {
            if (netWrong == 1) {
                showAlertDialog(7);
            } else {
                if (isServiceWork(MainActivity.this)) {
                    Intent stopIntent = new Intent(this, ShakeService.class);
                    stopService(stopIntent);
                }
                this.setTitle("教室查询");
                BuildingFragment buildingfragment = new BuildingFragment();
                Bundle bundle = new Bundle();
                // 序列化sp中的数据以便传递
                SerializableMap myMap = new SerializableMap();
                myMap.setMap(sp.getAll());
                bundle.putSerializable("BuildingMap", myMap);
                buildingfragment.setArguments(bundle);
                buildingfragment.Init();
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, buildingfragment);
                transaction.commit();
            }
        } else if (id == R.id.developer_opensource) {
            if (isServiceWork(MainActivity.this)) {
                Intent stopintent = new Intent(this, ShakeService.class);
                stopService(stopintent);
            }
            this.setTitle("软件信息");
            AboutFragment aboutfragment = new AboutFragment();
            Bundle bundle = new Bundle();
            try {
                bundle.putString("Version", getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            aboutfragment.setArguments(bundle);
            manager = this.getSupportFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.frame, aboutfragment);
            transaction.commit();
        } else if (id == R.id.version) {
            if (isServiceWork(MainActivity.this)) {
                Intent stopIntent = new Intent(this, ShakeService.class);
                stopService(stopIntent);
            }
            this.setTitle("版本介绍");
            VersionFragment versionfragment = new VersionFragment();
            manager = this.getSupportFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.frame, versionfragment);
            transaction.commit();
        } else if (id == R.id.homepage) {
            if (isServiceWork(MainActivity.this)) {
                Intent stopIntent = new Intent(this, ShakeService.class);
                stopService(stopIntent);
            }
            this.setTitle("首页");
            HomePageFragment homepagefragment = new HomePageFragment();
            manager = this.getSupportFragmentManager();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.frame, homepagefragment);
            transaction.commit();
        } else if (id == R.id.theme_choose) {
            if (isServiceWork(MainActivity.this)) {
                Intent stopintent = new Intent(this, ShakeService.class);
                stopService(stopintent);
            }
            Intent intent = new Intent();
            intent.setClassName(this, "fawc.buptroom.activity.SettingActivity");
            startActivity(intent);

        } else if (id == R.id.shake) {
            if (netWrong == 1) {
                showAlertDialog(7);
            } else {
                this.setTitle("摇一摇");
                Intent intent = new Intent();
                // 序列化sp中的数据以便传递
                SerializableMap myMap = new SerializableMap();
                myMap.setMap(sp.getAll());
                intent.putExtra("BuildingMap", myMap);
                intent.setClass(this, ShakeService.class);
                startService(intent);
                ShakeFragment shakefragment = new ShakeFragment();
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, shakefragment);
                transaction.commit();
            }

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showAlertDialog(int paramNum) {
        Handler displayHandler;
        displayHandler = new DisplayHandler();
        Message msg = displayHandler.obtainMessage();
        msg.what = paramNum;
        displayHandler.sendMessage(msg);

    }

    public boolean isServiceWork(Context mContext) {
        /*
         * 判断Shake服务是否正在运行的方法
         *
         * @param mContext
         * @param serviceName
         *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
         * @return true代表正在运行，false代表服务没有正在运行
         */

        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        assert myAM != null;
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo runningServiceInfo : myList) {
            String mName = runningServiceInfo.service.getClassName();
            if (mName.equals("fawc.buptroom.services.ShakeService")) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        UpdateFunGO.onResume(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        UpdateFunGO.onStop(this);
//    }

    @SuppressLint("HandlerLeak")
    class DisplayHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Snackbar.make(snackBarTemp, "已从离线内容中加载", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                case 2:
                    Snackbar.make(snackBarTemp, "今天是" + timeShow.getTimeString() + " " + timeShow.getCurClass_str() + "\n" + interesting[timeShow.getDayCounter()], Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                case 3:
                    Snackbar.make(snackBarTemp, "刷新错误", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                case 4:
                    Snackbar.make(snackBarTemp, "刷新成功（已经是今天最新数据啦，不用频繁刷新哦）", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                case 5:
                    Snackbar.make(snackBarTemp, "刷新成功", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                case 6:
                    new AlertDialog.Builder(MainActivity.this).setTitle("确认退出吗？")
                            .setIcon(R.drawable.logoko)
                            .setPositiveButton("确定", (dialog, which) -> {
                                // 点击“确认”后的操作
                                if (isServiceWork(MainActivity.this)) {
                                    Intent stopIntent = new Intent(MainActivity.this, ShakeService.class);
                                    Log.i(TAG, "The ShakeService has been closed");
                                    stopService(stopIntent);
                                }
                                MainActivity.this.finish();
                            })
                            .setNegativeButton("返回", (dialog, which) -> {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }).show();
                    break;
                case 7:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("网络错误");
                    builder.setMessage("您所在网络环境可能不佳，无法得到空闲教室信息" +
                            "\n请检查网络连接或重启软件");
                    builder.setNegativeButton("我知道了", null);
                    builder.show();
                    break;
                case 8:
                    Toast netCheckOK = Toast.makeText(getApplicationContext(),
                            "", Toast.LENGTH_LONG);
                    netCheckOK.setGravity(Gravity.TOP, 0, 200);
                    netCheckOK.setText("教室信息已更新");
                    netCheckOK.show();
                    break;
                case 9:
                    Toast netCheckWrong = Toast.makeText(getApplicationContext(),
                            "", Toast.LENGTH_LONG);
                    netCheckWrong.setGravity(Gravity.TOP, 0, 200);
                    netCheckWrong.setText("教室信息更新失败，请刷新");
                    netCheckWrong.show();
                    break;
                default:
                    break;

            }
        }
    }

}
