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
 *@创建日期 2019/7/5 14:26
 *@描述 待接单
 */
public class ReceiveAdapter extends RecyclerView.Adapter<ReceiveAdapter.ReceiveViewHolder> {

    @NonNull
    @Override
    public ReceiveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_receive_data, viewGroup, false);
        ReceiveViewHolder receiveViewHolder = new ReceiveViewHolder(inflate);
        return receiveViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiveViewHolder receiveViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ReceiveViewHolder extends RecyclerView.ViewHolder{

        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
