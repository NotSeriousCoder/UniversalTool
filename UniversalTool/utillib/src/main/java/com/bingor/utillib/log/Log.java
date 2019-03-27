package com.bingor.utillib.log;


import android.text.TextUtils;

/**
 * Created by HXB on 2017-05-08.
 */

public class Log {
    private static String TAG = "TEST";
    public static boolean isDebug = true;

    public static Log setTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            try {
                throw new Exception("tag can not be empty");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.TAG = tag;
        }
        return new Log();
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

    public static void v(String... content) {
        if (content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            Log.v(contentStr);
        }
    }

    public static void d(String... content) {
        if (content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            Log.d(contentStr);
        }
    }

    public static void i(String... content) {
        if (content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            Log.i(contentStr);
        }
    }

    public static void w(String... content) {
        if (content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            Log.w(contentStr);
        }
    }

    public static void e(String... content) {
        if (content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            Log.e(contentStr);
        }
    }

    public static void wtf(String... content) {
        if (content != null && content.length > 0) {
            String contentStr = "";
            for (String con : content) {
                if (!TextUtils.isEmpty(con)) {
                    contentStr += con + "  ";
                }
            }
            Log.wtf(contentStr);
        }
    }

}
