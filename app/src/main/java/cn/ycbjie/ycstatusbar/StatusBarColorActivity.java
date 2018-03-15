package cn.ycbjie.ycstatusbar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

public class StatusBarColorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar_color);

        YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
    }
}
