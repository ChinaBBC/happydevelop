package com.zx.haijixing.driver.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.OrderAdapter;
import com.zx.haijixing.driver.contract.CompleteContract;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.driver.presenter.CompleteImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/5 10:15
 * @描述 订单中心
 */
@Route(path = PathConstant.DRIVER_ORDER)
public class OrderActivity extends BaseActivity<CompleteImp> implements OnRefreshLoadMoreListener,CompleteContract.CompleteView {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.order_rv_data)
    RecyclerView data;
    @BindView(R.id.order_center_refresh)
    SmartRefreshLayout refresh;

    private Map<String, Object> params = new HashMap<>();
    private List<OrderTotalEntry.OrderEntry> orderEntries = new ArrayList<>();
    private OrderAdapter orderAdapter;

    private int page = 1;

    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String) instance.getParam("token", "null");

        title.setText(getHaiString(R.string.all_orders));
        data.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        orderAdapter = new OrderAdapter(orderEntries);
        data.setAdapter(orderAdapter);

        params.put("token", token);
        //params.put("status", OtherConstants.DETAIL_SENDING);
        //params.put("params")
        params.put("pageNum", page);
        params.put(OtherConstants.SIZE, 5);

        mPresenter.completeMethod(params);
        refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initInjector() {
        mPresenter = new CompleteImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        params.put(OtherConstants.PAGE, page);
        mPresenter.completeMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        orderEntries.clear();
        params.put(OtherConstants.PAGE, page);
        mPresenter.completeMethod(params);
    }

    @Override
    public void completeSuccess(OrderTotalEntry orderTotalEntry) {
        List<OrderTotalEntry.OrderEntry> rows = orderTotalEntry.getRows();
        if (rows.size() == 0) {
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        } else {
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
        }
        orderEntries.addAll(orderTotalEntry.getRows());
        orderAdapter.notifyDataSetChanged();
    }
}
