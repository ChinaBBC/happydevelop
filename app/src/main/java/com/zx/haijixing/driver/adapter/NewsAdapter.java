package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.haijixing.R;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 18:47
 *@描述 新闻资讯
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEAD_ITEM = 0;
    private final static int BODY_ITEM = 1;
    private View.OnClickListener clickListener;

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD_ITEM;
        return BODY_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == HEAD_ITEM){
            View head = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_header, viewGroup, false);
            viewHolder = new NewsHeadViewHolder(head);
        }else {
            View body = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_data, viewGroup, false);
            viewHolder = new NewsViewHolder(body);
        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (i == HEAD_ITEM){

        }else {
            NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
            newsViewHolder.item.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout item;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.new_data_item);
        }
    }

    class NewsHeadViewHolder extends RecyclerView.ViewHolder{

        public NewsHeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
