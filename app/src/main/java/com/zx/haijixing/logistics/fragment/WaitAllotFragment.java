package com.zx.haijixing.logistics.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.ReceiveAdapter;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.logistics.adapter.AllotAdapter;
import com.zx.haijixing.logistics.contract.WaitAllotContract;
import com.zx.haijixing.logistics.presenter.WaitAllotImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 10:59
 *@描述 待派单
 */
public class WaitAllotFragment extends BaseFragment<WaitAllotImp> implements WaitAllotContract.WaitAllotView,OnRefreshLoadMoreListener {
    @BindView(R.id.fragment_receive_data)
    RecyclerView rvData;
    @BindView(R.id.fragment_receive_lay1)
    ViewStub viewStub;
    @BindView(R.id.fragment_receive_refresh)
    SmartRefreshLayout refresh;

    private AllotAdapter allotAdapter;

    private Map<String, Object> params = new HashMap<>();
    private List<OrderTotalEntry.OrderEntry> orderEntries = new ArrayList<>();
    private String loginType;
    private String token;
    private int page = 1;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_receive;
    }

    @Override
    protected void initData() {
        mPresenter = new WaitAllotImp();
    }

    @Override
    protected void initView(View view) {

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        token = (String) instance.getParam("token", "null");
        loginType = (String) instance.getParam("login_type", "4");
        ZxLogUtil.logError("<<<<<" + token);

        rvData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        allotAdapter = new AllotAdapter(orderEntries);
        rvData.setAdapter(allotAdapter);

        params.put("token", token);
        params.put("status", OtherConstants.DETAIL_WAIT_SEND);
        //params.put("params")
        params.put(OtherConstants.PAGE, page);
        params.put(OtherConstants.SIZE, 5);

        mPresenter.waitAllotMethod(params);

        refresh.setOnRefreshLoadMoreListener(this);

        skeletonScreen = Skeleton.bind(rvData).adapter(allotAdapter)
                .shimmer(true)
                .load(R.layout.skeleton_order_center)
                .show();
    }

    @OnClick()
    public void onViewClicked(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void waitAllotSuccess(OrderTotalEntry orderTotalEntry) {
        if (!isHide){
            skeletonScreen.hide();
            isHide = false;
        }
        List<OrderTotalEntry.OrderEntry> rows = orderTotalEntry.getRows();
        if (rows.size() == 0) {
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        } else {
            refresh.setNoMoreData(false);
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
        }
        orderEntries.addAll(orderTotalEntry.getRows());
        allotAdapter.notifyDataSetChanged();
        if (orderEntries.size() == 0)
            ZxToastUtil.centerToast("没有待派单的订单");
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        params.put(OtherConstants.PAGE, page);
        mPresenter.waitAllotMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh.setNoMoreData(false);
        page = 1;
        orderEntries.clear();
        allotAdapter.notifyDataSetChanged();
        params.put(OtherConstants.PAGE, page);
        mPresenter.waitAllotMethod(params);
    }

    class AllotViewHolder{
        @BindView(R.id.fragment_receive_selectAll)
        ImageView selectAll;
        @BindView(R.id.fragment_receive_word1)
        TextView word1;
        @BindView(R.id.fragment_receive_total)
        TextView total;
        @BindView(R.id.fragment_receive_receive)
        TextView receive;
        public AllotViewHolder(View view) {
            ButterKnife.bind(this,view);
        }

        @OnClick({R.id.fragment_receive_selectAll,R.id.fragment_receive_word1,R.id.fragment_receive_receive})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.fragment_receive_selectAll:
                case R.id.fragment_receive_word1:
                    break;
                case R.id.fragment_receive_receive:
                    ARouter.getInstance().build(PathConstant.ALLOT).navigation();
                    break;
            }
        }
    }
}
