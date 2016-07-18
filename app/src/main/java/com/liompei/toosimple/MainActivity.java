package com.liompei.toosimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.liompei.toosimple.activity.SettingActivity;
import com.liompei.toosimple.fragment.Fragment_Jokes;
import com.liompei.toosimple.fragment.Fragment_Movies;
import com.liompei.toosimple.fragment.Fragment_News;
import com.liompei.toosimple.fragment.Fragment_Ooxx;
import com.liompei.toosimple.fragment.Fragment_Pic;
import com.liompei.toosimple.utils.MyPermission;
import com.liompei.toosimple.utils.SharePreUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ListView listView;
    private List<Map<String, Object>> mapList;
    private SimpleAdapter simpleAdapter;
    private String mTitle;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private LinearLayout layout_drawer;
    private LinearLayout layout_setting;

    private int ss;

    private MyPermission myPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPermission = new MyPermission(this);
        myPermission.getPermission();

        initView();


    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitle("简单");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);
        layout_drawer = (LinearLayout) findViewById(R.id.layout_drawer);
        layout_setting = (LinearLayout) findViewById(R.id.layout_setting);
        layout_setting.setOnClickListener(this);
        mapList = getMapList();
        simpleAdapter = new SimpleAdapter(this, mapList, R.layout.drawer_list_item, new String[]{"title", "image"}, new int[]{R.id.drawer_item_text, R.id.drawer_item_image});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        actionBarDrawerToggle.syncState();  //这样设置不会有默认动画效果,需要加上下一句
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        openWho();
    }

    private void openWho() {
        String who = SharePreUtils.getSP("fragment");

        if (who.equals("news")) {
            ss = 0;
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_News()).commit();
            toolbar.setTitle("新鲜事");
        } else if (who.equals("pic")) {
            ss = 1;
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_Pic()).commit();
            toolbar.setTitle("斗图");
        } else if (who.equals("ooxx")) {
            ss = 2;
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_Ooxx()).commit();
            toolbar.setTitle("妹子图");
        } else if (who.equals("jokes")) {
            ss = 3;
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_Jokes()).commit();
            toolbar.setTitle("段子");
        } else if (who.equals("movies")) {
            ss = 4;
            getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_Movies()).commit();
            toolbar.setTitle("小电影");
        }


    }

    //填充mapList
    private List<Map<String, Object>> getMapList() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("title", "新鲜事");
        map1.put("image", R.mipmap.news);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("title", "斗图");
        map2.put("image", R.mipmap.pic);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("title", "妹子图");
        map3.put("image", R.mipmap.ooxx);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("title", "段子");
        map4.put("image", R.mipmap.jokes);
        Map<String, Object> map5 = new HashMap<>();
        map5.put("title", "小电影");
        map5.put("image", R.mipmap.movies);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
//        list.add(map5);  //小电影暂时隐藏
        return list;
    }


    //侧栏点击事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //动态插入一个fragment到FrameLayout当中
        //判断是否切换到其他栏
        if (ss == i) {

        } else {
            switch (i) {
                case 0:
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_News()).commit();
                    toolbar.setTitle("新鲜事");
                    SharePreUtils.putSP("fragment", "news");
                    ss = 0;
                    break;
                case 1:
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_Pic()).commit();
                    toolbar.setTitle("斗图");
                    SharePreUtils.putSP("fragment", "pic");
                    ss = 1;
                    break;
                case 2:
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_Ooxx()).commit();
                    toolbar.setTitle("妹子图");
                    SharePreUtils.putSP("fragment", "ooxx");
                    ss = 2;
                    break;
                case 3:
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_Jokes()).commit();
                    toolbar.setTitle("段子");
                    SharePreUtils.putSP("fragment", "jokes");
                    ss = 3;
                    break;
                case 4:
                    getFragmentManager().beginTransaction().replace(R.id.content_frame, new Fragment_Movies()).commit();
                    toolbar.setTitle("小电影");
                    SharePreUtils.putSP("fragment", "movies");
                    ss = 4;
                    break;
            }
        }


        drawerLayout.closeDrawer(layout_drawer);  //点击后关闭侧栏
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_setting:
                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(layout_drawer);  //点击后关闭侧栏
                break;

        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_refresh:
//
//                break;
//        }


//        return super.onOptionsItemSelected(item);
//    }
}
