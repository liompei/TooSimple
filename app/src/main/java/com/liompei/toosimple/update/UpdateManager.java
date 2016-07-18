package com.liompei.toosimple.update;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.liompei.toosimple.bean.UpdateBean;
import com.liompei.toosimple.utils.Z;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by BLM on 2016/7/15.
 */
public class UpdateManager {

    private Context context;
    private int versionCode;

    public UpdateManager(Context context, int versionCode) {
        this.context = context;
        this.versionCode = versionCode;
    }

    public void check() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在检查更新...");
        progressDialog.show();

        BmobQuery<UpdateBean> beanBmobQuery = new BmobQuery<>();
        beanBmobQuery.getObject("7sgqPPPY", new QueryListener<UpdateBean>() {
            @Override
            public void done(UpdateBean updateBean, BmobException e) {
                if (e == null) {
                    int versionC = updateBean.getVersionCode();
                    String versionN = updateBean.getVersionName();
                    String downloadP = updateBean.getDownloadPath();
                    if (versionC > versionCode) {
                        //更新
                        update(versionN, downloadP);
                    } else {
                        Z.show("已是最新版本");
                    }


                } else {
                    Z.show("检查更新失败");
                }
                progressDialog.dismiss();

            }
        });
    }


    private void update(String versionN, final String downloadP) {

        new AlertDialog.Builder(context)
                .setTitle("提醒")
                .setMessage("检测到新版本" + versionN + "\n\n是否下载更新?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse(downloadP);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }
                })
                .show();


    }

}
