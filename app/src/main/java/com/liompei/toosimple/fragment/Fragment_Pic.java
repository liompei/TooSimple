package com.liompei.toosimple.fragment;

import android.app.AlertDialog;
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
import com.liompei.toosimple.adapter.PicAdapter;
import com.liompei.toosimple.bean.PicBean;
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
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;

/**
 * Created by BLM on 2016/7/11.
 */
public class Fragment_Pic extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    View view;
    private RecyclerView recyclerView;
    private List<Map<String, String>> mapList;
    private PicAdapter picAdapter;
    private SwipeRefreshLayout swipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pic, container, false);
        log("pic");
        x.view().inject(view);
        initView();
        fab();
        return view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mapList = new ArrayList<>();
        picAdapter = new PicAdapter(mapList, getActivity());
        recyclerView.setAdapter(new AlphaInAnimationAdapter(picAdapter));
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        swipe.setColorSchemeResources(android.R.color.holo_red_light);
        swipe.setOnRefreshListener(this);
        picAdapter.setListener(new PicAdapter.OnItemClickListener() {
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
    }

    //下载图片
    private void downloadfile(final String url) {
//        File file = new File("/sdcard/TooSimple/pic/" + url.lastIndexOf("/"));
        RequestParams params = new RequestParams(url);
        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/TooSimple/pic/"+url.substring(url.lastIndexOf("/")+1));
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
        BmobQuery<PicBean> beanBmobQuery = new BmobQuery<>();
        beanBmobQuery.order("-createdAt");
        beanBmobQuery.findObjects(new FindListener<PicBean>() {
            @Override
            public void done(List<PicBean> list, BmobException e) {
                if (e == null) {
                    mapList.clear();
                    for (PicBean picBean : list) {
                        String id = picBean.getObjectId();
                        String time = picBean.getCreatedAt();
                        String name = picBean.getName();
                        String img = picBean.getImg();
                        String like = picBean.getLike();
                        String dislike = picBean.getDislike();
                        String num = picBean.getNum();
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
                    picAdapter.notifyDataSetChanged();
                    swipe.setRefreshing(false);

                } else {
                    Z.show("查询失败");
                    Z.log(e.getMessage() + " ");
                    swipe.setRefreshing(false);
                }
            }
        });

    }

    public void refresh() {
        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                getInterData();
            }
        });
    }


    private void fab() {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PicBean picBean = new PicBean();
                picBean.setName("李狗蛋");
                picBean.setImg("http://joymepic.joyme.com/article/uploads/allimg/201508/1440394229221354.jpg");
                picBean.setLike("0");
                picBean.setDislike("0");
                picBean.setNum("0");
                picBean.save(new SaveListener<String>() {
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
