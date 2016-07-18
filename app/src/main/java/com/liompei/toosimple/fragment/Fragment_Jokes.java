package com.liompei.toosimple.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liompei.toosimple.BaseFragment;
import com.liompei.toosimple.R;
import com.liompei.toosimple.adapter.JokesAdapter;
import com.liompei.toosimple.bean.JokesBean;
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
public class Fragment_Jokes extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    View view;
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<Map<String, String>> mapList;
    private JokesAdapter jokesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jokes, container, false);
        log("jokes");
        initView();

        return view;
    }

    private void initView() {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setColorSchemeResources(android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        mapList = new ArrayList<>();
        jokesAdapter = new JokesAdapter(getActivity(), mapList);
        recyclerView.setAdapter(jokesAdapter);

        jokesAdapter.setListener(new JokesAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, String id) {

            }
        });

        refresh();

    }

    private void getInterData() {
        BmobQuery<JokesBean> jokesBeanBmobQuery = new BmobQuery<>();
        jokesBeanBmobQuery.order("-createdAt");
        jokesBeanBmobQuery.findObjects(new FindListener<JokesBean>() {
            @Override
            public void done(List<JokesBean> list, BmobException e) {
                if (e == null) {
                    mapList.clear();
                    for (JokesBean jokesBean : list) {
                        String id = jokesBean.getObjectId();
                        String time = jokesBean.getCreatedAt();
                        String name = jokesBean.getName();
                        String content = jokesBean.getContent();
                        String like = jokesBean.getLike();
                        String dislike = jokesBean.getDislike();
                        String num = jokesBean.getNum();

                        Map<String, String> map = new HashMap<String, String>();
                        map.put("id", id);
                        map.put("time", time);
                        map.put("name", name);
                        map.put("content", content);
                        map.put("like", like);
                        map.put("dislike", dislike);
                        map.put("num", num);
                        mapList.add(map);
                    }
                    jokesAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);
                } else {

                    Z.show("查询失败");
                    Z.log(e.getMessage() + " ");
                    swipe.setRefreshing(false);

                }
            }
        });


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
    public void onClick(View view) {
        JokesBean jokesBean = new JokesBean();
        jokesBean.setName("刘斩仙");
        jokesBean.setContent("我上班忙着工作，下班忙着休息，闲的时候才急忙想：我该怎样生活？");
        jokesBean.setLike("10");
        jokesBean.setDislike("20");
        jokesBean.setNum("5");
        jokesBean.save(new SaveListener<String>() {
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

    @Override
    public void onRefresh() {
        getInterData();
    }
}
