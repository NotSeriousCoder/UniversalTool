package com.bingor.universaltool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bingor.utillib.hardware.DeviceUtil;
import com.bingor.utillib.imageutil.ImageCompressor;
import com.bingor.utillib.imageutil.TakePictureUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //选择本地图片
//        //cut：true==裁剪 false==不裁减
//        TakePictureUtil.pickPhotoLocal(true, activity);

        //拍照
        //cut：true==裁剪 false==不裁减
        //file 指定存放拍照图片的地址
//        TakePictureUtil.takePhotoCamera(true, file, activity);

        findViewById(R.id.bt_imei).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = DeviceUtil.getImeiId(MainActivity.this);
                Log.d("HXB", "IMEI==" + (aa == null ? "null" : aa));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //接收结果
        //不裁减的话，target不用传
        //裁剪的话，传入target，target是接收裁减后图像的File
//        File image = TakePictureUtil.handleResult(activity, requestCode, resultCode, data, target);
    }
}
