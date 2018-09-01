package cn.ycbjie.ycstatusbarlib;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/3/27
 * 描    述：获取状态栏的高度
 * 修订历史：
 * ================================================
 */
public class StatusBarUtils {

    private static final String TAG = "StatusBarHeightUtils";
    /**
     * 获取状态栏高度
     * @param context       context
     * @return              状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 判断状态栏是否存在
     * @param activity
     * @return
     */
    public static boolean isStatusBarVisible(Activity activity){
        if ((activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0){
            Log.d(TAG,"status bar is visible");
            return true;
        } else {
            Log.d(TAG,"status bar is not visible");
            return false;
        }
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity      activity
     * @return              1:MIUUI 2:Flyme 3:android6.0
     * */
    public static int StatusBarLightMode(Activity activity){
        int result=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(YCAppBar.setStatusBarLightMode(activity, true)){
                result=1;
            }else if(YCAppBar.FlymeSetStatusBarLightMode(activity, true)){
                result=2;
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().
                        setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result=3;
            }
        } return result;
    }


    /**
     * 已知系统类型时，设置状态栏黑色文字、图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity      activity
     * @param type          1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode(Activity activity,int type){
        if(type==1){
            YCAppBar.setStatusBarLightMode(activity, true);
        }else if(type==2){
            YCAppBar.FlymeSetStatusBarLightMode(activity, true);
        }else if(type==3){
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    /**
     * 状态栏暗色模式，清除MIUI、flyme或6.0以上版本状态栏黑色文字、图标
     */
    public static void StatusBarDarkMode(Activity activity,int type){
        if(type==1){
            YCAppBar.setStatusBarLightMode(activity, false);
        }else if(type==2){
            YCAppBar.FlymeSetStatusBarLightMode(activity, false);
        }else if(type==3){
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }


}
