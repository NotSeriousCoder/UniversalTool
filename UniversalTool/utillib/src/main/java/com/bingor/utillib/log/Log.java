package com.bingor.utillib.log;


import android.text.TextUtils;

/**
 * Created by HXB on 2017-05-08.
 */

public class Log {
    public static String TAG = "TEST";
    public static boolean isDebug = true;

    public static void v(String tag, String content) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(content)) {
            android.util.Log.v(tag, content);
        }
    }

    public static void d(String tag, String content) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(content)) {
            android.util.Log.d(tag, content);
        }
    }

    public static void i(String tag, String content) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(content)) {
            android.util.Log.i(tag, content);
        }
    }

    public static void w(String tag, String content) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(content)) {
            android.util.Log.w(tag, content);
        }
    }

    public static void e(String tag, String content) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(content)) {
            android.util.Log.e(tag, content);
        }
    }

    public static void wtf(String tag, String content) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(content)) {
            android.util.Log.wtf(tag, content);
        }
    }

    public static void v(String content) {
        if (isDebug && !TextUtils.isEmpty(content)) {
            android.util.Log.v(TAG, content);
        }
    }

    public static void d(String content) {
        if (isDebug && !TextUtils.isEmpty(content)) {
            android.util.Log.d(TAG, content);
        }
    }

    public static void i(String content) {
        if (isDebug && !TextUtils.isEmpty(content)) {
            android.util.Log.i(TAG, content);
        }
    }

    public static void w(String content) {
        if (isDebug && !TextUtils.isEmpty(content)) {
            android.util.Log.w(TAG, content);
        }
    }

    public static void e(String content) {
        if (isDebug && !TextUtils.isEmpty(content)) {
            android.util.Log.e(TAG, content);
        }
    }

    public static void wtf(String content) {
        if (isDebug && !TextUtils.isEmpty(content)) {
            android.util.Log.wtf(TAG, content);
        }
    }

}
