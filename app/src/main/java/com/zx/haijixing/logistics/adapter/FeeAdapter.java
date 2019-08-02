package com.zx.haijixing.logistics.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.haijixing.R;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/8/2 14:03
 *@描述 规格价格
 */
public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.FeeViewHolder> {

    private List<String> data;
    private int number = 0;

    public FeeAdapter(List<String> data) {
        this.data = data;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @NonNull
    @Override
    public FeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_fee_data, viewGroup, false);
        FeeViewHolder feeViewHolder = new FeeViewHolder(inflate);
        return feeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeeViewHolder feeViewHolder, int i) {
        if (data.size()>0 && number>0){
            feeViewHolder.word.setText(data.get(i));
            if (i<number){
                feeViewHolder.word.setBackgroundResource(R.drawable.shape_0dp_a45b_ffff);
                feeViewHolder.word.setTextColor(Color.parseColor("#ffffff"));
            }else {
                feeViewHolder.word.setBackgroundResource(R.drawable.shape_0dp_ffff_a45b);
                feeViewHolder.word.setTextColor(Color.parseColor("#000000"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class FeeViewHolder extends RecyclerView.ViewHolder{
        TextView word;
        public FeeViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(R.id.fee_data_word);
        }


    }
}
