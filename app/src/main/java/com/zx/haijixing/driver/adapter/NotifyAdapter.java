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
 *@创建日期 2019/7/3 16:33
 *@描述 消息中心
 */
public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyViewHolder> {

    @NonNull
    @Override
    public NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notify_center, viewGroup, false);
        NotifyViewHolder notifyViewHolder = new NotifyViewHolder(inflate);
        return notifyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHolder notifyViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class NotifyViewHolder extends RecyclerView.ViewHolder{

        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
