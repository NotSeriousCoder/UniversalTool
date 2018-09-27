package com.bingor.utillib.imageutil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.bingor.utillib.general.UriUtil;

import java.io.File;

/**
 * 图片获取工具
 * Created by Bingor on 2018/3/15.
 */

public class TakePictureUtil {
    //拍照不裁剪
    public static final int REQUEST_TAKE_PHOTO_NOCUT = 0x3200;
    //拍照+裁剪
    public static final int REQUEST_TAKE_PHOTO_CUT = 0x3201;
    //选取本地图片不裁剪
    public static final int REQUEST_PICK_PHOTO_NOCUT = 0x3202;
    //选取本地图片+裁剪
    public static final int REQUEST_PICK_PHOTO_CUT = 0x3203;
    //裁剪图片
    public static final int REQUEST_CODE_CROP_ICON = 0x3204;

    public static File imageFile;

    /**
     * 启动拍照
     */
    public static void takePhotoCamera(boolean cut, File outputFile, Activity activity) {
        imageFile = outputFile;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Continue only if the File was successfully created
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, UriUtil.getUriForFile(activity, imageFile));//设置文件保存的URI
            if (cut) {
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_CUT);
            } else {
                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO_NOCUT);
            }
        }
    }

    /**
     * 选取本地图片
     */
    public static void pickPhotoLocal(boolean cut, Activity activity) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        if (cut) {
            activity.startActivityForResult(intent, REQUEST_PICK_PHOTO_CUT);
        } else {
            activity.startActivityForResult(intent, REQUEST_PICK_PHOTO_NOCUT);
        }
    }

    public static File handleResult(Activity activity, int requestCode, int resultCode, Intent data, File targetFile) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO_CUT:
                    crop(activity, UriUtil.getUriForFile(activity, imageFile), targetFile);
                    break;
                case REQUEST_TAKE_PHOTO_NOCUT:
                    return imageFile;
                case REQUEST_PICK_PHOTO_CUT://选择图片
                    if (data != null && data.getData() != null) {
                        crop(activity, data.getData(), targetFile);
                    }
                    break;
                case REQUEST_PICK_PHOTO_NOCUT:
                    if (data != null && data.getData() != null) {
                        String path = UriUtil.getRealPathFromUri(activity, data.getData());
                        if (!TextUtils.isEmpty(path)) {
                            return new File(path);
                        }
                    }
                    break;
                case REQUEST_CODE_CROP_ICON:
                    return imageFile;
            }
        }
        return null;
    }


    /**
     * 裁剪图片
     *
     * @param uri 目标图片Uri
     */
    private static void crop(Activity activity, Uri uri, File imageFile) {
        Intent intent = new Intent();
        intent.setAction("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(uri, "image/*");// mUri是已经选择的图片Uri
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);// 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);// 是否保留比例
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //        intent.putExtra(MediaStore.EXTRA_OUTPUT, UriUtil.getUriForFile(ActivityManager.currentActivity, file));
        //这里只能使用Uri.fromFile(file)，否则无法保存裁减后的图片
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        activity.startActivityForResult(intent, REQUEST_CODE_CROP_ICON);
        TakePictureUtil.imageFile = imageFile;
    }
}
