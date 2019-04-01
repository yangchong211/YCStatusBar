## 目录介绍
- 1.关于如何集成
- 2.关于如何使用
- 3.关于鸣谢
- 4.关于版本更新说明
- 5.出现的bug及解决方案
- 6.关于其他介绍

### 0.说明
- **状态栏工具类，应该可以满足绝大多数的使用场景。具体可以参考代码案例，欢迎star！！**
- 1.[技术博客汇总](https://www.jianshu.com/p/614cb839182c)
- 2.[开源项目汇总](https://blog.csdn.net/m0_37700275/article/details/80863574)
- 3.[生活博客汇总](https://blog.csdn.net/m0_37700275/article/details/79832978)
- 4.[喜马拉雅音频汇总](https://www.jianshu.com/p/f665de16d1eb)
- 5.[其他汇总](https://www.jianshu.com/p/53017c3fc75d)



### 1.关于如何集成
- 在gradle中添加：compile 'cn.yc:YCStatusBarLib:1.4.0'
- 项目地址：https://github.com/yangchong211/YCStatusBar
- ![image](https://github.com/yangchong211/YCStatusBar/blob/master/image/image1.jpg)
- ![image](https://github.com/yangchong211/YCStatusBar/blob/master/image/statusBar.gif)


### 2.关于如何使用
- **2.1.1 DrawerLayout设置状态栏**
```
//为DrawerLayout 布局设置状态栏颜色,纯色
DlStatusBar.setColorNoTranslucentForDrawerLayout(this, drawerLayout,getResources().getColor(R.color.colorTheme));
//为DrawerLayout 布局设置状态栏变色，也就是加上透明度
DlStatusBar.setColorForDrawerLayout(this, drawerLayout,getResources().getColor(R.color.colorTheme), 0);
```

- **2.1.2 设置状态栏颜色**
```
StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
```

- **2.1.3 设置状态栏和toolbar颜色**
- 注意，如果是设置白色的话，则需要单独设置状态栏字体的颜色，否则看不见
- 如果要设置状态栏为白色：则直接可以使用2.7中的方法
```
StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
```

- **2.1.4 设置状态栏透明**
```
StateAppBar.translucentStatusBar(this, true);
```

- **2.1.5 设置状态栏coordinatorLayout颜色**
```
StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
```

- **2.1.6 设置状态栏coordinatorLayout颜色透明**
```
StateAppBar.setStatusBarColorForCollapsingToolbar(this, mAppBarLayout, collapsingToolbarLayout, toolbar, ContextCompat.getColor(this, R.color.colorPrimary));
```

- **2.1.7 设置状态栏颜色为白色**
```
StateAppBar.setStatusBarLightMode(this, Color.WHITE);
```

- **2.1.8 设置状态栏和toolbar颜色为白色**
```
StateAppBar.setStatusBarLightMode(this, Color.WHITE);
```

- **2.1.9 设置状态栏和coordinatorLayout为白色**
```
StateAppBar.setStatusBarLightForCollapsingToolbar(this, mAppBarLayout, collapsingToolbarLayout, toolbar, Color.WHITE);
```

- **2.2.0 单Activity多Fragment动态修改状态栏颜色**
- 如果是单Activity多Fragment，由Fragment控制状态栏颜色的应用，有两种方案：
- 1.由Activity控制状态栏背景颜色和字体颜色，提供方法供Fragment调用即可。
- 2.首先设置Activity侵入状态栏，并设置状态栏为透明色，相当于隐藏Activity的状态栏，
然后在BaseFragment中封装状态栏，由Fragment控制自己的颜色即可；
但是状态栏字体颜色还是需要通过Activity控制。
- **第一种方法:直接在activity中操作**
```
//例如，ViewPager+TabLayout+Fragment中，很常见
@Override
public void onPageSelected(int position) {
    switch (position){
        case 0:
            //设置状态栏为黑色
            StateAppBar.setStatusBarColor(StatusBarFragmentActivity.this,
                    ContextCompat.getColor(StatusBarFragmentActivity.this,
                            R.color.colorTheme));
            break;
        case 1:
            //设置状态栏为红色
            StateAppBar.setStatusBarColor(StatusBarFragmentActivity.this,
                    ContextCompat.getColor(StatusBarFragmentActivity.this,
                            R.color.colorAccent));
            break;
        case 2:
            //设置状态栏为蓝色
            StateAppBar.setStatusBarColor(StatusBarFragmentActivity.this,
                    ContextCompat.getColor(StatusBarFragmentActivity.this,
                            R.color.colorPrimary));
            break;
        case 3:
            //设置状态栏为透明，相当于隐藏状态栏，也称之为沉浸式状态栏
            StateAppBar.translucentStatusBar(StatusBarFragmentActivity.this,
                    true);
            break;
        case 4:
            //设置状态栏为白色
            StateAppBar.setStatusBarColor(StatusBarFragmentActivity.this,
                    ContextCompat.getColor(StatusBarFragmentActivity.this,
                            R.color.white));
            //状态栏亮色模式，设置状态栏黑色文字、图标
            StatusBarUtils.StatusBarLightMode(StatusBarFragmentActivity.this);
            break;
    }
}
```

- **第一种方法:activity和Fragment配合使用**
    - 首先设置Activity侵入状态栏，并设置状态栏为透明色，相当于隐藏Activity的状态栏，
      然后在BaseFragment中封装状态栏，由Fragment控制自己的颜色即可；
      但是状态栏字体颜色还是需要通过Activity控制。

```
//在activity中
StateAppBar.translucentStatusBar(this,true);

//在fragment中
//判断是否展示—与ViewPager连用，进行左右切换
@Override
public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    if (isVisibleToUser){
        if(activity!=null){
            StateAppBar.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.white));
            //状态栏亮色模式，设置状态栏黑色文字、图标
            //注意：如果是设置白色状态栏，则需要添加下面这句话。如果是设置其他的颜色，则可以不添加，状态栏大都默认是白色字体和图标
            StatusBarUtils.StatusBarLightMode(activity);
        }
    }//展示
}
```



- **2.2.1 可以自由设置状态栏中的字体，图标颜色**
    - 支持类型，1:MIUUI 2:Flyme 3:android6.0
    - 目前只是支持改变状态栏中字体图标为黑色，思考是否有可能改变其他颜色呢？
```
//状态栏亮色模式，设置状态栏黑色文字、图标
StatusBarUtils.StatusBarLightMode(StatusBarFragmentActivity.this);
```



### 3.关于鸣谢
- 关于状态栏，是经过阅读一些项目慢慢总结而来的。
- 非常方便使用，具体的用法都已经整理成demo，欢迎直接看代码，如果可以麻烦star！
- 感谢开源前辈们的无私奉献……


### 4.关于版本更新说明
- v0.0 更新于2016年3月9日
- v1.0 更新于2017年9月8日
- v1.1 更新于2017年12月5日
- v1.3 更新于2018年3月16日
- v1.3.1 更新于2018年9月1日
    - 添加了单Activity多Fragment动态修改状态栏颜色功能


### 5.出现的bug及解决方案，欢迎提出更多bug
- 1.如果是在设置fragment中，有的是白色【或者其他色】，有的是透明色【就相当于隐藏了状态栏】，则还是会出现设置透明色无效
    - 解决办法：
    - 这其实更像是一个效果，而不是问题，透明色时应该显示了下面的Fragment的颜色，所以看起来无效。
    - 实际上每一个Activity的Window都有背景色，如果你把Activity的Window设置为透明色，也会出现这个效果，
    - 所以你应该给Fragment的RootView设置一个背景色，才能更像一个Activity，再设置透明色就没有问题了
- 2.横屏状态栏问题
    - 比如视频播放器，横屏是需要隐藏状态栏，也可以成为透明状态栏，部分手机失效
    - 建议可以直接移除状态栏，可以参考我的视频播放器：https://github.com/yangchong211/YCVideoPlayer
- 3.设置状态栏颜色无效
    - 状态栏字体颜色只适合6.0以上或者小米和魅族手机
- 4.多个EditText会遮挡的问题
    - 最外层布局不要使用LinearLayout ，而需要使用scrollView
- 5.弹出FragmentDialog的时候设置背景色变暗，状态栏的颜色变白
    - 如果是全屏的dialogFragment是可以修改状态栏字体颜色，其他情况都不可以修改



### 6.关于其他介绍
#### 关于我的博客
- 我的个人站点：www.yczbj.org，www.ycbjie.cn
- github：https://github.com/yangchong211
- 知乎：https://www.zhihu.com/people/yang-chong-69-24/pins/posts
- 简书：http://www.jianshu.com/u/b7b2c6ed9284
- csdn：http://my.csdn.net/m0_37700275
- 喜马拉雅听书：http://www.ximalaya.com/zhubo/71989305/
- 泡在网上的日子：http://www.jcodecraeer.com/member/content_list.php?channelid=1
- 邮箱：yangchong211@163.com
- 阿里云博客：https://yq.aliyun.com/users/article?spm=5176.100- 239.headeruserinfo.3.dT4bcV
