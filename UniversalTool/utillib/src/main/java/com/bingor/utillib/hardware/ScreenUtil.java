package com.bingor.utillib.hardware;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by Bingor on 2019/2/12.
 */
public class ScreenUtil {

    public static Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        return size;
    }

    //Configuration.ORIENTATION_PORTRAIT || Configuration.ORIENTATION_LANDSCAPE || Configuration.ORIENTATION_UNDEFINE
    public static int getScreenOrientation(Context context) {
        return context.getResources().getConfiguration().orientation;
    }

    //返回4个方向 0 90 180 270
    public static int getScreenRotation(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getRotation();
    }
}
