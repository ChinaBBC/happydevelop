package com.zx.haijixing.logistics.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.LinesManageAdapter;
import com.zx.haijixing.logistics.contract.LinesMangeContract;
import com.zx.haijixing.logistics.entry.LinesManageEntry;
import com.zx.haijixing.logistics.presenter.LinesManageImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 14:23
 *@描述 路线管理
 */
public class LinesManageFragment extends BaseFragment<LinesManageImp> implements LinesMangeContract.LinesManageView,OnRefreshLoadMoreListener {
    @BindView(R.id.fragment_cancel_data)
    RecyclerView lineData;
    @BindView(R.id.fragment_cancel_refresh)
    SmartRefreshLayout refresh;

    private LinesManageAdapter linesManageAdapter;
    private List<LinesManageEntry> linesManageEntries = new ArrayList<>();
    private Map<String,Object> params = new HashMap<>();
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cancel;
    }

    @Override
    protected void initData() {
        mPresenter = new LinesManageImp();
    }

    @Override
    protected void initView(View view) {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        String token = (String) instance.getParam("token", "null");

        lineData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        linesManageAdapter = new LinesManageAdapter(linesManageEntries);
        lineData.setAdapter(linesManageAdapter);

        refresh.setOnRefreshLoadMoreListener(this);

        params.put("token",token);
        params.put(OtherConstants.PAGE,page);
        params.put(OtherConstants.SIZE,5);

        mPresenter.linesManageMethod(params);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        params.put(OtherConstants.PAGE, page);
        mPresenter.linesManageMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh.setNoMoreData(false);
        page = 1;
        linesManageEntries.clear();
        linesManageAdapter.notifyDataSetChanged();
        params.put(OtherConstants.PAGE, page);
        mPresenter.linesManageMethod(params);
    }

    @Override
    public void linesManageSuccess(List<LinesManageEntry> linesManageEntries) {
        if (linesManageEntries.size() == 0) {
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        } else {
            refresh.setNoMoreData(false);
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
        }
        this.linesManageEntries.addAll(linesManageEntries);
        linesManageAdapter.notifyDataSetChanged();
        if (this.linesManageEntries.size() == 0)
            ZxToastUtil.centerToast("暂无可管理线路");
    }
}
