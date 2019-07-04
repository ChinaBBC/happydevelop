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
 *@创建日期 2019/7/4 10:28
 *@描述 订单
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order_data, viewGroup, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(inflate);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
