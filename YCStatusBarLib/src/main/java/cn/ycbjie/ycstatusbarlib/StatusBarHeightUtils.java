package cn.ycbjie.ycstatusbarlib;


import android.content.Context;
import android.content.res.Resources;

/**
 * ================================================
 * 作    者：杨充
 * 版    本：1.0
 * 创建日期：2017/3/27
 * 描    述：获取状态栏的高度
 * 修订历史：
 * ================================================
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
