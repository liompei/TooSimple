package com.liompei.toosimple.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liompei.toosimple.BaseFragment;
import com.liompei.toosimple.R;
import com.liompei.toosimple.adapter.MoviesAdapter;
import com.liompei.toosimple.bean.MoviesBean;
import com.liompei.toosimple.utils.Z;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by BLM on 2016/7/11.
 */
public class Fragment_Movies extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    View view;
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<Map<String, String>> mapList;
    private MoviesAdapter moviesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movies, container, false);
        log("movies");
        initView();

        return view;
    }

    private void initView() {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setColorSchemeResources(android.R.color.holo_green_dark);
        swipe.setOnRefreshListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        mapList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(getActivity(), mapList);
        recyclerView.setAdapter(moviesAdapter);

        refresh();
    }


    private void getInterData() {
        BmobQuery<MoviesBean> moviesQuery = new BmobQuery<>();
        moviesQuery.order("-createdAt");
        moviesQuery.findObjects(new FindListener<MoviesBean>() {
            @Override
            public void done(List<MoviesBean> list, BmobException e) {
                if (e == null) {
                    mapList.clear();
                    for (MoviesBean movies : list) {
                        String id = movies.getObjectId();
                        String time = movies.getCreatedAt();
                        String image = movies.getImage();
                        String introduce = movies.getIntroduce();
                        String like = movies.getLike();
                        String dislike = movies.getDislike();
                        String num = movies.getNum();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("id", id);
                        map.put("time", time);
                        map.put("image", image);
                        map.put("introduce", introduce);
                        map.put("like", like);
                        map.put("dislike", dislike);
                        map.put("num", num);
                        mapList.add(map);
                    }
                    moviesAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);

                } else {
                    Z.show("查询失败");
                    swipe.setRefreshing(false);
                }
            }
        });
    }

    private void refresh(){
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                getInterData();
            }
        });
    }


    @Override
    public void onRefresh() {
       getInterData();
    }

    @Override
    public void onClick(View view) {

        MoviesBean moviesBean = new MoviesBean();
        moviesBean.setImage("http://i-3.yxdown.com/2015/12/4/a36e9cf5-657e-41e9-b225-04b7627cfa39.jpg");
        moviesBean.setIntroduce("这是小美");
        moviesBean.setLike("50");
        moviesBean.setDislike("2");
        moviesBean.setNum("33");
        moviesBean.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Z.show("发布成功");
                } else {
                    Z.show("发布失败");
                }
            }
        });


    }
}
