package cn.ycbjie.ycstatusbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class StatusBarColorCoordinatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar_color_coordinator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("StatusBarColorCoordinatorLayout");

        StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
    }
}
