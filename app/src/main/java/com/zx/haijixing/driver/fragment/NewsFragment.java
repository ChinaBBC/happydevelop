package com.zx.haijixing.driver.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.NewsAdapter;
import com.zx.haijixing.driver.contract.NewsFragmentContract;
import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.driver.presenter.NewsFragmentImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;

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
    private Map<String, String> params = new HashMap<>();

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

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        String token = (String) instance.getParam("token","null");

        newsRvData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        newsAdapter = new NewsAdapter(newsData);
        newsRvData.setAdapter(newsAdapter);

        params.put("token",token);
        params.put(OtherConstants.PAGE, page+"");
        params.put(OtherConstants.SIZE, 5+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.newsFragmentMethod(params);

        Map<String,String> paramsBanner = new HashMap<>();
        paramsBanner.put("token",token);
        paramsBanner.put("timestamp",System.currentTimeMillis()+"");
        paramsBanner.put("sign","");
        paramsBanner.put("sign",HaiTool.sign(paramsBanner));
        mPresenter.newsFragmentBanner(paramsBanner);

        skeletonScreen = Skeleton.bind(newsRvData).adapter(newsAdapter)
                .shimmer(true)
                .load(R.layout.skeleton_index_data)
                .show();
        refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void newsFragmentSuccess(List<NewsEntry.NewsData> newsEntries, String base) {
        if (!isHide){
            isHide = true;
            skeletonScreen.hide();
        }

        newsAdapter.setBaseStr(base);
        if (newsEntries.size()>0){
            refresh.setNoMoreData(false);
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
        params.put(OtherConstants.PAGE, page+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.newsFragmentMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        newsData.clear();
        newsAdapter.notifyDataSetChanged();
        params.put(OtherConstants.PAGE, page+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.newsFragmentMethod(params);
    }

    @Override
    public void newsFragmentBannerSuccess(List<BannerEntry.BannerData> bannerDataList,String bannerStr) {
        newsAdapter.setBannerData(bannerDataList,bannerStr);
        newsAdapter.notifyDataSetChanged();
    }
}
