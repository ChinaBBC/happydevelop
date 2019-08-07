package com.zx.haijixing.logistics.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zx.haijixing.R;
import com.zx.haijixing.logistics.entry.RunTableEntry;

import java.util.List;

import zx.com.skytool.ZxStringUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/17 9:29
 *@描述 运营总表（物流中心、管理端）
 */
public class RunTableAdapter extends RecyclerView.Adapter<RunTableAdapter.RunTableViewHolder> {

    private String company;
    private List<RunTableEntry> runTableEntries;

    public RunTableAdapter(List<RunTableEntry> runTableEntries) {
        this.runTableEntries = runTableEntries;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @NonNull
    @Override
    public RunTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_run_table_data, viewGroup, false);
        RunTableViewHolder runTableViewHolder = new RunTableViewHolder(inflate);
        return runTableViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RunTableViewHolder runTableViewHolder, int i) {
        if (runTableEntries.size()>0){
            RunTableEntry runTableEntry = runTableEntries.get(i);
            runTableViewHolder.company.setText(ZxStringUtil.isEmpty(company)?"":company);
            runTableViewHolder.sendWay.setText(runTableEntry.getProductName());
            runTableViewHolder.bill.setText(runTableEntry.getWaybillCount()+"单");
            runTableViewHolder.fee.setText(runTableEntry.getSumrealPrice()+"元");
            runTableViewHolder.brokerage.setText(runTableEntry.getCommission()+"元");
        }
    }

    @Override
    public int getItemCount() {
        return runTableEntries.size();
    }

    class RunTableViewHolder extends RecyclerView.ViewHolder{

        TextView company,sendWay,bill,fee,brokerage;
        public RunTableViewHolder(@NonNull View itemView) {
            super(itemView);
            company = itemView.findViewById(R.id.run_table_data_company);
            sendWay = itemView.findViewById(R.id.run_table_data_sendWay);
            bill = itemView.findViewById(R.id.run_table_data_bill);
            fee = itemView.findViewById(R.id.run_table_data_fee);
            brokerage = itemView.findViewById(R.id.run_table_data_brokerage);
        }
    }
}
