package com.yc.search;


import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.List;

public class SearchSavedState extends View.BaseSavedState {

    protected int isSearchBarVisible;
    protected int suggestionsVisible;
    protected int speechMode;
    protected int searchIconRes;
    protected int navIconResId;
    protected String hint;
    protected List suggestions;
    protected int maxSuggestions;

    public static final Creator<SearchSavedState> CREATOR = new Creator<SearchSavedState>() {
        @Override
        public SearchSavedState createFromParcel(Parcel source) {
            return new SearchSavedState(source);
        }

        @Override
        public SearchSavedState[] newArray(int size) {
            return new SearchSavedState[size];
        }
    };

    public SearchSavedState(Parcel source) {
        super(source);
        isSearchBarVisible = source.readInt();
        suggestionsVisible = source.readInt();
        speechMode = source.readInt();

        navIconResId = source.readInt();
        searchIconRes = source.readInt();
        hint = source.readString();
        suggestions = source.readArrayList(null);
        maxSuggestions = source.readInt();
    }

    public SearchSavedState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(isSearchBarVisible);
        out.writeInt(suggestionsVisible);
        out.writeInt(speechMode);

        out.writeInt(searchIconRes);
        out.writeInt(navIconResId);
        out.writeString(hint);
        out.writeList(suggestions);
        out.writeInt(maxSuggestions);
    }
}
