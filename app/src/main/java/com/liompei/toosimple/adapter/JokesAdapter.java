package com.liompei.toosimple.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liompei.toosimple.R;
import com.liompei.toosimple.activity.CommentActivity;
import com.liompei.toosimple.bean.JokesBean;
import com.liompei.toosimple.utils.Z;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by BLM on 2016/7/14.
 */
public class JokesAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Map<String, String>> mapList;

    public JokesAdapter(Context context, List<Map<String, String>> mapList) {
        this.context = context;
        this.mapList = mapList;
    }

    //定义点击事件接口
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, String id);

    }


    private class MyHolder extends RecyclerView.ViewHolder {

        private TextView name, content, like, dislike, num;

        public MyHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.content);
            like = (TextView) itemView.findViewById(R.id.like);
            dislike = (TextView) itemView.findViewById(R.id.dislike);
            num = (TextView) itemView.findViewById(R.id.num);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClick(view, getLayoutPosition(), mapList.get(getLayoutPosition()).get("id"));
                    }
                }
            });

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int numLike = Integer.parseInt(mapList.get(getLayoutPosition()).get("like")) + 1;
                    JokesBean jokesBean = new JokesBean();
                    jokesBean.setLike(String.valueOf(numLike));
                    jokesBean.update(mapList.get(getLayoutPosition()).get("id"), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Z.log("点赞成功");
                                like.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
                                like.setText("OO " + String.valueOf(numLike));
                            } else {
                                Z.show("赞失败");
                            }
                        }
                    });
                }
            });

            dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int numDisLike = Integer.parseInt(mapList.get(getLayoutPosition()).get("dislike")) + 1;
                    JokesBean jokesBean = new JokesBean();
                    jokesBean.setDislike(String.valueOf(numDisLike));
                    jokesBean.update(mapList.get(getLayoutPosition()).get("id"), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Z.log("点赞成功");
                                dislike.setTextColor(context.getResources().getColor(android.R.color.holo_red_light));
                                dislike.setText("XX " + String.valueOf(numDisLike));
                            } else {
                                Z.show("赞失败");
                            }
                        }
                    });
                }
            });

            num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("style", "jokes");
                    intent.putExtra("id", mapList.get(getLayoutPosition()).get("id"));
                    context.startActivity(intent);
                }
            });


        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item_jokes, parent, false);
        MyHolder myHolder = new MyHolder(view);


        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.name.setText(mapList.get(position).get("name"));
        myHolder.content.setText(mapList.get(position).get("content"));
        myHolder.like.setText("OO " + mapList.get(position).get("like"));
        myHolder.dislike.setText("XX" + mapList.get(position).get("dislike"));
        myHolder.num.setText("吐槽");


    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }
}
