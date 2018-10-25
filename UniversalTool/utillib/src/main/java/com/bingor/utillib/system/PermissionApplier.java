package com.bingor.utillib.system;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


import com.bingor.utillib.hardware.DeviceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HXB on 2017-06-30.
 */
public class PermissionApplier {
    private String[] permissions;
    //权限请求次数
    private int permissionRequestCount = 0;
    //是否需要继续申请
    private boolean needRequestPermission = true;
    private PermissionRequestor requestor;
    private Activity activity;
    private int reqCode;
    private OnPermissionListener onPermissionListener;

    public PermissionApplier(Activity activity, int reqCode, String... permissions) {
        this.permissions = permissions;
        this.activity = activity;
        this.reqCode = reqCode;
    }

    public void startRequest() {
        startRequest(3000);
    }

    public void startRequest(int interval) {
        if (requestor != null && requestor.isAlive()) {
            return;
        }
        needRequestPermission = true;
        requestor = new PermissionRequestor(interval);
        requestor.start();
    }

    /**
     * 检查权限是否已请求到 (6.0)
     *
     * @param act
     * @param reqCode
     * @param permissions
     * @return 是否缺失权限
     */
    public static boolean checkPermissions(Activity act, int reqCode, String... permissions) {
        boolean lack = false;
        // 版本兼容
        // 判断缺失哪些必要权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (lack = lacksPermissions(act, permissions))) {
            // 如果缺失,则申请
            requestPermissions(act, reqCode, permissions);
        }
        return lack;
    }

    public static boolean checkPermissions(Fragment frg, int reqCode, String... permissions) {
        boolean lack = false;
        // 版本兼容
        // 判断缺失哪些必要权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (lack = lacksPermissions(frg, permissions))) {
            // 如果缺失,则申请
            requestPermissions(frg, reqCode, permissions);
        }
        return lack;
    }


    /**
     * 判断是否缺失权限集合中的权限
     */
    public static boolean lacksPermissions(Activity act, String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(act, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺失权限集合中的权限
     */
    public static boolean lacksPermissions(Fragment fragment, String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(fragment, permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否缺少某个权限
     */
    private static boolean lacksPermission(Activity act, String permission) {
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //            Log.d("权限情况==" + act.checkSelfPermission(permission) + "  " + ContextCompat.checkSelfPermission(ActivityManager.currentActivity, permission));
        //        } else {
        //            Log.d("权限情况==" + ContextCompat.checkSelfPermission(ActivityManager.currentActivity, permission));
        //        }
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //            return act.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED;
        //        } else {
        return ContextCompat.checkSelfPermission(act, permission) == PackageManager.PERMISSION_DENIED;
        //        }
    }

    /**
     * 判断是否缺少某个权限
     */
    private static boolean lacksPermission(Fragment fragment, String permission) {
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //            Log.d("权限情况==" + fragment.getActivity().checkSelfPermission(permission) + "  " + ContextCompat.checkSelfPermission(ActivityManager.currentActivity, permission));
        //        } else {
        //            Log.d("权限情况==" + ContextCompat.checkSelfPermission(ActivityManager.currentActivity, permission));
        //        }
        return ContextCompat.checkSelfPermission(fragment.getActivity(), permission) == PackageManager.PERMISSION_DENIED;
    }


    /**
     * 请求权限
     */
    private static void requestPermissions(Activity act, int reqCode, String... permissions) {
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //            act.requestPermissions(permissions, reqCode);
        //        } else {
        ActivityCompat.requestPermissions(act, permissions, reqCode);
        //        }
    }

    /**
     * 请求权限
     */
    private static void requestPermissions(Fragment frg, int reqCode, String... permissions) {
        frg.requestPermissions(permissions, reqCode);
    }

    /**
     * 启动应用的设置,进入手动配置权限页面
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void openPermissionSetting(Context context) {
        Intent intent = new Intent();
        if (DeviceUtil.isMIUI()) {
            try {
                switch (BuildProperties.newInstance().getProperty(DeviceUtil.KEY_MIUI_VERSION_NAME)) {
                    case "V5":
                        Uri packageURI = Uri.parse("package:" + context.getApplicationInfo().packageName);
                        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                        break;
                    case "V6":
                    case "V7":
                        intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("extra_pkgname", context.getPackageName());
                        break;
                    case "V8":
                        intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                        intent.putExtra("extra_pkgname", context.getPackageName());
                        break;
                    default:
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (DeviceUtil.isFlyme() || Build.MANUFACTURER.equals("Meizu")) {
            intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", context.getPackageName());
        } else {
            switch (Build.MANUFACTURER) {
                case "HUAWEI":
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", context.getPackageName());
                    ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
                    intent.setComponent(comp);
                    break;
                case "Sony":
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", context.getPackageName());
                    ComponentName compSony = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
                    intent.setComponent(compSony);
                    break;
                case "LG":
                    intent.setAction("android.intent.action.MAIN");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", context.getPackageName());
                    ComponentName compLG = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
                    intent.setComponent(compLG);
                    break;
                case "Letv":
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", context.getPackageName());
                    ComponentName compLS = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
                    intent.setComponent(compLS);
                    break;
                case "360":
                    intent.setAction("android.intent.action.MAIN");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", context.getPackageName());
                    ComponentName comp360 = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
                    intent.setComponent(comp360);
                    break;
                case "ZTE":
                case "YuLong":
                case "LENOVO":
                case "vivo":
                case "samsung":
                case "OPPO":
                    //报 com.color.safecenter.permission.PermissionManagerActivity 找不到
                    //找不到OPPO的机子测试，先不管
                    //                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //                    intent.putExtra("packageName", context.getPackageName());
                    //                    ComponentName compOPPO = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
                    //                    intent.setComponent(compOPPO);
                    //                    break;
                default:
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                    break;
            }
        }
        context.startActivity(intent);
    }

    public OnPermissionListener getOnPermissionListener() {
        return onPermissionListener;
    }

    public void setOnPermissionListener(OnPermissionListener onPermissionListener) {
        this.onPermissionListener = onPermissionListener;
    }

    private class PermissionRequestor extends Thread {
        private int interval;

        public PermissionRequestor(int interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            while (needRequestPermission) {
                if (lacksPermissions(activity, permissions)) {
                    permissionRequestCount++;
                    //权少权限
                    if (permissionRequestCount > 2) {
                        final List<String> pers = new ArrayList();
                        for (String permission : permissions) {
                            if (lacksPermission(activity, permission)) {
                                pers.add(permission);
                            }
                        }
                        if (onPermissionListener != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onPermissionListener.onNeedManual(pers);
                                }
                            });
                        }
                        needRequestPermission = false;
                    } else {
                        requestPermissions(activity, reqCode, permissions);
                        if (onPermissionListener != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onPermissionListener.onPermissionLack();
                                }
                            });
                        }
                    }
                } else {
                    needRequestPermission = false;
                    if (onPermissionListener != null) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onPermissionListener.onPermissionPossess();
                            }
                        });
                    }
                }

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface OnPermissionListener {
        public void onPermissionLack();

        public void onPermissionPossess();

        public void onNeedManual(List<String> persLack);
    }
}
