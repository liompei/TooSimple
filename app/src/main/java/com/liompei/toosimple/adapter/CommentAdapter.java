package com.liompei.toosimple.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liompei.toosimple.R;
import com.liompei.toosimple.utils.Z;

import java.util.List;
import java.util.Map;

/**
 * Created by BLM on 2016/7/14.
 */
public class CommentAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Map<String, String>> mapList;

    public CommentAdapter(Context context, List<Map<String, String>> mapList) {
        this.context = context;
        this.mapList = mapList;
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView name, time, content;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            time = (TextView) itemView.findViewById(R.id.time);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_item_comment, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.name.setText(mapList.get(position).get("name"));
        myViewHolder.time.setText(mapList.get(position).get("time"));
        myViewHolder.content.setText(mapList.get(position).get("content"));

    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }
}
