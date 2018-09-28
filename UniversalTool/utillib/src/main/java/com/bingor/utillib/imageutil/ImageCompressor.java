package com.bingor.utillib.imageutil;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;


import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Bingor
 * 图片压缩器
 * ***************************使用方法********************************
 * new ImageCompressor.Builder()
 *                 .setImgPath(path)
 *                 .setOutputPath(outPath)
 *                 .setMaxSize(512)
 *                 .setMaxWidth(720)
 *                 .setMaxHeight(720)
 *                 .build()
 *                 .compress();
 * ******************************************************************
 */
public class ImageCompressor {
    public static final String KEY_WIDTH = "width";
    public static final String KEY_HEIGHT = "height";

    private String imgPath, outputPath;
    private Bitmap bitmap, bitmapCompress;
    private int maxWidth, maxHeight;
    private int maxSize, quality;
    private int degree;

    private ImageCompressor() {
    }

    public static class Builder {
        private ImageCompressor ImageCompressor;

        public Builder() {
            ImageCompressor = new ImageCompressor();
        }

        public ImageCompressor build() {
            return ImageCompressor;
        }

        public Builder setImgPath(String imgPath) {
            ImageCompressor.imgPath = imgPath;
            if (!TextUtils.isEmpty(imgPath)) {
                ImageCompressor.degree = ImageCompressor.getPicRotate(imgPath);
            }
            return this;
        }

        public Builder setOutputPath(String outputPath) {
            ImageCompressor.outputPath = outputPath;
            return this;

        }

        public Builder setBitmap(Bitmap bitmap) {
            ImageCompressor.bitmap = bitmap;
            return this;
        }

        public Builder setMaxWidth(int maxWidth) {
            ImageCompressor.maxWidth = maxWidth;
            return this;
        }

        public Builder setMaxHeight(int maxHeight) {
            ImageCompressor.maxHeight = maxHeight;
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            ImageCompressor.maxSize = maxSize;
            return this;
        }
    }


    public Bitmap compress() throws Exception {
        if (bitmap == null) {
            if (TextUtils.isEmpty(imgPath)) {
                return null;
            }
            getBitmap();
        }
        if (bitmap == null) {
            return null;
        }
        if (maxWidth != 0 && maxHeight != 0) {
            ratio();
        } else {
            bitmapCompress = bitmap;
        }
        compressImageQuality();
        if (bitmapCompress != null) {
            storeImage(bitmapCompress, outputPath);
        }
        return bitmapCompress;
    }

    /**
     * 从路径中获取Bitmap
     * Get bitmap from specified image path
     *
     * @return
     */
    public Bitmap getBitmap() throws Exception {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Config.RGB_565;
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);

        if (newOpts.mCancel || newOpts.outWidth == -1 || newOpts.outHeight == -1) {
            //表示图片已损毁
            bitmap = null;
            throw new Exception("图片已损毁");
        }
        return bitmap;
    }

    /**
     * 保存Bitmap到路径
     * Store bitmap into specified image path
     *
     * @param bitmap
     * @param outPath
     * @throws FileNotFoundException
     */
    public void storeImage(Bitmap bitmap, String outPath) throws FileNotFoundException {
        FileOutputStream os = new FileOutputStream(outPath);
        if (quality == 0 || quality == 100) {
            FileInputStream is = new FileInputStream(imgPath);
            byte[] data = new byte[1024];
            try {
                while (is.read(data) != -1) {
                    os.write(data);
                    os.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), getPicRotateMatrix(bitmap), true);// 从新生成图片
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
        }
    }


    /**
     * 压缩图片尺寸
     * Compress image by size, ImageCompressor.will modify image width/height.
     * Used to get thumbnail
     *
     * @return
     */
    private Bitmap ratio() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        quality = 100;
        //避免内存溢出，先压缩到5MB
        while (os.toByteArray().length / 1024 > 5120) {
            os.reset();
            quality -= 5;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Config.RGB_565;
        bitmapCompress = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = maxHeight;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = maxWidth;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmapCompress = BitmapFactory.decodeStream(is, null, newOpts);
        return bitmapCompress;
    }


    /**
     * 压缩图片质量
     * Compress by quality,  and generate image to the path specified
     *
     * @throws IOException
     */
    private void compressImageQuality() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        // Store the bitmap into output stream(no compress)
        bitmapCompress.compress(Bitmap.CompressFormat.JPEG, quality, os);
        // Compress by loop

        while (os.toByteArray().length / 1024 > maxSize) {
            int aa = os.toByteArray().length / 1024;
            // Clean up os
            os.reset();
            // interval 10
            quality -= 5;
            bitmapCompress.compress(Bitmap.CompressFormat.JPEG, quality, os);
        }
    }


    /**
     * Bitmap转byte数组
     *
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 读取图片文件旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    private int getPicRotate(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private Matrix getPicRotateMatrix(Bitmap bitmap) {
        Matrix m = new Matrix();
        m.setRotate(degree); // 旋转angle度
        return m;
    }


    // -------------------------------------------------------------------
    //通过传入位图,新的宽.高比进行位图的缩放操作
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {

        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;


        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);

        return resizedBitmap;

    }

    /**
     * @param bitmap
     * @param resizeBy BitmapHelper.KEY_WIDTH  BitmapHelper.KEY_HEIGHT
     * @param reqLen
     * @return
     */
    public static byte[] resizeImage(Bitmap bitmap, String resizeBy, int reqLen) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        //        int quality = 100;
        //        //避免内存溢出，先压缩到5MB
        //        while (os.toByteArray().length / 1024 > 5120) {
        //            os.reset();
        //            quality -= 5;
        //            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, os);
        //        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Config.RGB_565;
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        //be=1表示不缩放
        int be = 1;
        if (resizeBy.equals(KEY_WIDTH)) {
            if (w > reqLen) {
                be = newOpts.outWidth / reqLen;
            }
        } else if (resizeBy.equals(KEY_HEIGHT)) {
            if (h > reqLen) {
                be = newOpts.outHeight / reqLen;
            }
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        os.reset();
        try {
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        return os.toByteArray();
    }

    /**
     * 保存Bitmap到路径
     * Store bitmap into specified image path
     *
     * @param bitmap
     * @param file
     * @throws FileNotFoundException
     */
    public static void saveImage(Bitmap bitmap, File file) throws FileNotFoundException {
        try {
            file.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}