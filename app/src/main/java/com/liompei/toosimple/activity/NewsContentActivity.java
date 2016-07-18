package com.liompei.toosimple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liompei.toosimple.R;
import com.liompei.toosimple.bean.NewsBean;
import com.liompei.toosimple.utils.Z;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class NewsContentActivity extends AppCompatActivity implements View.OnClickListener {

    private String id;
    private String title, content, time, senderId, senderName, senderEmail;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private TextView tvContent, tvTime, tvName;
    private ProgressBar progressBar;
    private FloatingActionButton fab;  //进入评论


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        initView();

    }

    private void initView() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        tvContent = (TextView) findViewById(R.id.content);
        tvName = (TextView) findViewById(R.id.name);
        tvTime = (TextView) findViewById(R.id.time);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        refresh();

    }


    //网络请求
    private void getInterData() {

        BmobQuery<NewsBean> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(id, new QueryListener<NewsBean>() {
            @Override
            public void done(NewsBean newsBean, BmobException e) {
                if (e == null) {
                    content = newsBean.getContent();
                    time = newsBean.getCreatedAt();
                    senderId = newsBean.getSenderid();
                    senderName = newsBean.getSenderusername();
                    senderEmail = newsBean.getSenderemail();
                    tvContent.setText(content);
                    tvTime.setText(time);
                    tvName.setText(senderName);
                    linearLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Z.log(content);
//                    Z.show("查询成功");
                } else {
                    Z.show("获取失败");
                }

            }
        });

    }


    private void refresh() {
        getInterData();
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("style", "news");
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
