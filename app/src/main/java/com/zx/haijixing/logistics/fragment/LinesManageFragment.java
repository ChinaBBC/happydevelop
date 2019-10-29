package com.zx.haijixing.logistics.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.LinesManageAdapter;
import com.zx.haijixing.logistics.adapter.ProductAdapter;
import com.zx.haijixing.logistics.contract.LinesMangeContract;
import com.zx.haijixing.logistics.entry.LinesManageEntry;
import com.zx.haijixing.logistics.entry.ProductEntry;
import com.zx.haijixing.logistics.presenter.LinesManageImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/16 14:23
 * @描述 路线管理
 */
public class LinesManageFragment extends BaseFragment<LinesManageImp> implements LinesMangeContract.LinesManageView, OnRefreshLoadMoreListener,HaiDialogUtil.TruckResultListener {
    @BindView(R.id.fragment_lines_manage_data)
    RecyclerView lineData;
    @BindView(R.id.fragment_lines_manage_refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.fragment_lines_manage_noData)
    TextView noData;
    @BindView(R.id.fragment_lines_manage_ways)
    TextView ways;
    @BindView(R.id.fragment_lines_manage_allLines)
    TextView allLines;
    @BindView(R.id.fragment_lines_manage_input)
    EditText inputStr;

    private LinesManageAdapter linesManageAdapter;
    private List<LinesManageEntry> linesManageEntries = new ArrayList<>();
    private List<ProductEntry> productEntries = new ArrayList<>();
    private Map<String, String> params = new HashMap<>();
    private int page = 1;
    private CommonDialogFragment productDialog;
    private int productIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lines_manage;
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

        lineData.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        linesManageAdapter = new LinesManageAdapter(linesManageEntries);
        lineData.setAdapter(linesManageAdapter);

        refresh.setOnRefreshLoadMoreListener(this);

        params.put("token", token);
        params.put(OtherConstants.PAGE, page + "");
        params.put(OtherConstants.SIZE, 5 + "");
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", "");
        params.put("sign", HaiTool.sign(params));
        mPresenter.linesManageMethod(params);

        Map<String, String> waysParams = new HashMap<>();
        waysParams.put("token", token);
        waysParams.put("timestamp", System.currentTimeMillis() + "");
        waysParams.put("sign", "");
        waysParams.put("sign", HaiTool.sign(waysParams));
        mPresenter.logisticsWayMethod(waysParams);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        params.put(OtherConstants.PAGE, page + "");
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", "");
        params.put("sign", HaiTool.sign(params));
        mPresenter.linesManageMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh.setNoMoreData(false);
        page = 1;
        linesManageEntries.clear();
        linesManageAdapter.notifyDataSetChanged();
        params.put(OtherConstants.PAGE, page + "");
        params.put("timestamp", System.currentTimeMillis() + "");
        params.put("sign", "");
        params.put("sign", HaiTool.sign(params));
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
        noData.setVisibility(this.linesManageEntries.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void logisticsWaySuccess(List<ProductEntry> data) {
        ProductEntry productEntry = new ProductEntry();
        productEntry.setDictValue("全部");
        productEntry.setDictCode("");
        productEntries.add(productEntry);
        if (data.size()>0){
            productEntries.addAll(data);
        }
    }


    @OnClick({R.id.fragment_lines_manage_ways, R.id.fragment_lines_manage_ways_img, R.id.fragment_lines_manage_search,R.id.fragment_lines_manage_manageAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_lines_manage_ways:
            case R.id.fragment_lines_manage_ways_img:
                if (productEntries.size()>0){
                    productIndex = 0;
                    ProductAdapter productAdapter = new ProductAdapter(productEntries);
                    productDialog = HaiDialogUtil.showTruck(getFragmentManager(), productAdapter, this::truckResult);
                }else {
                    ZxToastUtil.centerToast("请稍候再试");
                }
                break;
            case R.id.fragment_lines_manage_search:
                refresh.setNoMoreData(false);
                page = 1;
                linesManageEntries.clear();
                linesManageAdapter.notifyDataSetChanged();
                String trim = inputStr.getText().toString().trim();
                params.put(OtherConstants.PAGE, page + "");
                params.put("keyword",trim);
                params.put("timestamp", System.currentTimeMillis() + "");
                params.put("sign", "");
                params.put("sign", HaiTool.sign(params));
                mPresenter.linesManageMethod(params);
                break;
            case R.id.fragment_lines_manage_manageAll:
                ARouter.getInstance().build(PathConstant.QUANTITY_SET).navigation();
                break;
        }
    }

    @Override
    public void truckResult(int index) {
        if (index == -1){
            ProductEntry productEntry = productEntries.get(productIndex);
            ways.setText(productEntry.getDictValue());
            params.put("productId",productEntry.getDictCode());
            productDialog.dismissAllowingStateLoss();
        }else if (index == -2){
            productDialog.dismissAllowingStateLoss();
        }else {
            productIndex = index;
        }
    }
}
