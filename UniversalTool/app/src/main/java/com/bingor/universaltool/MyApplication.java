package com.bingor.universaltool;

import android.app.Application;

import com.bingor.utillib.log.Log;

/**
 * Created by Bingor on 2019/4/11.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.setTag("HXB");
    }
}
