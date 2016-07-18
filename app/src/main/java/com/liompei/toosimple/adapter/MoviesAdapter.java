package com.liompei.toosimple.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liompei.toosimple.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by BLM on 2016/7/14.
 */
public class MoviesAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Map<String, String>> mapList;

    public MoviesAdapter(Context context, List<Map<String, String>> mapList) {
        this.context = context;
        this.mapList = mapList;
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView introduce, like, dislike, num;

        public MyHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            introduce = (TextView) itemView.findViewById(R.id.introduce);
            like = (TextView) itemView.findViewById(R.id.like);
            dislike = (TextView) itemView.findViewById(R.id.dislike);
            num = (TextView) itemView.findViewById(R.id.num);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_movies, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        Picasso.with(context).load(mapList.get(position).get("image"))
                .into(myHolder.image);
        myHolder.introduce.setText(mapList.get(position).get("introduce"));
        myHolder.like.setText(mapList.get(position).get("like"));
        myHolder.dislike.setText(mapList.get(position).get("dislike"));
        myHolder.num.setText(mapList.get(position).get("num"));

    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }
}
