#### 目录介绍
- 01.透明状态栏和沉浸式
- 02.theme主题对状态栏影响
- 03.如何给状态栏着色
- 04.注意4.4状态栏着色
- 05.如何设置状态栏透明
- 06.状态栏不占位的问题
- 07.修改状态栏文字颜色




### 01.透明状态栏和沉浸式
- 什么是透明状态栏
    - 透明状态栏是指将状态栏设置为透明或者半透明。如果这时候将状态栏设置为透明，那么状态栏和手机页面看以来是一个整体，增大了屏幕的利用空间。
- 什么是沉浸式
    - 沉浸式是指，将页面的布局“沉浸”在状态栏下面，如果设置了沉浸式状态栏不透明，毫无疑问布局将会被状态栏遮挡。
    


### 02.theme主题对状态栏影响
- 对于现在开发项目我们一般使用的是 Theme.AppCompat.xxx兼容包里面的主题
    ```
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorPrimary</item>
        <item name="android:windowTranslucentStatus">true</item>
        <item name="android:windowTranslucentNavigation">true</item>
        <item name="android:statusBarColor">@android:color/transparent</item>
    </style>
    ```
- 那么这些属性是什么意思呢？
    - 分别代表toolbar的颜色、状态栏的颜色、输入法和radiobuttion等的颜色，但是需要注意的是：在5.0以下的版本，状态栏的颜色通过主题设置是没有用的，用以上的主题属性来设置状态栏颜色，在5.x以下是黑色，自己可以试试。
    - 后三个属性是来设置透明状态栏的，android:windowTranslucentStatus、android:windowTranslucentNavigation是4.4以后才有的属性，android:statusBarColor是5.0以后才有的属性
      

### 03.如何给状态栏着色
- 给状态栏着色有两个方式
    - 第一种方式通过样式设置状态栏颜色
    - 第二种方式通过代码设置状态栏颜色
- 通过代码设置状态栏颜色
    - 5.x以上的版本
    ```
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    //设置状态栏颜色
    window.setStatusBarColor(setStatusBarColor());
    ```
- 通过样式设置状态栏颜色
    - 5.x以上的版本
    ```
    <style name="AppThemeTranslucent" parent="AppTheme">
        <item name="android:windowTranslucentStatus">false</item>
        <item name="android:windowTranslucentNavigation">true</item>
        <item name="android:statusBarColor">@color/colorAccent</item>
    </style>
    ```





### 04.注意4.4状态栏着色
- 4.4版本唯一一个和5.x版本不一样的地方就是4.4没有改变状态栏颜色的api，只有改变透明度的api，那怎么解决这个问题呢？
    - 都知道，整个app展现在我们眼前的最外层的视图为DecorView，实质上是一个FrameLayout，状态栏是系统的窗口，覆盖在DecorView之上的，DecorView里面有我们的content，既然是这样，我们可以在DecorView里面加一个布局，覆盖在content之上，通过设置状态栏透明，改变这个View的颜色，这样就造成了改变状态栏颜色的假象。
- 大概的代码如下所示
    ```
    static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        //设置Window为全透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        //获取父布局
        View mContentChild = mContentView.getChildAt(0);
        //获取状态栏高度
        int statusBarHeight = getStatusBarHeight(activity);
        //如果已经存在假状态栏则移除，防止重复添加
        removeFakeStatusBarViewIfExist(activity);
        //添加一个View来作为状态栏的填充
        addFakeStatusBarView(activity, statusColor, statusBarHeight);
        //设置子控件到状态栏的间距
        addMarginTopToContentChild(mContentChild, statusBarHeight);
        //不预留系统栏位置
        if (mContentChild != null) {
            mContentChild.setFitsSystemWindows(false);
        }
    }
    /**
     * 添加一个假的状态栏
     * @param activity                      activity
     * @param statusBarColor                状态栏颜色
     * @param statusBarHeight               状态栏高度，这个高度可以获取
     * @return
     */
    private static View addFakeStatusBarView(Activity activity, int statusBarColor, int statusBarHeight) {
        Window window = activity.getWindow();
        ViewGroup mDecorView = (ViewGroup) window.getDecorView();
    
        //创建一个假的状态栏，添加到DecorView上
        StatusBarView mStatusBarView = new StatusBarView(activity);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        layoutParams.gravity = Gravity.TOP;
        mStatusBarView.setLayoutParams(layoutParams);
        mStatusBarView.setBackgroundColor(statusBarColor);
        mStatusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
        //添加view
        mDecorView.addView(mStatusBarView);
        return mStatusBarView;
    }
    ```



### 05.如何设置状态栏透明
- 有两种方式设置状态栏透明
    - 第一种是通过改变主题的方式
    - 第二种是通过代码方式的方式
- 通过改变主题的方式设置状态栏透明
    - 贴出value-v19、v21的主题参数
    ```
    //v19
    <style name="AppTheme1" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorPrimary</item>
        <item name="android:windowTranslucentStatus">true</item>
        <item name="android:windowTranslucentNavigation">true</item>
    </style>
    
    //v21
    <style name="AppTheme1" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorPrimary</item>
        <item name="android:windowTranslucentStatus">true</item>
        <item name="android:windowTranslucentNavigation">true</item>
        <item name="android:statusBarColor">@android:color/transparent</item>
    </style>
    ```
- 通过代码方式设置状态栏透明
    - 下面这个是比较简易型的代码
    ```
    // 设置状态栏全透明
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //设置根布局顶上去
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }
    ```
- 注意一些问题
    - windowTranslucentStatus属性改变状态栏成半透明，并且布局网上顶，充满全屏
    - windowTranslucentNavigation属性改变底部导航栏颜色为半透明
    - statusBarColor 5.x才有设置状态栏颜色，4.x不是不能设置颜色的。



### 06.状态栏不占位的问题
- 思考一下为什么setContentView的布局会顶上去？
    - 设置了状态栏透明，那么内容 view 就占据状态栏的位置了。
- 先看一下activity的层级布局
    - statusBar状态栏是系统窗口，DecorView是视图的顶级窗口，继承自FrameLayout，他的子View一个是actionbar，和一个id为content的FrameLyout，这个content就是setContentView的那个布局
- 为什么设置了透明主题属性就会把contentView顶上去呢？
    - 如果设置了透明状态栏，decorView和content之间的margin为0，这时候content布局的大小就是整个屏幕的大小；如果没有设置，则margin为状态栏的高度加上actionbar的高度(如果有）。
- 有什么方法可以设置顶上去
    - 布局添加：android:fitsSystemWindows="true|false"，有没有觉得很熟悉。
    - 代码设置：window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
- fitsSystemWindows 见名知意，意思是表示系统还是要占据状态栏的位置了，这样我们的状态栏和 ui 才能没有冲突



### 07.修改状态栏文字颜色
- 这个是在 6.0 之后才提供的功能，说是可以修改颜色，其实也只是能把颜色改为暗颜色和亮颜色，说白了就是白色和黑色。




























