package com.zx.haijixing.driver.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.OrderAdapter;
import com.zx.haijixing.driver.adapter.SearchAdapter;
import com.zx.haijixing.driver.contract.CompleteContract;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.driver.presenter.CompleteImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/3 17:43
 * @描述 搜索
 */
@Route(path = PathConstant.DRIVER_SEARCH)
public class SearchActivity extends BaseActivity<CompleteImp> implements CompleteContract.CompleteView,OnRefreshLoadMoreListener {

    @BindView(R.id.search_scan_code)
    ImageView back;
    @BindView(R.id.search_order_number)
    EditText orderNumber;
    @BindView(R.id.search_search)
    TextView search;
    @BindView(R.id.search_rv_data)
    RecyclerView searchRvData;
    @BindView(R.id.order_search_refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.order_search_noData)
    TextView noData;

    @Autowired(name = "content")
    String content;

    private Map<String, Object> params = new HashMap<>();
    private List<OrderTotalEntry.OrderEntry> orderEntries = new ArrayList<>();
    private OrderAdapter orderAdapter;

    private int page = 1;
    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String) instance.getParam("token", "null");

        searchRvData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        orderAdapter = new OrderAdapter(orderEntries);
        searchRvData.setAdapter(orderAdapter);

        params.put("token", token);
        //params.put("status", OtherConstants.DETAIL_SENDING);
        params.put("params",content);
        params.put("pageNum", page);
        params.put("pageSize", 5);

        mPresenter.completeMethod(params);
        refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initInjector() {
        mPresenter = new CompleteImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @OnClick({R.id.search_scan_code, R.id.search_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_scan_code:
                finish();
                break;
            case R.id.search_search:
                String trim = orderNumber.getText().toString().trim();
                if (ZxStringUtil.isEmpty(trim)){
                    ZxToastUtil.centerToast("请输入搜索内容");
                }else {
                    params.put("param",trim);
                    orderEntries.clear();
                    orderAdapter.notifyDataSetChanged();
                    mPresenter.completeMethod(params);
                }
                break;
        }
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
        noData.setVisibility(orderEntries.size() == 0?View.VISIBLE:View.GONE);
    }
}
