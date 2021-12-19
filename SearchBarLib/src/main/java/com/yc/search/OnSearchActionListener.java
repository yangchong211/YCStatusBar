package com.yc.search;

public interface OnSearchActionListener {

    void onSearchStateChanged(boolean enabled);

    void onSearchConfirmed(CharSequence text);

    void onButtonClicked(int buttonCode);

}
