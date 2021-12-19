package com.ycbjie.ycstatusbar;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.yc.search.AppSearchBar;
import com.yc.search.OnSearchActionListener;
import com.yc.statusbar.bar.StateAppBar;

import cn.ycbjie.ycstatusbar.R;

public class StatusBarColorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusbar_color);

        StateAppBar.setStatusBarColor(this,
                ContextCompat.getColor(this, R.color.colorAccent));

        initSearch();
    }

    private AppSearchBar asbBar;
    private TextView tvSearchStart;
    private TextView tvSearchOpen;
    private void initSearch() {
        asbBar = findViewById(R.id.asb_bar);
        tvSearchStart = findViewById(R.id.tv_search_start);
        tvSearchOpen = findViewById(R.id.tv_search_open);

        asbBar = findViewById(R.id.asb_bar);
        asbBar.setCardViewElevation(10);
        asbBar.setHint("输入搜索数据");
        asbBar.setTextHintColor(getResources().getColor(R.color.colorAccent));
        asbBar.setText("搜索");
        asbBar.setPlaceHolder("开始搜索");
        asbBar.setOnSearchActionListener(new OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
        tvSearchStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asbBar.openSearch();
            }
        });
        tvSearchOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asbBar.closeSearch();
            }
        });
    }
}
