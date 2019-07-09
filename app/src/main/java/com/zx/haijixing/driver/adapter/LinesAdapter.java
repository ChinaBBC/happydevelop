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
 *@创建日期 2019/7/8 16:23
 *@描述 车辆路线
 */
public class LinesAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lines_data, viewGroup, false);
        LinesViewHolder linesViewHolder = new LinesViewHolder(inflate);
        return linesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 9;
    }

    class LinesViewHolder extends RecyclerView.ViewHolder{

        public LinesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
