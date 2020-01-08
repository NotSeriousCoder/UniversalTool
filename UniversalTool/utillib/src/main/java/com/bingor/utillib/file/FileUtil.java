package com.bingor.utillib.file;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HXB on 2017/10/16.
 */

public class FileUtil {
    public static final int FILE_TYPE_IMAGE = 0;
    public static final int FILE_TYPE_VIDEO = 1;
    public static final int FILE_TYPE_AUDIO = 2;
    public static final int FILE_TYPE_TXT = 3;

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static void deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        deleteFile(new File(path));
    }

    /**
     * 删除文件夹及其子目录
     *
     * @param file
     */
    public static void deleteDirectory(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            deleteFile(file);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    if (child.isDirectory()) {
                        deleteDirectory(child);
                    }
                    deleteFile(child);
                }
            }
        }
    }

    /**
     * 删除文件夹及其子目录
     *
     * @param path
     */
    public static void deleteDirectory(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        deleteDirectory(new File(path));
    }


    /**
     * 获取存储文件的File
     *
     * @param type
     * @return
     */
    public static File getFile(int type, String path) {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        if (!fileDir.exists()) {
            return null;
        }
        File resFile = null;
        switch (type) {
            case FILE_TYPE_IMAGE:
                resFile = new File(fileDir, getTimeNow() + ".jpg");
                break;
            case FILE_TYPE_VIDEO:
                resFile = new File(fileDir, getTimeNow() + ".jpg");
                break;
            case FILE_TYPE_AUDIO:
                resFile = new File(fileDir, getTimeNow() + ".mp3");
                break;
            case FILE_TYPE_TXT:
                resFile = new File(fileDir, getTimeNow() + ".txt");
                break;
            default:
                return null;
        }
        if (!resFile.exists()) {
            try {
                resFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resFile;
    }

    private static String getTimeNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
