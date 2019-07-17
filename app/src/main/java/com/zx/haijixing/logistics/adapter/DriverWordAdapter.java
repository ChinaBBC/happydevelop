package com.zx.haijixing.logistics.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zx.haijixing.R;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/17 10:56
 *@描述 司机评价汇总
 */
public class DriverWordAdapter extends RecyclerView.Adapter<DriverWordAdapter.DriverWordsViewHolder> {

    @NonNull
    @Override
    public DriverWordsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_driver_words_data, viewGroup, false);
        DriverWordsViewHolder driverWordsViewHolder = new DriverWordsViewHolder(inflate);
        return driverWordsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DriverWordsViewHolder driverWordsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class DriverWordsViewHolder extends RecyclerView.ViewHolder{

        public DriverWordsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
