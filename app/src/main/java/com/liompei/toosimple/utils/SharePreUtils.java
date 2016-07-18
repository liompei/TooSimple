package com.liompei.toosimple.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.liompei.toosimple.MyApplication;

/**
 * Created by BLM on 2016/7/12.
 */
public class SharePreUtils {

    public static void putSP(String key, String value) {
        String FILE = "TooSimpleFR";
        SharedPreferences sharedPreferences = MyApplication.instance().getSharedPreferences(FILE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getSP(String property) {
        String FILE = "TooSimpleFR";
        SharedPreferences sharedPreferences = MyApplication.instance().getSharedPreferences(FILE, Context.MODE_PRIVATE);
        String string = sharedPreferences.getString(property, "news");
        return string;
    }
}
