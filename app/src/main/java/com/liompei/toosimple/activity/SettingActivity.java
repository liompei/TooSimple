package com.liompei.toosimple.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liompei.toosimple.R;
import com.liompei.toosimple.bean.FeedbackBean;
import com.liompei.toosimple.update.UpdateManager;
import com.liompei.toosimple.utils.Z;

import java.io.File;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private LinearLayout checkUpdate, share, deleteImg, feedback, about, version;
    private TextView versionName;

    private PackageInfo packageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //用于获取版本信息
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        checkUpdate = (LinearLayout) findViewById(R.id.checkUpdate);
        share = (LinearLayout) findViewById(R.id.share);
        deleteImg = (LinearLayout) findViewById(R.id.deleteImg);
        feedback = (LinearLayout) findViewById(R.id.feedback);
        about = (LinearLayout) findViewById(R.id.about);
        version = (LinearLayout) findViewById(R.id.version);
        versionName = (TextView) findViewById(R.id.versionName);
        versionName.setText(packageInfo.versionName);  //设置版本信息
        checkUpdate.setOnClickListener(this);
        feedback.setOnClickListener(this);
        share.setOnClickListener(this);
        deleteImg.setOnClickListener(this);
        about.setOnClickListener(this);
        version.setOnClickListener(this);


    }


    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.checkUpdate:
                int code = packageInfo.versionCode;
                UpdateManager updateManager = new UpdateManager(this, code);
                updateManager.check();
                break;
            case R.id.share:

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "简单App,提高自己的姿势水平" + " http://newandroid.cn");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share"));

                break;
            case R.id.deleteImg:

                File file = new File(Environment.getExternalStorageDirectory() + "/TooSimple/");
                de(file);

                break;

            case R.id.feedback:

                LayoutInflater inflater = LayoutInflater.from(this);
                View view1 = inflater.inflate(R.layout.dialog_feedback, null);
                final EditText etemail = (EditText) view1.findViewById(R.id.email);
                final EditText etcontent = (EditText) view1.findViewById(R.id.content);

                new AlertDialog.Builder(this)
                        .setTitle("feedback")
                        .setView(view1)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String email = etemail.getText().toString().trim();
                                String content = etcontent.getText().toString().trim();
                                if ("".equals(content)) {
                                    Toast.makeText(SettingActivity.this, "...别玩我", Toast.LENGTH_SHORT).show();
                                } else {
                                    final ProgressDialog progress = new ProgressDialog(SettingActivity.this);
                                    progress.show();

                                    FeedbackBean feedbackB = new FeedbackBean();
                                    feedbackB.setEmail(email);
                                    feedbackB.setContent(content);
                                    feedbackB.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null) {
                                                progress.dismiss();
                                                Snackbar.make(view, "Thank You", Snackbar.LENGTH_SHORT).show();
                                            } else {
                                                Z.show("反馈失败");
                                                progress.dismiss();
                                            }
                                        }
                                    });


                                }


                            }
                        })
                        .setCancelable(false)
                        .show();


                break;
            case R.id.about:

                new AlertDialog.Builder(this)
                        .setTitle("About")
                        .setMessage("很惭愧，就做了一点微小的工作")
                        .setNegativeButton("微博", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Uri uri = Uri.parse("http://weibo.com/u/3169703803?refer_flag=1001030102_&is_all=1");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("知乎", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Uri uri = Uri.parse("https://www.zhihu.com/people/bei-li-ming");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        })
                        .show();

                break;
            case R.id.version:
                break;


        }
    }


    private void de(File file) {
        if (file.isFile()) {
            file.delete();
            Z.show("success");
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                de(f);
            }
            file.delete();
        }
    }
}
