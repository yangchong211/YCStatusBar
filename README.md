## 目录介绍
- 1.关于如何集成
- 2.关于如何使用
- 3.关于鸣谢
- 4.关于版本更新说明
- 5.关于其他介绍

### 0.说明
- **状态栏工具类，应该可以满足绝大多数的使用场景。具体可以参考代码案例，欢迎star！！**

### 1.关于如何集成
- 在gradle中添加：compile 'cn.yc:YCStatusBarLib:1.3'


### 2.关于如何使用
- **2.1 DrawerLayout设置状态栏**
```
//为DrawerLayout 布局设置状态栏颜色,纯色
StatusBarUtils.setColorNoTranslucentForDrawerLayout(this, drawerLayout,getResources().getColor(R.color.colorTheme));
//为DrawerLayout 布局设置状态栏变色，也就是加上透明度
StatusBarUtils.setColorForDrawerLayout(this, drawerLayout,getResources().getColor(R.color.colorTheme), 0);
```

- **2.2 设置状态栏颜色**
```
YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
```

- **2.3 设置状态栏和toolbar颜色**
```
YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorAccent));
```

- **2.4 设置状态栏透明**
```
YCAppBar.translucentStatusBar(this, true);
```

- **2.5 设置状态栏coordinatorLayout颜色**
```
YCAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
```

- **2.6 设置状态栏coordinatorLayout颜色透明**
```
YCAppBar.setStatusBarColorForCollapsingToolbar(this, mAppBarLayout, collapsingToolbarLayout, toolbar, ContextCompat.getColor(this, R.color.colorPrimary));
```

- **2.7 设置状态栏颜色为白色**
```
YCAppBar.setStatusBarLightMode(this, Color.WHITE);
```

- **2.8 设置状态栏和toolbar颜色为白色**
```
YCAppBar.setStatusBarLightMode(this, Color.WHITE);
```

- **2.9 设置状态栏和coordinatorLayout为白色**
```
YCAppBar.setStatusBarLightForCollapsingToolbar(this, mAppBarLayout, collapsingToolbarLayout, toolbar, Color.WHITE);
```

### 3.关于鸣谢
- 关于状态栏，采用的是拿来主义，是经过阅读一些项目慢慢总结而来的。
- 非常使用，具体的用法都已经整理成demo，欢迎直接看代码，如果可以麻烦star！
- 感谢开源前辈们的无私奉献……


### 4.关于版本更新说明
- v1.0 更新于2017年9月8日
- v1.1 更新于2017年12月5日
- v1.3 更新于2018年3月16日


### 5.关于其他介绍
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
