package com.zx.haijixing.logistics.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zx.haijixing.R;
/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 16:16
 *@描述 路线管理
 */
public class LinesManageAdapter extends RecyclerView.Adapter<LinesManageAdapter.LinesManageViewHolder> {

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public LinesManageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lines_manage_data, viewGroup, false);
        LinesManageViewHolder linesManageViewHolder = new LinesManageViewHolder(inflate);
        return linesManageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinesManageViewHolder linesManageViewHolder, int i) {
        linesManageViewHolder.manage.setOnClickListener(onClickListener);
        linesManageViewHolder.fee.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class LinesManageViewHolder extends RecyclerView.ViewHolder{
        LinearLayout fee,manage;
        public LinesManageViewHolder(@NonNull View itemView) {
            super(itemView);
            fee = itemView.findViewById(R.id.lines_manage_fee);
            manage = itemView.findViewById(R.id.lines_manage_manage);
        }
    }
}
