package com.zx.haijixing.driver.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.CcAdapter;
import com.zx.haijixing.driver.contract.CompleteContract;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.driver.presenter.CompleteImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/5 11:30
 *@描述 已完成
 */
public class CompleteFragment extends BaseFragment<CompleteImp> implements CompleteContract.CompleteView,OnRefreshLoadMoreListener {
    @BindView(R.id.fragment_complete_data)
    RecyclerView completeData;
    @BindView(R.id.fragment_complete_refresh)
    SmartRefreshLayout refresh;
    private Map<String, Object> params = new HashMap<>();
    private List<OrderTotalEntry.OrderEntry> orderEntries = new ArrayList<>();
    private CcAdapter ccAdapter;
    private String token;
    private int page = 1;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_complete;
    }

    @Override
    protected void initData() {
        mPresenter = new CompleteImp();
    }

    @Override
    protected void initView(View view) {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        token = (String) instance.getParam("token", "token");

        completeData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        ccAdapter = new CcAdapter(orderEntries);
        completeData.setAdapter(ccAdapter);

        params.put("token", token);
        params.put("status", OtherConstants.DETAIL_COMPLETE);
        //params.put("params")
        params.put("pageNum", page);
        params.put("pageSize", 5);

        mPresenter.completeMethod(params);
        refresh.setOnRefreshLoadMoreListener(this);

        skeletonScreen = Skeleton.bind(completeData).adapter(ccAdapter)
                .shimmer(true)
                .load(R.layout.skeleton_order_center)
                .show();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        params.put("pageNum", page);
        mPresenter.completeMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        orderEntries.clear();
        params.put("pageNum", page);
        mPresenter.completeMethod(params);
    }

    @Override
    public void completeSuccess(OrderTotalEntry orderTotalEntry) {
        if (!isHide){
            skeletonScreen.hide();
            isHide = true;
        }
        List<OrderTotalEntry.OrderEntry> rows = orderTotalEntry.getRows();
        if (rows.size() == 0) {
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        } else {
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
        }

        orderEntries.addAll(orderTotalEntry.getRows());
        ccAdapter.notifyDataSetChanged();
        if (orderEntries.size() == 0)
            ZxToastUtil.centerToast("没有已完成的订单");
    }
}
