package com.zx.haijixing.logistics.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zx.haijixing.R;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 11:42
 *@描述 待派单
 */
public class AllotAdapter extends RecyclerView.Adapter<AllotAdapter.AllotViewHolder> {

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AllotViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_wait_allot, viewGroup, false);
        AllotViewHolder allotViewHolder = new AllotViewHolder(inflate);
        return allotViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllotViewHolder allotViewHolder, int i) {
        allotViewHolder.button1.setOnClickListener(onClickListener);
        allotViewHolder.button2.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class AllotViewHolder extends RecyclerView.ViewHolder{
        Button button1,button2;
        public AllotViewHolder(@NonNull View itemView) {
            super(itemView);
            button1 = itemView.findViewById(R.id.allot_data_sure_allot);
            button2 = itemView.findViewById(R.id.allot_data_change_order);
        }
    }
}
