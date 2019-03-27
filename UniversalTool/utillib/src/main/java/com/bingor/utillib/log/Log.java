package com.bingor.utillib.log;


import android.text.TextUtils;

/**
 * Created by HXB on 2017-05-08.
 */

public class Log {
    public static String TAG = "TEST";
    public static boolean isDebug = true;

    public static void v(String tag, String... content) {
        if (isDebug && !TextUtils.isEmpty(tag) && content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            android.util.Log.v(tag, contentStr);
        }
    }

    public static void d(String tag, String... content) {
        if (isDebug && !TextUtils.isEmpty(tag) && content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            android.util.Log.d(tag, contentStr);
        }
    }

    public static void i(String tag, String... content) {
        if (isDebug && !TextUtils.isEmpty(tag) && content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            android.util.Log.i(tag, contentStr);
        }
    }

    public static void w(String tag, String... content) {
        if (isDebug && !TextUtils.isEmpty(tag) && content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            android.util.Log.w(tag, contentStr);
        }
    }

    public static void e(String tag, String... content) {
        if (isDebug && !TextUtils.isEmpty(tag) && content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            android.util.Log.e(tag, contentStr);
        }
    }

    public static void wtf(String tag, String... content) {
        if (isDebug && !TextUtils.isEmpty(tag) && content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            android.util.Log.wtf(tag, contentStr);
        }
    }

    public static void v(String... content) {
        Log.v(TAG, content);
    }

    public static void d(String... content) {
        Log.d(TAG, content);
    }

    public static void i(String... content) {
        Log.i(TAG, content);
    }

    public static void w(String... content) {
        Log.w(TAG, content);
    }

    public static void e(String... content) {
        Log.e(TAG, content);
    }

    public static void wtf(String... content) {
        Log.wtf(TAG, content);
    }

}
