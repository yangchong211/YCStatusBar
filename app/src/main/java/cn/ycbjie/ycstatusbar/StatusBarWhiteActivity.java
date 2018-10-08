package cn.ycbjie.ycstatusbar;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class StatusBarWhiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar_white);

        StateAppBar.setStatusBarLightMode(this, Color.WHITE);
    }
}
