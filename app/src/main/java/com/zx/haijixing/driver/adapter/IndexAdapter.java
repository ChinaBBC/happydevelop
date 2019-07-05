package com.zx.haijixing.driver.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.zx.haijixing.R;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 10:13
 *@描述 首页
 */
public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEAD_ITEM = 0;
    private final static int BODY_ITEM = 1;
    private View.OnClickListener clickListener;


    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD_ITEM;
        return BODY_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == HEAD_ITEM){
            View head = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_header, parent, false);
            viewHolder = new IndexHeadViewHolder(head);
        }else {
            View body = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_data, parent, false);
            viewHolder = new IndexBodyViewHolder(body);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder indexViewHolder, int i) {
        if (i == HEAD_ITEM){
            IndexHeadViewHolder indexHeadViewHolder = (IndexHeadViewHolder) indexViewHolder;
            indexHeadViewHolder.receive.setOnClickListener(clickListener);
            indexHeadViewHolder.print.setOnClickListener(clickListener);
            indexHeadViewHolder.clock.setOnClickListener(clickListener);
            indexHeadViewHolder.services.setOnClickListener(clickListener);
            indexHeadViewHolder.notify.setOnClickListener(clickListener);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class IndexHeadViewHolder extends RecyclerView.ViewHolder{
        ImageView receive,print,clock,services;
        Banner banner;
        TextView city,weather,temperature;
        TextView notify;
        public IndexHeadViewHolder(@NonNull View itemView) {
            super(itemView);
            receive = itemView.findViewById(R.id.index_header_receive);
            print = itemView.findViewById(R.id.index_header_print);
            clock = itemView.findViewById(R.id.index_header_clock);
            services = itemView.findViewById(R.id.index_header_services);
            banner = itemView.findViewById(R.id.index_header_banner);
            city = itemView.findViewById(R.id.index_header_city);
            weather = itemView.findViewById(R.id.index_header_weather);
            temperature = itemView.findViewById(R.id.index_header_temperature);
            notify = itemView.findViewById(R.id.index_header_notify);
        }

    }
    class IndexBodyViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView simple,time;
        public IndexBodyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.index_body_img);
            simple = itemView.findViewById(R.id.index_body_words);
            time = itemView.findViewById(R.id.index_body_time);
        }
    }
}
