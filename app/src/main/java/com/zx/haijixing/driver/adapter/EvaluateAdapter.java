package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.haijixing.R;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/8 11:48
 *@描述 评价
 */
public class EvaluateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEAD_ITEM = 0;
    private final static int BODY_ITEM = 1;


    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD_ITEM;
        return BODY_ITEM;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder = null;
        if (i == HEAD_ITEM){
            View head = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_evaluate_header, viewGroup, false);
            viewHolder = new EvaluateHeadViewHolder(head);
        }else {
            View body = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_evaluate_data, viewGroup, false);
            viewHolder = new EvaluateViewHolder(body);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class EvaluateViewHolder extends RecyclerView.ViewHolder{

        public EvaluateViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class EvaluateHeadViewHolder extends RecyclerView.ViewHolder{

        public EvaluateHeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
