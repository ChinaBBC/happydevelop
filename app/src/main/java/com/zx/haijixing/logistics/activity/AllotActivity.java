package com.zx.haijixing.logistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.LogisticsMoveAdapter;
import com.zx.haijixing.logistics.contract.LinesClassContract;
import com.zx.haijixing.logistics.entry.LinesClassEntry;
import com.zx.haijixing.logistics.presenter.LinesClassImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
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
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/17 16:30
 * @描述 派单
 */
@Route(path = PathConstant.ALLOT)
public class AllotActivity extends BaseActivity<LinesClassImp> implements LinesClassContract.LinesClassView,
        LogisticsMoveAdapter.AllotResultListener,OnRefreshLoadMoreListener {

    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.allot_rv_data)
    RecyclerView allotRvData;
    @BindView(R.id.allot_srl_refresh)
    SmartRefreshLayout refresh;

    @Autowired(name = "orderId")
    public String orderId;
    @Autowired(name = "linesId")
    public String linesId;

    private LogisticsMoveAdapter logisticsMoveAdapter;
    private List<LinesClassEntry> linesClassEntries = new ArrayList<>();
    private Map<String,String> linesParams = new HashMap<>();
    private Map<String,String> allotParams = new HashMap<>();
    private int page = 1;
    private CommonDialogFragment showAllot;


    @Override
    protected void initView() {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        String token = (String) instance.getParam("token", "token");
        ZxLogUtil.logError("<<<<<" + token);

        title.setText(getHaiString(R.string.wait_allot_order));
        allotRvData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        logisticsMoveAdapter = new LogisticsMoveAdapter(linesClassEntries);
        logisticsMoveAdapter.setLoadType(OtherConstants.LOAD_ALLOT);
        logisticsMoveAdapter.setAllotResultListener(this::allotResult);
        allotRvData.setAdapter(logisticsMoveAdapter);

        linesParams.put("token",token);
        linesParams.put("lineId",linesId);
        linesParams.put(OtherConstants.PAGE,page+"");
        linesParams.put(OtherConstants.SIZE,5+"");

        allotParams.put("token",token);
        allotParams.put("waybillId",orderId);
        allotParams.put("lineId",linesId);

        refresh.setOnRefreshLoadMoreListener(this);

        linesParams.put("timestamp",System.currentTimeMillis()+"");
        linesParams.put("sign","");
        linesParams.put("sign",HaiTool.sign(linesParams));
        mPresenter.linesClassMethod(linesParams);
    }

    @Override
    protected void initInjector() {
        mPresenter = new LinesClassImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_allot;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.common_title_back:
                finish();
                break;
            case R.id.move_data_count_allot:
                ZxToastUtil.centerToast("派单");
                break;
            case R.id.dialog_update_yes:
                showAllot.dismissAllowingStateLoss();
                allotParams.put("timestamp",System.currentTimeMillis()+"");
                allotParams.put("sign","");
                allotParams.put("sign",HaiTool.sign(allotParams));
                mPresenter.allotOrderMethod(allotParams);
                break;
            case R.id.dialog_update_no:
                showAllot.dismissAllowingStateLoss();
                break;

        }
    }

    @Override
    public void linesClassSuccess(List<LinesClassEntry> linesClassEntries) {

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
        if (this.linesClassEntries.size() == 0)
            ZxToastUtil.centerToast("没有可选的班次");
    }

    @Override
    public void allotOrderSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        /*linesClassEntries.clear();
        logisticsMoveAdapter.notifyDataSetChanged();
        page = 1;
        linesParams.put(OtherConstants.PAGE,page+"");
        linesParams.put("timestamp",System.currentTimeMillis()+"");
        linesParams.put("sign","");
        linesParams.put("sign",HaiTool.sign(linesParams));
        mPresenter.linesClassMethod(linesParams);*/

        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void allotResult(String bkId) {
        allotParams.put("bkkiId",bkId);
        showAllot = HaiDialogUtil.showUpdate(getSupportFragmentManager(), "是否确认派单", this::onViewClicked);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        linesParams.put(OtherConstants.PAGE, page+"");
        linesParams.put("timestamp",System.currentTimeMillis()+"");
        linesParams.put("sign","");
        linesParams.put("sign",HaiTool.sign(linesParams));
        mPresenter.linesClassMethod(linesParams);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh.setNoMoreData(false);
        page = 1;
        linesClassEntries.clear();
        logisticsMoveAdapter.notifyDataSetChanged();
        linesParams.put(OtherConstants.PAGE, page+"");
        linesParams.put("timestamp",System.currentTimeMillis()+"");
        linesParams.put("sign","");
        linesParams.put("sign",HaiTool.sign(linesParams));
        mPresenter.linesClassMethod(linesParams);
    }
}
