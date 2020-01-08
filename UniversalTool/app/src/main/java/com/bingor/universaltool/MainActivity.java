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

import com.bingor.utillib.hardware.DeviceUtil;
import com.bingor.utillib.hardware.ScreenUtil;
import com.bingor.utillib.log.LocalLogger;
import com.bingor.utillib.log.Log;
import com.bingor.utillib.system.PermissionApplier;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvCounter;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!PermissionApplier.checkPermissions(this, 0x1020, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            LocalLogger.getInstance().setPath(getPackageName()).open();
        }

        findViewById(R.id.bt_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.et_content);
                LocalLogger.getInstance().log(editText.getText().toString(), System.currentTimeMillis());
            }
        });

        findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalLogger.getInstance().close();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x1020 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LocalLogger.getInstance().setPath(getPackageName()).open();
        }
    }

}
