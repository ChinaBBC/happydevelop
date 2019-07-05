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
 *@创建日期 2019/7/5 14:45
 *@描述 待出发
 */
public class SendAdapter extends RecyclerView.Adapter<SendAdapter.SendViewHolder> {

    @NonNull
    @Override
    public SendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_send_data, viewGroup, false);
        SendViewHolder sendViewHolder = new SendViewHolder(inflate);
        return sendViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SendViewHolder sendViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class SendViewHolder extends RecyclerView.ViewHolder{

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
