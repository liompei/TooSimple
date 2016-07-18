package com.liompei.toosimple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.liompei.toosimple.R;
import com.liompei.toosimple.adapter.CommentAdapter;
import com.liompei.toosimple.bean.JokesCommentBean;
import com.liompei.toosimple.bean.MoviesCommentBean;
import com.liompei.toosimple.bean.NewsCommentBean;
import com.liompei.toosimple.bean.OoxxCommentBean;
import com.liompei.toosimple.bean.PicCommentBean;
import com.liompei.toosimple.utils.Z;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 评论表,根据传入不同的类型和id解析不同表里的内容
 */
public class CommentActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String style, id;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private List<Map<String, String>> mapList;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
    }

    private void initView() {
        style = getIntent().getStringExtra("style");
        id = getIntent().getStringExtra("id");
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("评论");
        toolbar.setNavigationIcon(R.drawable.close);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setColorSchemeResources(android.R.color.holo_red_light);
        swipe.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mapList = new ArrayList<>();
        commentAdapter = new CommentAdapter(this, mapList);
        recyclerView.setAdapter(commentAdapter);

        refresh();

    }


    //net
    private void getInterData() {

        if (style.equals("news")) {
            getNewsInterData();
        } else if (style.equals("pic")) {
            getPicInterData();

        } else if (style.equals("ooxx")) {
            getOoxxInterData();

        } else if (style.equals("jokes")) {
            getJokesInterData();
        } else if (style.equals("movies")) {
            getMoviesInterData();
        }
    }


    private void refresh() {

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                getInterData();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_comment, menu);

        return true;  //显示
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addcomment:
                Intent i = new Intent(this, AddCommentActivity.class);
                i.putExtra("style", style);
                i.putExtra("id", id);
                startActivityForResult(i, 20);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getInterData();
    }


    /**
     * 根据不同表获取不同数据库的数据
     */
    private void getNewsInterData() {

        BmobQuery<NewsCommentBean> bmobQuery = new BmobQuery<>();
        bmobQuery.order("createdAt");
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.findObjects(new FindListener<NewsCommentBean>() {
            @Override
            public void done(List<NewsCommentBean> list, BmobException e) {

                if (e == null) {
                    mapList.clear();
                    for (NewsCommentBean bean : list) {
                        String objectId = bean.getObjectId();
                        String time = bean.getCreatedAt();
                        String id = bean.getId();
                        String name = bean.getName();
                        String content = bean.getContent();
                        String email = bean.getEmail();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("objectId", objectId);
                        map.put("time", time);
                        map.put("id", id);
                        map.put("name", name);
                        map.put("content", content);
                        map.put("email", email);
                        mapList.add(map);
                    }
                    commentAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
//                    Z.log("查询成功");

                } else {
                    Z.show("查询失败");
                    swipe.setRefreshing(false);
                }


            }
        });
    }

    private void getPicInterData() {
        BmobQuery<PicCommentBean> bmobQuery = new BmobQuery<>();
        bmobQuery.order("createdAt");
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.findObjects(new FindListener<PicCommentBean>() {
            @Override
            public void done(List<PicCommentBean> list, BmobException e) {

                if (e == null) {
                    mapList.clear();
                    for (PicCommentBean bean : list) {
                        String objectId = bean.getObjectId();
                        String time = bean.getCreatedAt();
                        String id = bean.getId();
                        String name = bean.getName();
                        String content = bean.getContent();
                        String email = bean.getEmail();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("objectId", objectId);
                        map.put("time", time);
                        map.put("id", id);
                        map.put("name", name);
                        map.put("content", content);
                        map.put("email", email);
                        mapList.add(map);
                    }
                    commentAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);

                } else {
                    Z.show("查询失败");
                    swipe.setRefreshing(false);
                }


            }
        });
    }

    private void getOoxxInterData() {
        BmobQuery<OoxxCommentBean> bmobQuery = new BmobQuery<>();
        bmobQuery.order("createdAt");
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.findObjects(new FindListener<OoxxCommentBean>() {
            @Override
            public void done(List<OoxxCommentBean> list, BmobException e) {

                if (e == null) {
                    mapList.clear();
                    for (OoxxCommentBean bean : list) {
                        String objectId = bean.getObjectId();
                        String time = bean.getCreatedAt();
                        String id = bean.getId();
                        String name = bean.getName();
                        String content = bean.getContent();
                        String email = bean.getEmail();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("objectId", objectId);
                        map.put("time", time);
                        map.put("id", id);
                        map.put("name", name);
                        map.put("content", content);
                        map.put("email", email);
                        mapList.add(map);
                    }
                    commentAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);

                } else {
                    Z.show("查询失败");
                    swipe.setRefreshing(false);
                }


            }
        });
    }

    private void getJokesInterData() {
        BmobQuery<JokesCommentBean> bmobQuery = new BmobQuery<>();
        bmobQuery.order("createdAt");
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.findObjects(new FindListener<JokesCommentBean>() {
            @Override
            public void done(List<JokesCommentBean> list, BmobException e) {

                if (e == null) {
                    mapList.clear();
                    for (JokesCommentBean bean : list) {
                        String objectId = bean.getObjectId();
                        String time = bean.getCreatedAt();
                        String id = bean.getId();
                        String name = bean.getName();
                        String content = bean.getContent();
                        String email = bean.getEmail();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("objectId", objectId);
                        map.put("time", time);
                        map.put("id", id);
                        map.put("name", name);
                        map.put("content", content);
                        map.put("email", email);
                        mapList.add(map);
                    }
                    commentAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);

                } else {
                    Z.show("查询失败");
                    swipe.setRefreshing(false);
                }


            }
        });
    }

    private void getMoviesInterData() {
        BmobQuery<MoviesCommentBean> bmobQuery = new BmobQuery<>();
        bmobQuery.order("createdAt");
        bmobQuery.addWhereEqualTo("id", id);
        bmobQuery.findObjects(new FindListener<MoviesCommentBean>() {
            @Override
            public void done(List<MoviesCommentBean> list, BmobException e) {

                if (e == null) {
                    mapList.clear();
                    for (MoviesCommentBean bean : list) {
                        String objectId = bean.getObjectId();
                        String time = bean.getCreatedAt();
                        String id = bean.getId();
                        String name = bean.getName();
                        String content = bean.getContent();
                        String email = bean.getEmail();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("objectId", objectId);
                        map.put("time", time);
                        map.put("id", id);
                        map.put("name", name);
                        map.put("content", content);
                        map.put("email", email);
                        mapList.add(map);
                    }
                    commentAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);

                } else {
                    Z.show("查询失败");
                    swipe.setRefreshing(false);
                }


            }
        });
    }

    //根据返回值判断是否需要刷新界面


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 10) {
            refresh();
        }


    }
}
