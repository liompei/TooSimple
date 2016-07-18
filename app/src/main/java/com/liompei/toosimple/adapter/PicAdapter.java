package com.liompei.toosimple.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liompei.toosimple.R;
import com.liompei.toosimple.activity.CommentActivity;
import com.liompei.toosimple.bean.PicBean;
import com.liompei.toosimple.utils.Z;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by BLM on 2016/7/13.
 */
public class PicAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Map<String, String>> mapList;


    public PicAdapter(List<Map<String, String>> mapList, Context context) {
        this.mapList = mapList;
        this.context = context;
    }

    //定义点击事件接口
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, String id, String img);

    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_pic, parent, false);


        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;

        Picasso.with(context).load(mapList.get(position).get("img"))  //实际上,有一个img
                .into(myHolder.image);
        myHolder.mName.setText(mapList.get(position).get("name"));
        myHolder.mLike.setText("OO " + mapList.get(position).get("like"));
        myHolder.mDislike.setText("XX " + mapList.get(position).get("dislike"));
//        myHolder.mNum.setText("吐槽 "+mapList.get(position).get("num"));
        myHolder.mNum.setText("吐槽");

    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }


    private class MyHolder extends RecyclerView.ViewHolder {
        private ImageView image;  //图片
        private TextView mName, mLike, mDislike, mNum;  //喜欢,不喜欢.评论数量

        public MyHolder(View itemView) {
            super(itemView);

            mName = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
            mLike = (TextView) itemView.findViewById(R.id.like);
            mDislike = (TextView) itemView.findViewById(R.id.dislike);
            mNum = (TextView) itemView.findViewById(R.id.num);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(view, getLayoutPosition(), mapList.get(getLayoutPosition()).get("id"), mapList.get(getLayoutPosition()).get("img"));
                    }
                }
            });

            mLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final int numLike = Integer.parseInt(mapList.get(getLayoutPosition()).get("like")) + 1;
                    PicBean picBean = new PicBean();
                    picBean.setLike(String.valueOf(numLike));
                    picBean.update(mapList.get(getLayoutPosition()).get("id"), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Z.log("点赞成功");
                                mLike.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
                                mLike.setText("OO " + String.valueOf(numLike));
                            } else {
                                Z.show("点赞失败");
                            }
                        }
                    });

                }
            });

            mDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int numDisLike = Integer.parseInt(mapList.get(getLayoutPosition()).get("dislike")) + 1;
                    PicBean picBean = new PicBean();
                    picBean.setDislike(String.valueOf(numDisLike));
                    picBean.update(mapList.get(getLayoutPosition()).get("id"), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Z.log("点赞成功");
                                mDislike.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
                                mDislike.setText("XX " + String.valueOf(numDisLike));
                            } else {
                                Z.show("点赞失败");
                            }
                        }
                    });
                }
            });
            mNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, CommentActivity.class);
                    intent.putExtra("style", "pic");
                    intent.putExtra("id", mapList.get(getLayoutPosition()).get("id"));
                    context.startActivity(intent);
                }
            });


        }
    }


}
