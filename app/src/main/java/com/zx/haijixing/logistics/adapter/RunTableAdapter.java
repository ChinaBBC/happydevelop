package com.zx.haijixing.logistics.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.haijixing.R;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/17 9:29
 *@描述 运营总表（物流中心、管理端）
 */
public class RunTableAdapter extends RecyclerView.Adapter<RunTableAdapter.RunTableViewHolder> {

    private int loginType = 0;//0是物流端 1是管理端
    @NonNull
    @Override
    public RunTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_run_table_data, viewGroup, false);
        RunTableViewHolder runTableViewHolder = new RunTableViewHolder(inflate);
        return runTableViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RunTableViewHolder runTableViewHolder, int i) {
        if (loginType == 0)
            runTableViewHolder.company.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class RunTableViewHolder extends RecyclerView.ViewHolder{

        TextView company;
        public RunTableViewHolder(@NonNull View itemView) {
            super(itemView);
            company = itemView.findViewById(R.id.run_table_data_company);
        }
    }
}
