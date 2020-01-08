package com.bingor.utillib.log;

import android.Manifest;
import android.support.annotation.RequiresPermission;

import java.io.File;

/**
 * Created by Bingor on 2020/1/8.
 */
public interface LocalLog {

    void log(String content, long time);

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void open();

    void close();
}
