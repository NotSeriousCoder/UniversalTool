package com.bingor.utillib.general;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Manifest meta-data 信息读取工具
 * Created by Bingor on 2018/4/25.
 */

public class MetaDataReader {
    //////////////////////////////////////////读取application 节点  meta-data 信息/////////////////////////////////////////////////////////
    public static String readStringFromApplication(Context context, String key, String defaultValue) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static int readIntFromApplication(Context context, String key, int defaultValue) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getInt(key, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static boolean readBooleanFromApplication(Context context, String key, boolean defaultValue) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getBoolean(key, false);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    //////////////////////////////////////////END/////////////////////////////////////////////////////////


    //////////////////////////////////////////读取BroadcastReceiver 节点  meta-data 信息/////////////////////////////////////////////////////////
    public static String readStringFromBroadCast(Context context, Class receiverCls, String key, String defaultValue) {
        try {
            ComponentName cn = new ComponentName(context, receiverCls);
            ActivityInfo info = context.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static int readIntFromBroadCast(Context context, Class receiverCls, String key, int defaultValue) {
        try {
            ComponentName cn = new ComponentName(context, receiverCls);
            ActivityInfo info = context.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getInt(key, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static boolean readBooleanFromBroadCast(Context context, Class receiverCls, String key, boolean defaultValue) {
        try {
            ComponentName cn = new ComponentName(context, receiverCls);
            ActivityInfo info = context.getPackageManager().getReceiverInfo(cn, PackageManager.GET_META_DATA);
            return info.metaData.getBoolean(key, false);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    //////////////////////////////////////////END/////////////////////////////////////////////////////////

    //    /**
    //     * 读取Service 节点  meta-data 信息
    //     */
    //    private void readMetaDataFromService() {
    //        try {
    //            ComponentName cn = new ComponentName(this, DemoService.class);
    //            ServiceInfo info = this.getPackageManager().getServiceInfo(cn,
    //                    PackageManager.GET_META_DATA);
    //            String mkey = info.metaData.getString("mkey");
    //            Log.e(key, "mkey=" + mkey);
    //        } catch (NameNotFoundException e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    /**
    //     * 读取Activity 节点  meta-data 信息
    //     */
    //    private void readMetaDataFromActivity() {
    //        ActivityInfo info;
    //        try {
    //            info = this.getPackageManager().getActivityInfo(getComponentName(),
    //                    PackageManager.GET_META_DATA);
    //            String mkey = info.metaData.getString("mkey");
    //            Log.e(key, "mkey=" + mkey);
    //
    //            //读取图片资源id
    //            int mResource = info.metaData.getInt("mResource");
    //
    //            iv_pic.setImageResource(mResource);
    //
    //        } catch (NameNotFoundException e) {
    //            e.printStackTrace();
    //        }
    //
    //    }
}
