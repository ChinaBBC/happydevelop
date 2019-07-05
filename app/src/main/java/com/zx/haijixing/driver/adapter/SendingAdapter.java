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
 *@创建日期 2019/7/5 15:14
 *@描述 配送中
 */
public class SendingAdapter extends RecyclerView.Adapter<SendingAdapter.SendingViewHolder> {

    @NonNull
    @Override
    public SendingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sending_data, viewGroup, false);
        SendingViewHolder sendingViewHolder = new SendingViewHolder(inflate);
        return sendingViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SendingViewHolder sendingViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class SendingViewHolder extends RecyclerView.ViewHolder{

        public SendingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
