package com.liompei.toosimple.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.liompei.toosimple.BaseFragment;
import com.liompei.toosimple.R;
import com.liompei.toosimple.activity.NewsContentActivity;
import com.liompei.toosimple.adapter.NewsAdapter;
import com.liompei.toosimple.bean.JokesBean;
import com.liompei.toosimple.bean.NewsBean;
import com.liompei.toosimple.utils.Z;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Created by BLM on 2016/7/11.
 */
public class Fragment_News extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    View view;
    private RecyclerView recyclerView;
    private List<Map<String, String>> mapList;
    private NewsAdapter adapter;

    private FloatingActionButton fab;
    private SwipeRefreshLayout swipe;
    private Handler handler;  //刷新


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        log("news");
        initView();
        return view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mapList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NewsAdapter(getActivity(), mapList);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());

        recyclerView.getItemAnimator().setAddDuration(1000);
        recyclerView.getItemAnimator().setRemoveDuration(1000);
        recyclerView.getItemAnimator().setMoveDuration(1000);
        recyclerView.getItemAnimator().setChangeDuration(1000);

        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(adapter);
//        alphaInAnimationAdapter.setDuration(1000);
        alphaInAnimationAdapter.setFirstOnly(false);  //只显示一次
//        recyclerView.setAdapter(alphaInAnimationAdapter);

        SlideInLeftAnimationAdapter slideInLeftAnimationAdapter = new SlideInLeftAnimationAdapter(adapter);
//        slideInLeftAnimationAdapter.setInterpolator(new OvershootInterpolator());
//        slideInLeftAnimationAdapter.setDuration(1000);

        recyclerView.setAdapter(slideInLeftAnimationAdapter);
        adapter.setListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, String id, String title) {
                //                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), NewsContentActivity.class);
                i.putExtra("id", id);
                i.putExtra("title", title);
                startActivity(i);
            }
        });

        //添加分割线
        //添加默认分割线：高度为2px，颜色为灰色
//        recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayout.HORIZONTAL));
        //添加自定义分割线：可自定义分割线drawable
//        recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayout.HORIZONTAL,R.mipmap.ic_launcher));
        //添加自定义分割线：可自定义分割线高度和颜色
//        recyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayout.HORIZONTAL,10,getResources().getColor(R.color.grey_300)));

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setColorSchemeResources(R.color.grey_900, R.color.blue_500);
        swipe.setOnRefreshListener(this);
        handler = new Handler();

        refresh();

    }


    //网络获取数据
    public void getInterNews() {
        BmobQuery<NewsBean> beanBmobQuery = new BmobQuery<>();
        beanBmobQuery.order("-createdAt");
        beanBmobQuery.addQueryKeys("objectId,title,senderusername");
        beanBmobQuery.findObjects(new FindListener<NewsBean>() {
            @Override
            public void done(List<NewsBean> list, BmobException e) {
                if (e == null) {
                    Z.log("查询成功");
                    mapList.clear();
                    for (NewsBean newsBean : list) {
                        String newsId = newsBean.getObjectId();
                        String newsTitle = newsBean.getTitle();
                        String senderusername = newsBean.getSenderusername();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("newsId", newsId);
                        map.put("newsTitle", newsTitle);
                        map.put("senderusername", senderusername);

                        mapList.add(map);

                    }
                    //查询成功后显示内容并停止刷新
                    adapter.notifyDataSetChanged();
//                    adapter.notify();
                    swipe.setRefreshing(false);

                } else {
                    Z.show("查询失败");
                    swipe.setRefreshing(false);
                }
            }
        });


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_refresh:
                refresh();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void refresh() {
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
            }
        });
        getInterNews();

    }


    //fab点击事件
    @Override
    public void onClick(View view) {
//        NewsBean newsBean = new NewsBean();
//        newsBean.setTitle();
//        newsBean.setContent();
//        newsBean.setSenderid();
//        newsBean.setSenderusername();
//        newsBean.setSenderemail();
//        newsBean.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    Z.show("发布成功");
//                } else {
//                    Z.show("发布失败");
//                }
//            }
//        });
    }


    //下拉刷新事件
    @Override
    public void onRefresh() {
        getInterNews();
    }
}
