package cn.ycbjie.ycstatusbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.ycbjie.ycstatusbarlib.dlBar.DlStatusBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.fl_title_menu)
    FrameLayout flTitleMenu;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ll_main)
    LinearLayout llMain;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.btn_statusbar_color)
    Button btnStatusbarColor;
    @Bind(R.id.btn_statusbar_color_toolbar)
    Button btnStatusbarColorToolbar;
    @Bind(R.id.btn_statusbar_translucent)
    Button btnStatusbarTranslucent;
    @Bind(R.id.btn_statusbar_color_coordinator)
    Button btnStatusbarColorCoordinator;
    @Bind(R.id.btn_statusbar_translucent_coordinator)
    Button btnStatusbarTranslucentCoordinator;
    @Bind(R.id.btn_statusbar_white)
    Button btnStatusbarWhite;
    @Bind(R.id.btn_statusbar_white_toolbar)
    Button btnStatusbarWhiteToolbar;
    @Bind(R.id.btn_statusbar_white_coordinator)
    Button btnStatusbarWhiteCoordinator;
    @Bind(R.id.btn_statusbar_fragment)
    Button btnStatusbarFragment;
    @Bind(R.id.btn_statusbar_fragment2)
    Button btnStatusbarFragment2;
    @Bind(R.id.btn_statusbar_fragment3)
    Button btnStatusbarFragment3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDrawerLayoutStatus();
        initBar();
        initNav();
        initListener();
    }


    /**
     * 初始化侧滑菜单的状态栏
     */
    private void initDrawerLayoutStatus() {
        //为DrawerLayout 布局设置状态栏颜色,纯色
        /*StatusBarUtils.setColorNoTranslucentForDrawerLayout(this, drawerLayout,
                getResources().getColor(R.color.colorTheme));*/
        //为DrawerLayout 布局设置状态栏变色，也就是加上透明度
        DlStatusBar.setColorForDrawerLayout(this, drawerLayout,
                getResources().getColor(R.color.colorTheme), 0);
    }

    /**
     * 初始化ActionBar按钮
     */
    private void initBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 初始化侧滑菜单
     */
    private void initNav() {
        navView.inflateHeaderView(R.layout.nav_header_main);
    }

    private void initListener() {
        flTitleMenu.setOnClickListener(this);
        btnStatusbarColor.setOnClickListener(this);
        btnStatusbarColorCoordinator.setOnClickListener(this);
        btnStatusbarColorToolbar.setOnClickListener(this);
        btnStatusbarTranslucent.setOnClickListener(this);
        btnStatusbarTranslucentCoordinator.setOnClickListener(this);
        btnStatusbarWhite.setOnClickListener(this);
        btnStatusbarWhiteToolbar.setOnClickListener(this);
        btnStatusbarWhiteCoordinator.setOnClickListener(this);
        btnStatusbarFragment.setOnClickListener(this);
        btnStatusbarFragment2.setOnClickListener(this);
        btnStatusbarFragment3.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.fl_title_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.btn_statusbar_color:
                intent.setClass(this, StatusBarColorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_color_toolbar:
                intent.setClass(this, StatusBarColorToolBarActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_translucent:
                intent.setClass(this, StatusBarTranslucent.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_color_coordinator:
                intent.setClass(this, StatusBarColorCoordinatorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_translucent_coordinator:
                intent.setClass(this, StatusBarTranslucentCoordinatorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_white:
                intent.setClass(this, StatusBarWhiteActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_white_toolbar:
                intent.setClass(this, StatusBarWhiteToolBarActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_white_coordinator:
                intent.setClass(this, StatusBarWhiteCoordinatorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_fragment:
                intent.setClass(this, StatusBarFragmentActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_fragment2:
                intent.setClass(this, StatusBarFragment2Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_statusbar_fragment3:
                intent.setClass(this, StatusBarFragment3Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * onBackPressed、onKeyDown和onKeyUp这三个事件的区别
     */
    @Override
    public void onBackPressed() {
        Log.e("触摸监听", "onBackPressed");
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
