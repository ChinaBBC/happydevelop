package com.zx.haijixing.driver.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.util.BannerUtil;

import java.util.List;

import zx.com.skytool.ZxLogUtil;

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
    private List<NewsEntry.NewsData> newsData;
    private List<BannerEntry.BannerData> bannerData;
    private String bannerStr;
    private String baseStr ;
    private Context context;

    public IndexAdapter(List<NewsEntry.NewsData> newsData) {
        this.newsData = newsData;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setBannerData(List<BannerEntry.BannerData> bannerData,String bannerStr) {
        this.bannerData = bannerData;
        this.bannerStr = bannerStr;
    }

    public void setBaseStr(String baseStr) {
        this.baseStr = baseStr;
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
        context = parent.getContext();
        if (viewType == HEAD_ITEM){
            View head = LayoutInflater.from(context).inflate(R.layout.item_index_header, parent, false);
            viewHolder = new IndexHeadViewHolder(head);
        }else {
            View body = LayoutInflater.from(context).inflate(R.layout.item_index_data, parent, false);
            viewHolder = new IndexBodyViewHolder(body);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder indexViewHolder, int i) {
        if (getItemViewType(i) == HEAD_ITEM){
            IndexHeadViewHolder indexHeadViewHolder = (IndexHeadViewHolder) indexViewHolder;
            indexHeadViewHolder.receive.setOnClickListener(clickListener);
            indexHeadViewHolder.print.setOnClickListener(clickListener);
            indexHeadViewHolder.clock.setOnClickListener(clickListener);
            indexHeadViewHolder.services.setOnClickListener(clickListener);
            indexHeadViewHolder.notify.setOnClickListener(clickListener);
            if (bannerData != null && bannerData.size()>0)
                BannerUtil.initBannerScroll(indexHeadViewHolder.banner, bannerData,bannerStr, position -> {
                    BannerEntry.BannerData bannerData = this.bannerData.get(position);
                    ZxLogUtil.logError("<banner data>"+bannerData.toString());
                });

        }else {
            int realPosition = getRealPosition(indexViewHolder);
            NewsEntry.NewsData newsData = this.newsData.get(realPosition);
            IndexBodyViewHolder indexBodyViewHolder = (IndexBodyViewHolder) indexViewHolder;
            indexBodyViewHolder.time.setText(newsData.getCreateTime());
            indexBodyViewHolder.simple.setText(newsData.getFtitle());
            RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(baseStr+newsData.getCoverImg()).apply(options).into(indexBodyViewHolder.img);
            indexBodyViewHolder.item.setOnClickListener(view->ARouter.getInstance().build(RoutePathConstant.DRIVER_NEWS).withString("newId",newsData.getNewId()).navigation());
        }
    }

    @Override
    public int getItemCount() {
        return newsData.size()+1;
    }

    public int getRealPosition(RecyclerView.ViewHolder viewHolder){
        int p = viewHolder.getLayoutPosition();
        return p-1;
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
        ConstraintLayout item;
        public IndexBodyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.index_body_img);
            simple = itemView.findViewById(R.id.index_body_words);
            time = itemView.findViewById(R.id.index_body_time);
            item = itemView.findViewById(R.id.index_body_item);
        }
    }
}
