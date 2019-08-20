package com.zx.haijixing.logistics.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.LogisticsMoveAdapter;
import com.zx.haijixing.logistics.contract.LoMoveContract;
import com.zx.haijixing.logistics.entry.LinesClassEntry;
import com.zx.haijixing.logistics.presenter.LoMoveImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.HaiTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/16 14:23
 * @描述 物流动态
 */
public class LogisticMoveFragment extends BaseFragment<LoMoveImp> implements LoMoveContract.LoMoveView,OnRefreshLoadMoreListener {
    @BindView(R.id.logistics_move_img1)
    ImageView logisticsMoveImg1;
    @BindView(R.id.logistics_move_num1)
    TextView num1;
    @BindView(R.id.logistics_move_word1)
    TextView word1;
    @BindView(R.id.logistics_move_num2)
    TextView num2;
    @BindView(R.id.logistics_move_word2)
    TextView word2;
    @BindView(R.id.logistics_move_num3)
    TextView num3;
    @BindView(R.id.logistics_move_word3)
    TextView word3;
    @BindView(R.id.logistics_move_num4)
    TextView num4;
    @BindView(R.id.logistics_move_word4)
    TextView word4;
    @BindView(R.id.logistics_move_title)
    TextView title;
    @BindView(R.id.logistics_move_noData)
    TextView noData;
    @BindView(R.id.logistics_move_data)
    RecyclerView logisticsMoveData;
    @BindView(R.id.logistics_move_refresh)
    SmartRefreshLayout refresh;

    private List<LinesClassEntry> linesClassEntries = new ArrayList<>();
    private LogisticsMoveAdapter logisticsMoveAdapter;
    private Map<String,String> allParams = new HashMap<>();
    private Map<String,String> params = new HashMap<>();
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_logistics_move;
    }

    @Override
    protected void initData() {
        mPresenter = new LoMoveImp();
    }

    @Override
    protected void initView(View view) {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        String token = (String) instance.getParam("token", "null");
        /*String loginType = (String) instance.getParam("login_type", "4");
        ZxLogUtil.logError("<<<<<" + token);
        if (OtherConstants.LOGIN_MANAGER.equals(loginType)){
            title.setVisibility(View.VISIBLE);
            setTitleTopMargin(title,0);
        }*/
        logisticsMoveData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        logisticsMoveAdapter = new LogisticsMoveAdapter(linesClassEntries);
        logisticsMoveData.setAdapter(logisticsMoveAdapter);

        allParams.put("token",token);
        allParams.put(OtherConstants.PAGE,page+"");
        allParams.put(OtherConstants.SIZE,5+"");
        allParams.put("timestamp",System.currentTimeMillis()+"");
        allParams.put("sign","");
        allParams.put("sign",HaiTool.sign(allParams));
        refresh.setOnRefreshLoadMoreListener(this);

        mPresenter.loMoveMethod(allParams);

        params.put("token",token);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.countAllMethod(params);

    }

    @OnClick({R.id.logistics_move_num1, R.id.logistics_move_word1, R.id.logistics_move_num2, R.id.logistics_move_word2, R.id.logistics_move_num3, R.id.logistics_move_word3, R.id.logistics_move_num4, R.id.logistics_move_word4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.logistics_move_num1:
            case R.id.logistics_move_word1:

                break;
            case R.id.logistics_move_num2:
            case R.id.logistics_move_word2:

                break;
            case R.id.logistics_move_num3:
            case R.id.logistics_move_word3:

                break;
            case R.id.logistics_move_num4:
            case R.id.logistics_move_word4:

                break;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        allParams.put(OtherConstants.PAGE, page+"");
        allParams.put("timestamp",System.currentTimeMillis()+"");
        allParams.put("sign","");
        allParams.put("sign",HaiTool.sign(allParams));
        mPresenter.loMoveMethod(allParams);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh.setNoMoreData(false);
        page = 1;
        linesClassEntries.clear();
        logisticsMoveAdapter.notifyDataSetChanged();
        allParams.put(OtherConstants.PAGE, page+"");
        allParams.put("timestamp",System.currentTimeMillis()+"");
        allParams.put("sign","");
        allParams.put("sign",HaiTool.sign(allParams));
        mPresenter.loMoveMethod(allParams);
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.countAllMethod(params);
    }

    @Override
    public void loMoveSuccess(List<LinesClassEntry> linesClassEntries) {
        if (linesClassEntries.size() == 0) {
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        } else {
            refresh.setNoMoreData(false);
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
        }
        this.linesClassEntries.addAll(linesClassEntries);
        logisticsMoveAdapter.notifyDataSetChanged();
        noData.setVisibility(this.linesClassEntries.size() == 0?View.VISIBLE:View.GONE);
    }

    @Override
    public void countAllSuccess(String today, String waitSend, String sending, String complete) {
        num1.setText(today);
        num2.setText(waitSend);
        num3.setText(sending);
        num4.setText(complete);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            page = 1;
            linesClassEntries.clear();
            logisticsMoveAdapter.notifyDataSetChanged();
            allParams.put(OtherConstants.PAGE, page+"");
            allParams.put("timestamp",System.currentTimeMillis()+"");
            allParams.put("sign","");
            allParams.put("sign",HaiTool.sign(allParams));
            mPresenter.loMoveMethod(allParams);
            params.put("timestamp",System.currentTimeMillis()+"");
            params.put("sign","");
            params.put("sign",HaiTool.sign(params));
            mPresenter.countAllMethod(params);
        }
    }
}
