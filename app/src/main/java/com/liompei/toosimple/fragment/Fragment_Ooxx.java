package com.liompei.toosimple.fragment;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.liompei.toosimple.BaseFragment;
import com.liompei.toosimple.R;
import com.liompei.toosimple.adapter.OoxxAdapter;
import com.liompei.toosimple.bean.OoxxBean;
import com.liompei.toosimple.utils.Z;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
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
public class Fragment_Ooxx extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    private SwipeRefreshLayout swipe;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<Map<String, String>> mapList;
    private OoxxAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ooxx, container, false);
        log("ooxx");
        initView();


        return view;
    }

    private void initView() {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light);
        swipe.setOnRefreshListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        mapList = new ArrayList<>();
        adapter = new OoxxAdapter(getActivity(), mapList);
        recyclerView.setAdapter(adapter);



        adapter.setListener(new OoxxAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, String id, final String img) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view1 = layoutInflater.inflate(R.layout.dialog_showimg, null);
                ImageView imageView = (ImageView) view1.findViewById(R.id.image);
                FloatingActionButton floatingActionButton = (FloatingActionButton) view1.findViewById(R.id.fab);
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //保存图片
                        downloadfile(img);
                        Z.log("准备下载");
                    }
                });
                Picasso.with(getActivity()).load(img).into(imageView);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view1);
                builder.show();
            }
        });

        refresh();
        fab();
    }


    //下载图片
    private void downloadfile(final String url) {
//        File file = new File("/sdcard/TooSimple/pic/" + url.lastIndexOf("/"));
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/TooSimple/ooxx/"+url.substring(url.lastIndexOf("/")+1));
        params.setAutoRename(true);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                Z.show("下载完成");
                //apk下载完成后，调用系统的安装方法


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Z.show("下载失败");
                Z.log(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            //网络请求之前回调
            @Override
            public void onWaiting() {

            }

            //网络请求开始的时候回调
            @Override
            public void onStarted() {

            }

            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                Log.i("JAVA", "current：" + current + "，total：" + total);
            }
        });

    }



    private void getInterData() {
        BmobQuery<OoxxBean> ooxxBeanBmobQuery = new BmobQuery<>();
        ooxxBeanBmobQuery.order("-createdAt");
        ooxxBeanBmobQuery.findObjects(new FindListener<OoxxBean>() {
            @Override
            public void done(List<OoxxBean> list, BmobException e) {
                if (e == null) {
                    mapList.clear();
                    for (OoxxBean ooxxBean : list) {
                        String id = ooxxBean.getObjectId();
                        String time = ooxxBean.getCreatedAt();
                        String name = ooxxBean.getName();
                        String img = ooxxBean.getImg();
                        String like = ooxxBean.getLike();
                        String dislike = ooxxBean.getDislike();
                        String num = ooxxBean.getNum();
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("id", id);
                        map.put("time", time);
                        map.put("name", name);
                        map.put("img", img);
                        map.put("like", like);
                        map.put("dislike", dislike);
                        map.put("num", num);
                        mapList.add(map);
                    }
                    adapter.notifyDataSetChanged();
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


    private void fab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OoxxBean ooxxBean = new OoxxBean();
                ooxxBean.setName("赵日天");
                ooxxBean.setImg("http://pic.962.net/up/2016-6/14647429438214689.jpg");
                ooxxBean.setLike("0");
                ooxxBean.setDislike("0");
                ooxxBean.setNum("0");
                ooxxBean.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {

                            Z.show("上传成功");
                        } else {
                            Z.show("长传失败");

                        }
                    }
                });
            }
        });


    }


    @Override
    public void onRefresh() {
        getInterData();
    }
}
