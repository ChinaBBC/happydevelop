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
import com.zx.haijixing.R;
import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.util.BannerUtil;

import java.util.List;

import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 18:47
 *@描述 新闻资讯
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int HEAD_ITEM = 0;
    private final static int BODY_ITEM = 1;
    private List<NewsEntry.NewsData> newsDataList;
    private List<BannerEntry.BannerData> bannerData;
    private String bannerStr;
    private String baseStr;
    private Context context;

    public NewsAdapter(List<NewsEntry.NewsData> newsDataList) {
        this.newsDataList = newsDataList;
    }

    public void setBaseStr(String baseStr) {
        this.baseStr = baseStr;
    }
    public void setBannerData(List<BannerEntry.BannerData> bannerData,String bannerStr) {
        this.bannerData = bannerData;
        this.bannerStr = bannerStr;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD_ITEM;
        return BODY_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        context = viewGroup.getContext();
        if (getItemViewType(viewType) == HEAD_ITEM){
            View head = LayoutInflater.from(context).inflate(R.layout.item_news_header, viewGroup, false);
            viewHolder = new NewsHeadViewHolder(head);
        }else {
            View body = LayoutInflater.from(context).inflate(R.layout.item_news_data, viewGroup, false);
            viewHolder = new NewsViewHolder(body);
        }
        return viewHolder;

    }
    public int getRealPosition(RecyclerView.ViewHolder viewHolder){
        int p = viewHolder.getLayoutPosition();
        return p-1;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == HEAD_ITEM){
            NewsHeadViewHolder newsHeadViewHolder = (NewsHeadViewHolder) viewHolder;
            if (bannerData != null && bannerData.size()>0)
                BannerUtil.initBannerScroll(newsHeadViewHolder.banner, bannerData,bannerStr, position -> {
                BannerEntry.BannerData bannerData = this.bannerData.get(position);
                ZxLogUtil.logError("<banner data>"+bannerData.toString());
                });
        }else {
            int realPosition = getRealPosition(viewHolder);
            NewsEntry.NewsData newsData = newsDataList.get(realPosition);
            NewsViewHolder newsViewHolder = (NewsViewHolder) viewHolder;
            newsViewHolder.item.setOnClickListener(view1 -> ARouter.getInstance().build(PathConstant.DRIVER_NEWS)
                    .withString("newId",newsData.getNewId())
                    .withString("from","news")
                    .navigation());
            newsViewHolder.title.setText(newsData.getTitle());
            newsViewHolder.sample.setText(newsData.getFtitle());
            newsViewHolder.time.setText(newsData.getCreateTime());
            RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(baseStr+newsData.getCoverImg()).apply(options).into(newsViewHolder.back);
        }
    }

    @Override
    public int getItemCount() {
        return newsDataList.size()+1;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout item;
        ImageView back,share;
        TextView title,sample,time;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.new_data_item);
            share = itemView.findViewById(R.id.new_data_share);
            back = itemView.findViewById(R.id.news_data_image);
            title = itemView.findViewById(R.id.news_data_title);
            sample = itemView.findViewById(R.id.news_data_content);
            time = itemView.findViewById(R.id.news_Data_time);

        }
    }

    class NewsHeadViewHolder extends RecyclerView.ViewHolder{
        Banner banner;
        public NewsHeadViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.news_banner);
        }
    }
}
