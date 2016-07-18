package com.liompei.toosimple.utils;

import android.widget.Toast;

import com.liompei.toosimple.MyApplication;

/**
 * Created by BLM on 2016/7/12.
 */
public class Z {

    public static void log(String msg) {
//        Log.d("ooxx", msg);
    }

    public static void toast(String msg) {
//        Toast.makeText(MyApplication.instance(), msg, Toast.LENGTH_SHORT).show();
    }


    public static void show(String msg) {
//        Log.d("zzzz", msg);
        Toast.makeText(MyApplication.instance(), msg, Toast.LENGTH_SHORT).show();
    }

}
