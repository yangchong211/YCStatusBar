#### 目录介绍
- 01.状态栏的发展过程介绍
- 02.android6.0状态栏内容不见
- 03.setSystemUiVisibility
- 04.fitsSystemWindows深入分析




### 01.状态栏的发展过程
- android的状态栏大致经历了以下几个阶段：
    - 在android4.4以下就不要想着对状态栏做什么文章了，现在app的适配一般也是在android4.4以上了。
    - 在android4.4—android5.0可以实现状态栏的变色，但是效果还不是很好，主要实现方式是通过FLAG_TRANSLUCENT_STATUS这个属性设置状态栏为透明并且为全屏模式，然后通过添加一个与StatusBar 一样大小的View，将View 设置为我们想要的颜色，从而来实现状态栏变色。
    - 在android5.0—android6.0系统才真正的支持状态栏变色，系统加入了一个重要的属性和方法 android:statusBarColor （对应方法为 setStatusBarColor），通过这个属性可以直接设置状态栏的颜色。
    - 在android6.0上主要就是添加了一个功能可以修改状态栏上内容和图标的颜色（黑色和白色）



### 02.android6.0状态栏内容不见
- Android 系统状态栏的字色和图标颜色为白色，当我的主题色或者图片接近白色或者为浅色的时候，状态栏上的内容就看不清了。 
- 这个问题在Android 6.0的时候得到了解决。Android 6.0 新添加了一个属性SYSTEM_UI_FLAG_LIGHT_STATUS_BAR，这个属性是干什么用的呢？
- 注意问题：
    - 为setSystemUiVisibility(int)方法添加的Flag,请求status bar 绘制模式，它可以兼容亮色背景的status bar 。
    - 要在设置了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag ,同时清除了FLAG_TRANSLUCENT_STATUS flag 才会生效。
- 代码如下所示
    ```
    //先清除
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //相当于在布局中设置android:fitsSystemWindows="true"，让contentView顶上去
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
    ```



### 03.setSystemUiVisibility
- 对于状态栏的控制是非常重要的，这个方法主要是用来设置系统ui的可见性以及和用户布局的位置关系。
    - 使用方式如下：getWindow().getDecorView().setSystemUiVisibility(flag);
- 下面着重介绍一下flag：
    - SYSTEM_UI_FLAG_FULLSCREEN(4.1+)：隐藏状态栏，手指在屏幕顶部往下拖动，状态栏会再次出现且不会消失，另外activity界面会重新调整大小，直观感觉就是activity高度有个变小的过程。
    - SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN(4.1+):状态栏一直存在并且不会挤压activity高度，状态栏会覆盖在activity之上
    - SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN(4.1+):配合SYSTEM_UI_FLAG_FULLSCREEN一起使用，效果使得状态栏出现的时候不会挤压activity高度，状态栏会覆盖在activity之上
    - SYSTEM_UI_FLAG_HIDE_NAVIGATION(4.0+):会使得虚拟导航栏隐藏，但是由于NavigationBar是非常重要的，因此只要有用户交互（例如点击一个 button），系统就会清除这个flag使NavigationBar就会再次出现，同时activity界面会被挤压 。
    - SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION(4.1+)：效果使得导航栏不会挤压activity高度，导航栏会覆盖在activity之上。
    - SYSTEM_UI_FLAG_IMMERSIVE配合SYSTEM_UI_FLAG_HIDE_NAVIGATION一起使用，还记得之前使用SYSTEM_UI_FLAG_HIDE_NAVIGATION之后只要有用户交互，系统就会清除这个flag使NavigationBar就会再次出现，和SYSTEM_UI_FLAG_IMMERSIVE一起使用之后就会使SYSTEM_UI_FLAG_HIDE_NAVIGATION必须手指在屏幕底部往上拖动NavigationBar才会出现。
    - SYSTEM_UI_FLAG_IMMERSIVE_STICKY配合View.SYSTEM_UI_FLAG_FULLSCREEN和View.SYSTEM_UI_FLAG_HIDE_NAVIGATION一起使用，会使状态栏和导航栏以透明的形式出现，并且一段时间后自动消失。


### 04.fitsSystemWindows深入分析
- 一些事情需要注意：
    - fitsSystemWindows 需要被设置给根View——这个属性可以被设置给任意View，但是只有根View（content部分的根）外面才是SystemWindow，所以只有设置给根View才有用。
    - 其它padding将通通被覆盖。需要注意，如果你对一个View设置了android:fitsSystemWindows=“true”，那么你对该View设置的其他padding将通通无效。
- 看一句有疑惑的话
    - 使用系统提供的一些控件时fitsSystemWindows 这个属性有时候并不仅仅是设置给最外层的根布局，他的子view也会添加这个属性，不是说这个属性只对根view才有效吗？
- 如何理解这句话
    - 这句话本身没问题，但是他的的前提是没有对view进行重写，在KITKAT及以下的版本，你的自定义View能够通过覆盖fitsSystemWindows() : boolean函数，来增加自定义行为。如果返回true，意味着你已经占据了整个Insets，如果返回false，意味着给其他的View依旧留有机会。所以当我们返回false时子view的fitsSystemWindows 就有效。
- 举个例子加深理解
    - CoordinatorLayout嵌套AppBarLayout嵌套CollapsingToolbarLayout嵌套ImageView，这是一个常见的效果，具体是什么我就不多介绍了，这里的每一个view都需要设置fitsSystemWindows为true，这样事件才能传到 ImageView中














