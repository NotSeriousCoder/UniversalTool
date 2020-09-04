package com.bingor.universaltool;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bingor.utillib.data.MapUtil;
import com.bingor.utillib.hardware.DeviceUtil;
import com.bingor.utillib.hardware.ScreenUtil;
import com.bingor.utillib.log.LocalLogger;
import com.bingor.utillib.log.Log;
import com.bingor.utillib.system.PermissionApplier;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView tvCounter;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Integer> params = new HashMap<>();
                params.put("tom", 2);
                params.put("ken", 2);
                params.put("alex", 2);
                params.put("Brown", 2);
                params.put("James", 2);
                params.put("cake", 2);
                params.put("cber", 2);
                params.put("Drecer", 2);
                Log.d(params.toString());
                Log.d("=======================================");

                LinkedHashMap res = MapUtil.sortMap(params, true);
                Log.d(res.toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
