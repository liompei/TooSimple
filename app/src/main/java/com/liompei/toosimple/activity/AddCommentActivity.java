package com.liompei.toosimple.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liompei.toosimple.R;
import com.liompei.toosimple.bean.JokesCommentBean;
import com.liompei.toosimple.bean.MoviesCommentBean;
import com.liompei.toosimple.bean.NewsCommentBean;
import com.liompei.toosimple.bean.OoxxCommentBean;
import com.liompei.toosimple.bean.PicCommentBean;
import com.liompei.toosimple.utils.Z;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddCommentActivity extends AppCompatActivity {

    private String style, id;
    private Toolbar toolbar;
    private EditText say;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        initView();
    }

    private void initView() {
        style = getIntent().getStringExtra("style");
        id = getIntent().getStringExtra("id");
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setNavigationIcon(R.drawable.close);
        toolbar.setTitle("回复");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        say = (EditText) findViewById(R.id.say);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_comment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sendcomment:
                String content = say.getText().toString().trim();
                if ("".equals(content)) {
                    Toast.makeText(AddCommentActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    sendInterData(content);
                }

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void sendInterData(final String content) {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_addcomment, null);
        final EditText name = (EditText) view.findViewById(R.id.name);
        final EditText email = (EditText) view.findViewById(R.id.email);


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("用户");
        builder.setView(view);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String sendName = name.getText().toString().trim();
                String sendEmail = email.getText().toString().trim();

                if (("".equals(sendName)) || "".equals(sendEmail)) {
                    Toast.makeText(AddCommentActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();

                } else {
                    //点击发送之后显示交互同时发送数据

                    ProgressDialog progressDialog = new ProgressDialog(AddCommentActivity.this);
                    progressDialog.setTitle("Loading...");
                    progressDialog.show();

                    isInter(content, sendName, sendEmail, progressDialog);
                }

            }
        });
        builder.show();


    }

    //判断并添加到数据库
    private void isInter(String content, String name, String email, ProgressDialog progressDialog) {
        if (style.equals("news")) {

            newsInter(content, name, email, progressDialog);
        } else if (style.equals("pic")) {

            picInter(content, name, email, progressDialog);
        } else if (style.equals("ooxx")) {

            ooxxInter(content, name, email, progressDialog);
        } else if (style.equals("jokes")) {
            jokesInter(content, name, email, progressDialog);
        } else if (style.equals("movies")) {
            moviesInter(content, name, email, progressDialog);
        }

    }

    private void moviesInter(String content, String name, String email, final ProgressDialog progressDialog) {
        Z.log("电影评论");
        MoviesCommentBean bean = new MoviesCommentBean();
        bean.setId(id);
        bean.setName(name);
        bean.setContent(content);
        bean.setEmail(email);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //保存成功,返回并率刷新数据
                    Z.show("发送成功");
                    progressDialog.dismiss();
                    setResult(10);
                    finish();
                } else {
                    Z.show("发送失败");
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void jokesInter(String content, String name, String email, final ProgressDialog progressDialog) {
        Z.log("段子评论");
        JokesCommentBean bean = new JokesCommentBean();
        bean.setId(id);
        bean.setName(name);
        bean.setContent(content);
        bean.setEmail(email);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //保存成功,返回并率刷新数据
                    Z.show("发送成功");
                    progressDialog.dismiss();
                    setResult(10);
                    finish();
                } else {
                    Z.show("发送失败");
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void ooxxInter(String content, String name, String email, final ProgressDialog progressDialog) {
        Z.log("妹子评论");
        OoxxCommentBean bean = new OoxxCommentBean();
        bean.setId(id);
        bean.setName(name);
        bean.setContent(content);
        bean.setEmail(email);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //保存成功,返回并率刷新数据
                    progressDialog.dismiss();
                    setResult(10);
                    finish();
                } else {
                    Z.show("发送失败");
                    progressDialog.dismiss();
                }
            }
        });


    }

    private void picInter(String content, String name, String email, final ProgressDialog progressDialog) {
        Z.log("斗图评论");
        PicCommentBean bean = new PicCommentBean();
        bean.setId(id);
        bean.setName(name);
        bean.setContent(content);
        bean.setEmail(email);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //保存成功,返回并率刷新数据
                    Z.show("发送成功");
                    progressDialog.dismiss();
                    setResult(10);
                    finish();
                } else {
                    Z.show("发送失败");
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void newsInter(String content, String name, String email, final ProgressDialog progressDialog) {
        Z.log("新闻评论");
        NewsCommentBean bean = new NewsCommentBean();
        bean.setId(id);
        bean.setName(name);
        bean.setContent(content);
        bean.setEmail(email);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    //保存成功,返回并率刷新数据
                    Z.show("发送成功");
                    progressDialog.dismiss();
                    setResult(10);
                    finish();
                } else {
                    Z.show("发送失败");
                    progressDialog.dismiss();
                }
            }
        });


    }


}
