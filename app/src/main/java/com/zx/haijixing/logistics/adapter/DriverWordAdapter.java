package com.zx.haijixing.logistics.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.entry.DriverWordEntry;

import java.util.List;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/17 10:56
 *@描述 司机评价汇总
 */
public class DriverWordAdapter extends RecyclerView.Adapter<DriverWordAdapter.DriverWordsViewHolder> {

    private List<DriverWordEntry> driverWordEntries;
    private Context context;

    public DriverWordAdapter(List<DriverWordEntry> driverWordEntries) {
        this.driverWordEntries = driverWordEntries;
    }

    @NonNull
    @Override
    public DriverWordsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_driver_words_data, viewGroup, false);
        DriverWordsViewHolder driverWordsViewHolder = new DriverWordsViewHolder(inflate);
        return driverWordsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DriverWordsViewHolder driverWordsViewHolder, int i) {
        if (driverWordEntries.size()>0){
            DriverWordEntry driverWordEntry = driverWordEntries.get(i);
            driverWordsViewHolder.setData(driverWordEntry);
        }
    }

    @Override
    public int getItemCount() {
        return driverWordEntries.size();
    }

    class DriverWordsViewHolder extends RecyclerView.ViewHolder{

        ImageView head;
        TextView name,all,service,arrive,goods;
        public DriverWordsViewHolder(@NonNull View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.driver_words_data_head);
            name = itemView.findViewById(R.id.driver_words_data_name);
            all = itemView.findViewById(R.id.driver_words_data_all_score);
            service = itemView.findViewById(R.id.driver_words_data_service_manner);
            arrive = itemView.findViewById(R.id.driver_words_data_arrive_time);
            goods = itemView.findViewById(R.id.driver_words_data_goods_intact);
        }

        public void setData(DriverWordEntry driverWordEntry){
            RequestOptions options = new RequestOptions().circleCrop().error(R.mipmap.user_head);
            Glide.with(context).load(driverWordEntry.getHeadImg()).apply(options).into(head);
            name.setText(driverWordEntry.getDriverName());
            all.setText(driverWordEntry.getTotalScore()+"分");
            service.setText(driverWordEntry.getServiceScore()+"分");
            arrive.setText(driverWordEntry.getLogScore()+"分");
            goods.setText(driverWordEntry.getGoodsScore()+"分");
        }
    }
}
