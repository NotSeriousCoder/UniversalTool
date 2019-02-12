package com.bingor.utillib.hardware;

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
}
