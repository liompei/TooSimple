package com.liompei.toosimple.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by BLM on 2016/7/14.
 */
public class MyPermission {

    private Activity activity;
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10;

    public MyPermission(Activity activity) {
        this.activity = activity;
    }

    public void getPermission() {
        //判断当前版本是否大于M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断当前Activity是否获得了该权限
            if (ContextCompat.checkSelfPermission(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //没有授权,判断权限申请是否曾经被拒绝过
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(activity, "你曾经拒绝过此权限,拒绝将导致无法存图", Toast.LENGTH_SHORT).show();
                    //进行权限请求
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            EXTERNAL_STORAGE_REQ_CODE);
                } else {
                    //进行权限请求
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            EXTERNAL_STORAGE_REQ_CODE);
                }

            }


        }
    }
}
