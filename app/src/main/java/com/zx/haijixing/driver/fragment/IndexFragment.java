package com.zx.haijixing.driver.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.IndexAdapter;
import com.zx.haijixing.driver.contract.IIndexContract;
import com.zx.haijixing.driver.entry.BannerEntry;
import com.zx.haijixing.driver.entry.NewsEntry;
import com.zx.haijixing.driver.presenter.IndexImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 10:12
 *@描述 首页
 */
public class IndexFragment extends BaseFragment<IndexImp> implements IIndexContract.IndexView,OnRefreshLoadMoreListener {


    @BindView(R.id.index_one)
    LinearLayout one;
    @BindView(R.id.index_order_number)
    EditText orderNumber;
    @BindView(R.id.index_search)
    TextView search;
    @BindView(R.id.index_scan_code)
    ImageView scanCode;
    @BindView(R.id.index_rv_body)
    RecyclerView rvBody;
    @BindView(R.id.index_refresh)
    SmartRefreshLayout refresh;

    private IndexAdapter indexAdapter;
    private CommonDialogFragment showClock;
    private List<NewsEntry.NewsData> newsData = new ArrayList<>();
    private int page = 1;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    protected void initData() {
        mPresenter = new IndexImp();
    }

    @Override
    protected void initView(View view) {

        setTitleTopMargin(one,0);
        setTitleTopMargin(scanCode,20);

        rvBody.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        indexAdapter = new IndexAdapter(newsData);
        rvBody.setAdapter(indexAdapter);
        indexAdapter.setClickListener(this::onViewClicked);

        mPresenter.newsDataBanner();
        mPresenter.newsDataMethod(page);
        refresh.setOnRefreshLoadMoreListener(this);
        skeletonScreen = Skeleton.bind(rvBody).adapter(indexAdapter)
                .shimmer(true)
                .load(R.layout.skeleton_index_data)
                .show();
    }

    @OnClick({R.id.index_search, R.id.index_scan_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.index_search:
                ARouter.getInstance().build(PathConstant.DRIVER_SEARCH).navigation();
                break;
            case R.id.index_scan_code:
                scanQrCode();
                break;
            case R.id.index_header_clock:
                showClock = HaiDialogUtil.showClock(getFragmentManager(), this::onViewClicked);
                break;
            case R.id.dialog_clock_yes:
                showClock.dismissAllowingStateLoss();
                break;
            case R.id.dialog_clock_no:
                showClock.dismissAllowingStateLoss();
                break;
            case R.id.index_header_notify:
                ARouter.getInstance().build(PathConstant.DRIVER_NOTIFY).navigation();
                break;
            case R.id.index_header_services:
                ARouter.getInstance().build(PathConstant.DRIVER_SERVICES).navigation();
                break;
        }
    }

    @Override
    public void newsDataSuccess(List<NewsEntry.NewsData> newsEntries,String base) {
        if (!isHide){
            skeletonScreen.hide();
            isHide = true;
        }
        indexAdapter.setBaseStr(base);

        if (newsEntries.size()>0){
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
            newsData.addAll(newsEntries);
            indexAdapter.notifyDataSetChanged();
        }else {
            refresh.finishRefresh(true);
            refresh.finishLoadMoreWithNoMoreData();
        }

    }

    @Override
    public void newDataBannerSuccess(List<BannerEntry.BannerData> bannerDataList,String bannerStr) {
        indexAdapter.setBannerData(bannerDataList,bannerStr);
        indexAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page<1000)
            page++;
        mPresenter.newsDataMethod(page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        newsData.clear();
        mPresenter.newsDataMethod(page);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ZxLogUtil.logError("in the result");
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ZxLogUtil.logError("onActivityResult: " + result);
                    ZxToastUtil.centerToast("解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ZxToastUtil.centerToast("解析失败");
                }
            }
        }
    }

    /**
     * 扫描二维码
     */
    private void scanQrCode(){
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        //第二个参数是请求码，定义的常量随意填写
        startActivityForResult(intent, 123);

    }
}
