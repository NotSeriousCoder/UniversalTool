package com.bingor.utillib.hardware;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;


import com.bingor.utillib.system.BuildProperties;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by LJW on 2016/7/26.
 */
public class DeviceUtil {
    public static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    public static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    public static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    public static final String PACKAGE_NAME_WEIJING = "cn.gov.weijing.ns.wz";
    private static final String TAG = "DeviceUtil";

    public static final String MODEL_HUAWEI = "HUAWEI";

    /**
     * 查找设备是否存在对应包名的APP
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean appExists(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                if (packName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    //当前应用是否处于前台
    public static boolean isForeground(Context context) {
        if (context != null) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
                if (processInfo.processName.equals(context.getPackageName())) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断当前系统是否MIUI
     *
     * @return
     */
    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * 判断当前系统是否Flyme
     *
     * @return
     */
    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * 播放声音
     *
     * @param activity
     * @param beeResId
     */
    public static void makeBee(Activity activity, int beeResId) {
        final float BEEP_VOLUME = 0.10f;

        MediaPlayer mediaPlayer = null;

        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
            }
        });
        AssetFileDescriptor file = activity.getResources().openRawResourceFd(beeResId);
        try {
            mediaPlayer.setDataSource(file.getFileDescriptor(),
                    file.getStartOffset(), file.getLength());
            file.close();
            mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
            mediaPlayer.prepare();
        } catch (IOException e) {
            mediaPlayer = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }


    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static final String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取手机品牌型号
     *
     * @return
     */
    public static String getPhoneModel() {
        String phoneModel = "";
        try {
            phoneModel = Build.BRAND + "--" + Build.MODEL;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phoneModel;
    }


    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getSystemVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }


}
