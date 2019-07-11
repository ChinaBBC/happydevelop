package com.zx.haijixing.driver.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.NewsAdapter;
import com.zx.haijixing.driver.contract.NewsFragmentContract;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.driver.presenter.NewsFragmentImp;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zx.com.skytool.ZxLogUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 18:49
 *@描述 新闻资讯
 */
public class NewsFragment extends BaseFragment<NewsFragmentImp> implements NewsFragmentContract.NewsFragmentView,OnRefreshLoadMoreListener {
    @BindView(R.id.news_rv_data)
    RecyclerView newsRvData;
    @BindView(R.id.news_title)
    TextView title;
    @BindView(R.id.news_refresh)
    SmartRefreshLayout refresh;

    private List<NewsEntry.NewsData> newsData = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private int page = 1;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initData() {
        mPresenter = new NewsFragmentImp();
    }

    @Override
    protected void initView(View view) {
        setTitleTopMargin(title,0);
        newsRvData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        newsAdapter = new NewsAdapter(newsData);
        newsRvData.setAdapter(newsAdapter);
        mPresenter.NewsFragmentMethod(page);

        skeletonScreen = Skeleton.bind(newsRvData).adapter(newsAdapter)
                .shimmer(true)
                .load(R.layout.skeleton_index_data)
                .show();
        refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void NewsFragmentSuccess(List<NewsEntry.NewsData> newsEntries, String base) {
        ZxLogUtil.logError("<<news>>"+newsEntries.toString());
        if (!isHide){
            isHide = true;
            skeletonScreen.hide();
        }

        newsAdapter.setBaseStr(base);
        if (newsEntries.size()>0){
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
            newsData.addAll(newsEntries);
            newsAdapter.notifyDataSetChanged();
        }else {
            refresh.finishRefresh(true);
            refresh.finishLoadMoreWithNoMoreData();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page<1000)
            page++;
        mPresenter.NewsFragmentMethod(page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        newsData.clear();
        mPresenter.NewsFragmentMethod(page);
    }
}
