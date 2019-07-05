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
 *@创建日期 2019/7/5 17:37
 *@描述 打印订单
 */
public class PrintAdapter extends RecyclerView.Adapter {
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == HEAD_ITEM){
            View head = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_print_header, parent, false);
            viewHolder = new PrintHeadViewHolder(head);
        }else {
            View body = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_print_data, parent, false);
            viewHolder = new PrintViewHolder(body);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class PrintViewHolder extends RecyclerView.ViewHolder{

        public PrintViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    class PrintHeadViewHolder extends RecyclerView.ViewHolder{

        public PrintHeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
