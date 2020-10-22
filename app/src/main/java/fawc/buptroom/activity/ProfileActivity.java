package fawc.buptroom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import fawc.buptroom.R;

/*
 * Created by think on 20162016/10/12 001220:47
 * PACKAGE:fawc.buptroom
 * PROJECT:BuptRoom
 */

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        toolbar.setTitle("个人首页");
        Intent intent = getIntent();
        int startCounts = intent.getIntExtra("StartCounts", 1);
        Button startCountsBtn = findViewById(R.id.startcountsbt);
        startCountsBtn.setText(String.format("%s%s", getString(R.string.usetimes), startCounts));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }
}
