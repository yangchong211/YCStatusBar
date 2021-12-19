package com.yc.search;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class AppSearchBar extends FrameLayout implements View.OnClickListener,
        Animation.AnimationListener, OnItemViewClickListener,
        View.OnFocusChangeListener, TextView.OnEditorActionListener {

    public static final int BUTTON_SPEECH = 1;
    public static final int BUTTON_NAVIGATION = 2;
    public static final int BUTTON_BACK = 3;
    public static final int VIEW_VISIBLE = 1;
    public static final int VIEW_INVISIBLE = 0;
    private CardView searchBarCardView;
    private LinearLayout inputContainer;
    private ImageView navIcon;
    private ImageView menuIcon;
    private ImageView searchIcon;
    private ImageView arrowIcon;
    private ImageView clearIcon;
    private EditText searchEdit;
    private TextView placeHolder;
    private View suggestionDivider;
    private OnSearchActionListener onSearchActionListener;
    private boolean searchOpened;
    private boolean suggestionsVisible;
    private boolean isSuggestionsEnabled = true;
    private SuggestionsAdapter adapter;
    private float destiny;

    private PopupMenu popupMenu;

    private int navIconResId;
    private int menuIconRes;
    private int searchIconRes;
    private int speechIconRes;
    private int arrowIconRes;
    private int clearIconRes;

    private boolean speechMode;
    private int maxSuggestionCount;
    private boolean navButtonEnabled;
    private boolean roundedSearchBarEnabled;
    private int dividerColor;
    private int searchBarColor;

    private CharSequence hintText;
    private CharSequence placeholderText;
    private int textColor;
    private int hintColor;
    private int placeholderColor;
    private int navIconTint;
    private int menuIconTint;
    private int searchIconTint;
    private int arrowIconTint;
    private int clearIconTint;

    private boolean navIconTintEnabled;
    private boolean menuIconTintEnabled;
    private boolean searchIconTintEnabled;
    private boolean arrowIconTintEnabled;
    private boolean clearIconTintEnabled;
    private boolean borderlessRippleEnabled = false;

    private int textCursorColor;
    private int highlightedTextColor;

    public AppSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AppSearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppSearchBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.custom_search_bar, this);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialSearchBar);

        speechMode = array.getBoolean(R.styleable.MaterialSearchBar_mt_speechMode, false);
        maxSuggestionCount = array.getInt(R.styleable.MaterialSearchBar_mt_maxSuggestionsCount, 3);
        navButtonEnabled = array.getBoolean(R.styleable.MaterialSearchBar_mt_navIconEnabled, false);
        roundedSearchBarEnabled = array.getBoolean(R.styleable.MaterialSearchBar_mt_roundedSearchBarEnabled, false);
        dividerColor = array.getColor(R.styleable.MaterialSearchBar_mt_dividerColor, ContextCompat.getColor(getContext(), R.color.searchBarDividerColor));
        searchBarColor = array.getColor(R.styleable.MaterialSearchBar_mt_searchBarColor, ContextCompat.getColor(getContext(), R.color.searchBarPrimaryColor));

        menuIconRes = array.getResourceId(R.styleable.MaterialSearchBar_mt_menuIconDrawable, R.drawable.ic_dots_vertical_black_48dp);
        searchIconRes = array.getResourceId(R.styleable.MaterialSearchBar_mt_searchIconDrawable, R.drawable.ic_magnify_black_48dp);
        speechIconRes = array.getResourceId(R.styleable.MaterialSearchBar_mt_speechIconDrawable, R.drawable.ic_microphone_black_48dp);
        arrowIconRes = array.getResourceId(R.styleable.MaterialSearchBar_mt_backIconDrawable, R.drawable.ic_arrow_left_black_48dp);
        clearIconRes = array.getResourceId(R.styleable.MaterialSearchBar_mt_clearIconDrawable, R.drawable.ic_close_black_48dp);
        navIconTint = array.getColor(R.styleable.MaterialSearchBar_mt_navIconTint, ContextCompat.getColor(getContext(), R.color.searchBarNavIconTintColor));
        menuIconTint = array.getColor(R.styleable.MaterialSearchBar_mt_menuIconTint, ContextCompat.getColor(getContext(), R.color.searchBarMenuIconTintColor));
        searchIconTint = array.getColor(R.styleable.MaterialSearchBar_mt_searchIconTint, ContextCompat.getColor(getContext(), R.color.searchBarSearchIconTintColor));
        arrowIconTint = array.getColor(R.styleable.MaterialSearchBar_mt_backIconTint, ContextCompat.getColor(getContext(), R.color.searchBarBackIconTintColor));
        clearIconTint = array.getColor(R.styleable.MaterialSearchBar_mt_clearIconTint, ContextCompat.getColor(getContext(), R.color.searchBarClearIconTintColor));
        navIconTintEnabled = array.getBoolean(R.styleable.MaterialSearchBar_mt_navIconUseTint, true);
        menuIconTintEnabled = array.getBoolean(R.styleable.MaterialSearchBar_mt_menuIconUseTint, true);
        searchIconTintEnabled = array.getBoolean(R.styleable.MaterialSearchBar_mt_searchIconUseTint, true);
        arrowIconTintEnabled = array.getBoolean(R.styleable.MaterialSearchBar_mt_backIconUseTint, true);
        clearIconTintEnabled = array.getBoolean(R.styleable.MaterialSearchBar_mt_clearIconUseTint, true);
        borderlessRippleEnabled = array.getBoolean(R.styleable.MaterialSearchBar_mt_borderlessRippleEnabled, false);

        //Text Related Attributes
        hintText = array.getString(R.styleable.MaterialSearchBar_mt_hint);
        placeholderText = array.getString(R.styleable.MaterialSearchBar_mt_placeholder);
        textColor = array.getColor(R.styleable.MaterialSearchBar_mt_textColor, ContextCompat.getColor(getContext(), R.color.searchBarTextColor));
        hintColor = array.getColor(R.styleable.MaterialSearchBar_mt_hintColor, ContextCompat.getColor(getContext(), R.color.searchBarHintColor));
        placeholderColor = array.getColor(R.styleable.MaterialSearchBar_mt_placeholderColor, ContextCompat.getColor(getContext(), R.color.searchBarPlaceholderColor));
        textCursorColor = array.getColor(R.styleable.MaterialSearchBar_mt_textCursorTint, ContextCompat.getColor(getContext(), R.color.searchBarCursorColor));
        highlightedTextColor = array.getColor(R.styleable.MaterialSearchBar_mt_highlightedTextColor, ContextCompat.getColor(getContext(), R.color.searchBarTextHighlightColor));

        destiny = getResources().getDisplayMetrics().density;
        if (adapter == null) {
            adapter = new DefaultSuggestionsAdapter(LayoutInflater.from(getContext()));
        }
        if (adapter instanceof DefaultSuggestionsAdapter)
            ((DefaultSuggestionsAdapter) adapter).setListener(this);
        adapter.setMaxSuggestionsCount(maxSuggestionCount);
        RecyclerView recyclerView = findViewById(R.id.mt_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        array.recycle();

        searchBarCardView = findViewById(R.id.mt_container);
        suggestionDivider = findViewById(R.id.mt_divider);
        menuIcon = findViewById(R.id.mt_menu);
        clearIcon = findViewById(R.id.mt_clear);
        searchIcon = findViewById(R.id.mt_search);
        arrowIcon = findViewById(R.id.mt_arrow);
        searchEdit = findViewById(R.id.mt_editText);
        placeHolder = findViewById(R.id.mt_placeholder);
        inputContainer = findViewById(R.id.inputContainer);
        navIcon = findViewById(R.id.mt_nav);
        findViewById(R.id.mt_clear).setOnClickListener(this);

        setOnClickListener(this);
        arrowIcon.setOnClickListener(this);
        searchIcon.setOnClickListener(this);
        searchEdit.setOnFocusChangeListener(this);
        searchEdit.setOnEditorActionListener(this);
        navIcon.setOnClickListener(this);

        postSetup();

    }

    public void inflateMenu(int menuResource) {
        inflateMenuRequest(menuResource, -1);
    }

    public void inflateMenu(int menuResource, int icon) {
        inflateMenuRequest(menuResource, icon);
    }

    private void inflateMenuRequest(int menuResource, int iconResId) {
        int menuResource1 = menuResource;
        if (menuResource1 > 0) {
            ImageView menuIcon = findViewById(R.id.mt_menu);
            if (iconResId != -1) {
                menuIconRes = iconResId;
                menuIcon.setImageResource(menuIconRes);
            }
            menuIcon.setVisibility(VISIBLE);
            menuIcon.setOnClickListener(this);
            popupMenu = new PopupMenu(getContext(), menuIcon);
            popupMenu.inflate(menuResource);
            popupMenu.setGravity(Gravity.RIGHT);
        }
    }

    public PopupMenu getMenu() {
        return this.popupMenu;
    }

    private void postSetup() {
        setupTextColors();
        setupRoundedSearchBarEnabled();
        setupSearchBarColor();
        setupIcons();
        setupSearchEditText();
    }

    private void setupRoundedSearchBarEnabled() {
        if (roundedSearchBarEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            searchBarCardView.setRadius(getResources().getDimension(R.dimen.corner_radius_rounded));
        } else {
            searchBarCardView.setRadius(getResources().getDimension(R.dimen.corner_radius_default));
        }
    }

    private void setupSearchBarColor() {
        searchBarCardView.setCardBackgroundColor(searchBarColor);
        setupDividerColor();
    }

    private void setupDividerColor() {
        suggestionDivider.setBackgroundColor(dividerColor);
    }

    private void setupTextColors() {
        searchEdit.setHintTextColor(hintColor);
        searchEdit.setTextColor(textColor);
        placeHolder.setTextColor(placeholderColor);
    }

    private void setupSearchEditText() {
        setupCursorColor();
        searchEdit.setHighlightColor(highlightedTextColor);

        if (hintText != null)
            searchEdit.setHint(hintText);
        if (placeholderText != null) {
            arrowIcon.setBackground(null);
            placeHolder.setText(placeholderText);
        }
    }

    private void setupCursorColor() {
        try {
            Field field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(searchEdit);

            field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int cursorDrawableRes = field.getInt(searchEdit);
            Drawable cursorDrawable = ContextCompat.getDrawable(getContext(), cursorDrawableRes).mutate();
            cursorDrawable.setColorFilter(textCursorColor, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {cursorDrawable, cursorDrawable};
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setupIcons() {
        navIconResId = R.drawable.ic_menu_animated;
        this.navIcon.setImageResource(navIconResId);
        setNavButtonEnabled(navButtonEnabled);

        if (popupMenu == null) {
            findViewById(R.id.mt_menu).setVisibility(GONE);
        }

        setSpeechMode(speechMode);

        this.arrowIcon.setImageResource(arrowIconRes);

        this.clearIcon.setImageResource(clearIconRes);
        setupNavIconTint();
        setupMenuIconTint();
        setupSearchIconTint();
        setupArrowIconTint();
        setupClearIconTint();
        setupIconRippleStyle();
    }

    private void setupNavIconTint() {
        if (navIconTintEnabled) {
            navIcon.setColorFilter(navIconTint, PorterDuff.Mode.SRC_IN);
        } else {
            navIcon.clearColorFilter();
        }
    }

    private void setupMenuIconTint() {
        if (menuIconTintEnabled) {
            menuIcon.setColorFilter(menuIconTint, PorterDuff.Mode.SRC_IN);
        } else {
            menuIcon.clearColorFilter();
        }
    }

    private void setupSearchIconTint() {
        if (searchIconTintEnabled) {
            searchIcon.setColorFilter(searchIconTint, PorterDuff.Mode.SRC_IN);
        } else {
            searchIcon.clearColorFilter();
        }
    }

    private void setupArrowIconTint() {
        if (arrowIconTintEnabled) {
            arrowIcon.setColorFilter(arrowIconTint, PorterDuff.Mode.SRC_IN);
        } else {
            arrowIcon.clearColorFilter();
        }
    }

    private void setupClearIconTint() {
        if (clearIconTintEnabled) {
            clearIcon.setColorFilter(clearIconTint, PorterDuff.Mode.SRC_IN);
        } else {
            clearIcon.clearColorFilter();
        }
    }

    private void setupIconRippleStyle() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            TypedValue rippleStyle = new TypedValue();
            if (borderlessRippleEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, rippleStyle, true);
            } else {
                getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, rippleStyle, true);
            }
            navIcon.setBackgroundResource(rippleStyle.resourceId);
            searchIcon.setBackgroundResource(rippleStyle.resourceId);
            menuIcon.setBackgroundResource(rippleStyle.resourceId);
            arrowIcon.setBackgroundResource(rippleStyle.resourceId);
            clearIcon.setBackgroundResource(rippleStyle.resourceId);
        } else {
            Log.w(TAG, "setupIconRippleStyle()  SDK Versions 必须大于 16 哈，YC !");
        }
    }

    public void setOnSearchActionListener(OnSearchActionListener onSearchActionListener) {
        this.onSearchActionListener = onSearchActionListener;
    }

    public void closeSearch() {
        animateNavIcon(false);
        searchOpened = false;
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_right);
        out.setAnimationListener(this);
        searchIcon.setVisibility(VISIBLE);
        inputContainer.startAnimation(out);
        searchIcon.startAnimation(in);

        if (placeholderText != null) {
            placeHolder.setVisibility(VISIBLE);
            placeHolder.startAnimation(in);
        }
        if (listenerExists())
            onSearchActionListener.onSearchStateChanged(false);
        if (suggestionsVisible) animateSuggestions(getListHeight(false), 0);
    }

    public void openSearch() {
        if (isSearchOpened()) {
            onSearchActionListener.onSearchStateChanged(true);
            searchEdit.requestFocus();
            return;
        }
        animateNavIcon(true);
        adapter.notifyDataSetChanged();
        searchOpened = true;
        Animation left_in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_left);
        Animation left_out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_left);
        left_in.setAnimationListener(this);
        placeHolder.setVisibility(GONE);
        inputContainer.setVisibility(VISIBLE);
        inputContainer.startAnimation(left_in);
        if (listenerExists()) {
            onSearchActionListener.onSearchStateChanged(true);
        }
        searchIcon.startAnimation(left_out);
    }

    private void animateNavIcon(boolean menuState) {
        if (menuState) {
            this.navIcon.setImageResource(R.drawable.ic_menu_animated);
        } else {
            this.navIcon.setImageResource(R.drawable.ic_back_animated);
        }
        Drawable mDrawable = navIcon.getDrawable();
        if (mDrawable instanceof Animatable) {
            ((Animatable) mDrawable).start();
        }
    }

    private void animateSuggestions(int from, int to) {
        suggestionsVisible = to > 0;
        final RecyclerView suggestionsList = findViewById(R.id.mt_recycler);
        final ViewGroup.LayoutParams lp = suggestionsList.getLayoutParams();
        if (to == 0 && lp.height == 0)
            return;
        findViewById(R.id.mt_divider).setVisibility(to > 0 ? View.VISIBLE : View.GONE);

        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(1200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.height = (int) animation.getAnimatedValue();
                suggestionsList.setLayoutParams(lp);
            }
        });
        if (adapter.getItemCount() > 0)
            animator.start();
    }

    public void showSuggestionsList() {
        animateSuggestions(0, getListHeight(false));
    }

    public void hideSuggestionsList() {
        animateSuggestions(getListHeight(false), 0);
    }

    public void clearSuggestions() {
        if (suggestionsVisible)
            animateSuggestions(getListHeight(false), 0);
        adapter.clearSuggestions();
    }

    public boolean isSuggestionsVisible() {
        return suggestionsVisible;
    }

    public boolean isSuggestionsEnabled() {
        return isSuggestionsEnabled;
    }

    public void setSuggestionsEnabled(boolean suggestionsEnabled) {
        isSuggestionsEnabled = suggestionsEnabled;
    }

    public void setMenuIcon(int menuIconResId) {
        this.menuIconRes = menuIconResId;
        this.menuIcon.setImageResource(this.menuIconRes);
    }

    public void setSearchIcon(int searchIconResId) {
        this.searchIconRes = searchIconResId;
        this.searchIcon.setImageResource(searchIconResId);
    }

    public void setArrowIcon(int arrowIconResId) {
        this.arrowIconRes = arrowIconResId;
        this.arrowIcon.setImageResource(arrowIconRes);
    }

    public void setClearIcon(int clearIconResId) {
        this.clearIconRes = clearIconResId;
        this.clearIcon.setImageResource(clearIconRes);
    }

    public void setNavIconTint(int navIconTint) {
        this.navIconTint = navIconTint;
        setupNavIconTint();
    }

    public void setMenuIconTint(int menuIconTint) {
        this.menuIconTint = menuIconTint;
        setupMenuIconTint();
    }

    public void setSearchIconTint(int searchIconTint) {
        this.searchIconTint = searchIconTint;
        setupSearchIconTint();
    }

    public void setArrowIconTint(int arrowIconTint) {
        this.arrowIconTint = arrowIconTint;
        setupArrowIconTint();
    }

    public void setClearIconTint(int clearIconTint) {
        this.clearIconTint = clearIconTint;
        setupClearIconTint();
    }

    public void setIconRippleStyle(boolean borderlessRippleEnabled) {
        this.borderlessRippleEnabled = borderlessRippleEnabled;
        setupIconRippleStyle();
    }

    public void setHint(CharSequence hintText) {
        this.hintText = hintText;
        searchEdit.setHint(hintText);
    }

    public CharSequence getPlaceHolderText() {
        return placeHolder.getText();
    }

    public void setSpeechMode(boolean speechMode) {
        this.speechMode = speechMode;
        if (speechMode) {
            searchIcon.setImageResource(speechIconRes);
            searchIcon.setClickable(true);
        } else {
            searchIcon.setImageResource(searchIconRes);
            searchIcon.setClickable(false);
        }
    }

    public boolean isSpeechModeEnabled() {
        return speechMode;
    }

    public boolean isSearchOpened() {
        return searchOpened;
    }

    public void setMaxSuggestionCount(int maxSuggestionsCount) {
        this.maxSuggestionCount = maxSuggestionsCount;
        adapter.setMaxSuggestionsCount(maxSuggestionsCount);
    }

    public void setCustomSuggestionAdapter(SuggestionsAdapter suggestionAdapter) {
        this.adapter = suggestionAdapter;
        RecyclerView recyclerView = findViewById(R.id.mt_recycler);
        recyclerView.setAdapter(adapter);
    }

    public List getLastSuggestions() {
        return adapter.getSuggestions();
    }

    public void setLastSuggestions(List suggestions) {
        adapter.setSuggestions(suggestions);
    }

    public void updateLastSuggestions(List suggestions) {
        int startHeight = getListHeight(false);
        if (suggestions.size() > 0) {
            List newSuggestions = new ArrayList<>(suggestions);
            adapter.setSuggestions(newSuggestions);
            animateSuggestions(startHeight, getListHeight(false));
        } else {
            animateSuggestions(startHeight, 0);
        }
    }

    public void setSuggestionsClickListener(OnItemViewClickListener listener) {
        if (adapter instanceof DefaultSuggestionsAdapter)
            ((DefaultSuggestionsAdapter) adapter).setListener(listener);
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        setupTextColors();
    }

    public void setTextHintColor(int hintColor) {
        this.hintColor = hintColor;
        setupTextColors();
    }

    public void setPlaceHolderColor(int placeholderColor) {
        this.placeholderColor = placeholderColor;
        setupTextColors();
    }

    public void setTextHighlightColor(int highlightedTextColor) {
        this.highlightedTextColor = highlightedTextColor;
        searchEdit.setHighlightColor(highlightedTextColor);
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setupDividerColor();
    }

    public void setNavButtonEnabled(boolean navButtonEnabled) {
        this.navButtonEnabled = navButtonEnabled;
        if (navButtonEnabled) {
            navIcon.setVisibility(VISIBLE);
            navIcon.setClickable(true);
            arrowIcon.setVisibility(GONE);
        } else {
            navIcon.setVisibility(GONE);
            navIcon.setClickable(false);
            arrowIcon.setVisibility(VISIBLE);
        }
        navIcon.requestLayout();
        placeHolder.requestLayout();
        arrowIcon.requestLayout();
    }

    public void setRoundedSearchBarEnabled(boolean roundedSearchBarEnabled) {
        this.roundedSearchBarEnabled = roundedSearchBarEnabled;
        setupRoundedSearchBarEnabled();
    }

    public void setCardViewElevation(int elevation) {
        CardView cardView = findViewById(R.id.mt_container);
        cardView.setCardElevation(elevation);
    }

    public String getText() {
        return searchEdit.getText().toString();
    }

    public void setText(String text) {
        searchEdit.setText(text);
    }

    public void addTextChangeListener(TextWatcher textWatcher) {
        searchEdit.addTextChangedListener(textWatcher);
    }

    public EditText getSearchEditText() {
        return searchEdit;
    }

    public TextView getPlaceHolderView() {
        return placeHolder;
    }

    public void setPlaceHolder(CharSequence placeholder) {
        this.placeholderText = placeholder;
        placeHolder.setText(placeholder);
    }

    private boolean listenerExists() {
        return onSearchActionListener != null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == getId()) {
            if (!searchOpened) {
                openSearch();
            }
        } else if (id == R.id.mt_arrow) {
            closeSearch();
        } else if (id == R.id.mt_search) {
            if (listenerExists())
                onSearchActionListener.onButtonClicked(BUTTON_SPEECH);
        } else if (id == R.id.mt_clear) {
            searchEdit.setText("");
        } else if (id == R.id.mt_menu) {
            popupMenu.show();
        } else if (id == R.id.mt_nav) {
            int button = searchOpened ? BUTTON_BACK : BUTTON_NAVIGATION;
            if (searchOpened) {
                closeSearch();
            }
            if (listenerExists()) {
                onSearchActionListener.onButtonClicked(button);
            }
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (!searchOpened) {
            inputContainer.setVisibility(GONE);
            searchEdit.setText("");
        } else {
            searchIcon.setVisibility(GONE);
            searchEdit.requestFocus();
            if (!suggestionsVisible && isSuggestionsEnabled)
                showSuggestionsList();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (hasFocus) {
            imm.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (listenerExists())
            onSearchActionListener.onSearchConfirmed(searchEdit.getText());
        if (suggestionsVisible)
            hideSuggestionsList();
        if (adapter instanceof DefaultSuggestionsAdapter)
            adapter.addSuggestion(searchEdit.getText().toString());
        return true;
    }

    private int getListHeight(boolean isSubtraction) {
        if (!isSubtraction)
            return (int) (adapter.getListHeight() * destiny);
        return (int) (((adapter.getItemCount() - 1) * adapter.getSingleViewHeight()) * destiny);
    }

    @Override
    public void OnItemClickListener(int position, View v) {
        if (v.getTag() instanceof String) {
            searchEdit.setText((String) v.getTag());
        }
    }

    @Override
    public void OnItemDeleteListener(int position, View v) {
        if (v.getTag() instanceof String) {
            animateSuggestions(getListHeight(false), getListHeight(true));
            adapter.deleteSuggestion(position, v.getTag());
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SearchSavedState savedState = new SearchSavedState(super.onSaveInstanceState());
        savedState.isSearchBarVisible = searchOpened ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.suggestionsVisible = suggestionsVisible ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.speechMode = speechMode ? VIEW_VISIBLE : VIEW_INVISIBLE;
        savedState.navIconResId = navIconResId;
        savedState.searchIconRes = searchIconRes;
        savedState.suggestions = getLastSuggestions();
        savedState.maxSuggestions = maxSuggestionCount;
        if (hintText != null) savedState.hint = hintText.toString();
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SearchSavedState savedState = (SearchSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        searchOpened = savedState.isSearchBarVisible == VIEW_VISIBLE;
        suggestionsVisible = savedState.suggestionsVisible == VIEW_VISIBLE;
        setLastSuggestions(savedState.suggestions);
        if (suggestionsVisible)
            animateSuggestions(0, getListHeight(false));
        if (searchOpened) {
            inputContainer.setVisibility(VISIBLE);
            placeHolder.setVisibility(GONE);
            searchIcon.setVisibility(GONE);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && searchOpened) {
            animateSuggestions(getListHeight(false), 0);
            closeSearch();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
