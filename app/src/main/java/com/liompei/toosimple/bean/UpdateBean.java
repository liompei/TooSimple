package com.liompei.toosimple.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by BLM on 2016/7/15.
 */
public class UpdateBean extends BmobObject {

    private String   versionName, downloadPath;  //版本码用于比较,版本名用于显示
    private int versionCode;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
