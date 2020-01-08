package com.bingor.utillib.log;

import android.os.Environment;
import android.text.TextUtils;

import com.bingor.utillib.file.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bingor on 2020/1/8.
 */
public class LocalLogger implements LocalLog {
    private static LocalLogger instance;

    private File file;
    private FileWriter fileWriter;
    private String path;

    public static LocalLogger getInstance() {
        if (instance == null) {
            instance = new LocalLogger();
        }
        return instance;
    }

    public LocalLogger setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public void log(String content, long time) {
        synchronized (fileWriter) {
            try {
                fileWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)) + "\n");
                fileWriter.write(content + "\n\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void open() {
        file = FileUtil.getFile(FileUtil.FILE_TYPE_TXT, Environment.getExternalStorageDirectory().getAbsolutePath() + (TextUtils.isEmpty(path) ? "" : "/" + path));
        try {
            fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fileWriter = null;
            }
        }
    }
}
