package com.zx.haijixing.driver.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.EvaluateAdapter;
import com.zx.haijixing.driver.contract.EvaluateContract;
import com.zx.haijixing.driver.entry.EveryEvaluateEntry;
import com.zx.haijixing.driver.entry.TotalEvaluateEntry;
import com.zx.haijixing.driver.presenter.EvaluateImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/8 11:11
 * @描述 评价
 */
@Route(path = PathConstant.EVALUATE)
public class EvaluateActivity extends BaseActivity<EvaluateImp> implements EvaluateContract.EvaluateView,OnRefreshLoadMoreListener {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.evaluate_data)
    RecyclerView evaluateData;
    @BindView(R.id.evaluate_refresh)
    SmartRefreshLayout refresh;

    private List<EveryEvaluateEntry> everyEvaluateEntries = new ArrayList<>();
    private EvaluateAdapter evaluateAdapter;
    private int page = 1;
    private Map<String, String> params = new HashMap<>();


    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String name = (String)instance.getParam("user_name","null");
        String head = (String)instance.getParam("user_head","null");
        String token = (String)instance.getParam("token","null");


        title.setText(getHaiString(R.string.evaluate));
        evaluateData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        evaluateAdapter = new EvaluateAdapter(everyEvaluateEntries,name,head,this);
        evaluateData.setAdapter(evaluateAdapter);

        params.put(OtherConstants.PAGE,page+"");
        params.put(OtherConstants.SIZE,5+"");
        params.put("token",token);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign",HaiTool.sign(params));

        mPresenter.everyEvaluateMethod(params);

        Map<String,String> toMap = new HashMap<>();
        toMap.put("token",token);
        toMap.put("timestamp",System.currentTimeMillis()+"");
        toMap.put("sign",HaiTool.sign(toMap));
        mPresenter.totalEvaluateMethod(toMap);
    }

    @Override
    protected void initInjector() {
        mPresenter = new EvaluateImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void totalEvaluateSuccess(TotalEvaluateEntry totalEvaluateEntry) {
        evaluateAdapter.setTotalEvaluateEntry(totalEvaluateEntry);
        evaluateAdapter.notifyDataSetChanged();
    }

    @Override
    public void everyEvaluateSuccess(List<EveryEvaluateEntry> everyEvaluateEntries) {
        if (everyEvaluateEntries.size()>0){
            refresh.setNoMoreData(false);
            refresh.finishRefresh(true);
            refresh.finishLoadMore(true);
        }else {
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        }
        this.everyEvaluateEntries.addAll(everyEvaluateEntries);
        evaluateAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page<1000)
            page++;
        params.put(OtherConstants.PAGE,page+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.everyEvaluateMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        params.put(OtherConstants.PAGE,page+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        everyEvaluateEntries.clear();
        evaluateAdapter.notifyDataSetChanged();
        mPresenter.everyEvaluateMethod(params);
    }
}
