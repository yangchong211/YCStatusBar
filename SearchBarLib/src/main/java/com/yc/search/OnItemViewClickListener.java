package com.yc.search;

import android.view.View;

public interface OnItemViewClickListener {
    void OnItemClickListener(int position, View v);
    void OnItemDeleteListener(int position,View v);
}
