package com.liompei.toosimple;

import android.app.Application;

import org.xutils.x;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by BLM on 2016/7/11.
 */
public class MyApplication extends Application {

    public static MyApplication instance;
    //id,用户名,密码,邮箱
    public static String NAME = "李狗蛋";
    public static String PASSWORD = "2222";
    public static String ID = "123456";
    public static String EMAIL = "1137694912@qq.com";


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initBmob();
        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }

    private void initBmob() {
        //第一：默认初始化
//        Bmob.initialize(this, "752a4f4853f7a596be1b90ccf974ecd6");

//        第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("752a4f4853f7a596be1b90ccf974ecd6")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(15)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }

    public static MyApplication instance() {
        return instance;
    }
}
