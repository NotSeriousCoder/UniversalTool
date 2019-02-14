package com.bingor.universaltool;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bingor.utillib.hardware.ScreenUtil;

public class MainActivity extends AppCompatActivity {
    private TextView tvCounter;

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

        tvCounter = findViewById(R.id.tv_counter);
//        findViewById(R.id.tv_counter).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TimeCounter.Builder
//                        .createDefaultCounter((TextView) findViewById(R.id.tv_counter), new TimeCounter.OnTimeCountListener() {
//                            @Override
//                            public void onCount(String count) {
//                                Log.d("HXB", count);
//                            }
//
//                            @Override
//                            public void onFinish(String textDefault) {
//                                Log.d("HXB", textDefault);
//                            }
//                        })
//                        .start();
//            }
//        });


//        Point size = ScreenUtil.getScreenSize(this);
//        tvCounter.setText("width==" + size.x + "   height==" + size.y);

        findViewById(R.id.bt_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        tvCounter.setText("orientation==" + ScreenUtil.getScreenRotation(MainActivity.this));
                    }
                };
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        while (true) {
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            handler.sendEmptyMessage(1);
                        }
                    }
                }.start();
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
