package com.zx.haijixing.driver.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.youth.banner.Banner;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.NewsAdapter;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseFragment;

import butterknife.BindView;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 18:49
 *@描述 新闻资讯
 */
public class NewsFragment extends BaseFragment {
    @BindView(R.id.news_rv_data)
    RecyclerView newsRvData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView(View view) {
        newsRvData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        NewsAdapter newsAdapter = new NewsAdapter();
        newsRvData.setAdapter(newsAdapter);
        newsAdapter.setClickListener(view1 -> ARouter.getInstance().build(RoutePathConstant.DRIVER_NEWS).navigation());
    }
}
