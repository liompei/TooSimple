package com.liompei.toosimple.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liompei.toosimple.R;

import java.util.List;
import java.util.Map;

/**
 * Created by BLM on 2016/7/12.
 */
public class NewsAdapter extends RecyclerView.Adapter {


    private List<Map<String, String>> mapList;
    private Context context;

    public NewsAdapter(Context context, List<Map<String, String>> mapList) {
        this.context = context;
        this.mapList = mapList;
    }


    //定义点击事件接口
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position, String id, String title);

    }


    private class MyHolder extends RecyclerView.ViewHolder {

        private TextView t1, t2;
        private CardView cardView;

        public MyHolder(final View itemView) {
            super(itemView);
            t1 = (TextView) itemView.findViewById(R.id.news_title);
            t2 = (TextView) itemView.findViewById(R.id.news_user);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listener != null) {
                        listener.onClick(view, getLayoutPosition(), mapList.get(getLayoutPosition()).get("newsId"), mapList.get(getLayoutPosition()).get("newsTitle"));
                    }

                }
            });


//            t1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    Toast.makeText(context, getLayoutPosition()+"", Toast.LENGTH_SHORT).show();
//                }
//            });

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_item_news, parent, false);
        MyHolder myHolder = new MyHolder(view);


        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;

        myHolder.t1.setText(mapList.get(position).get("newsTitle"));
        myHolder.t2.setText(mapList.get(position).get("senderusername"));

    }

    @Override
    public int getItemCount() {
        return mapList.size();
    }
}
