package cn.ycbjie.ycstatusbarlib;


import android.content.Context;
import android.content.res.Resources;

/**
 * Created by PC on 2017/3/27
 * 获取状态栏的高度
 */

public class StatusBarHeightUtils {

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

}
