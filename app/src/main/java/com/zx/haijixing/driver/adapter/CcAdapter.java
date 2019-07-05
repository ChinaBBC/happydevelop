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
 *@创建日期 2019/7/5 15:25
 *@描述 已完成和已取消
 */
public class CcAdapter extends RecyclerView.Adapter<CcAdapter.CcViewHolder> {

    @NonNull
    @Override
    public CcViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_complete_cancel_data, viewGroup, false);
        CcViewHolder ccViewHolder = new CcViewHolder(inflate);
        return ccViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CcViewHolder ccViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class CcViewHolder extends RecyclerView.ViewHolder{

        public CcViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
