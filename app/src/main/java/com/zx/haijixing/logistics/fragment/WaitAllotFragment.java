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
import com.zx.haijixing.driver.contract.IResultPositionListener;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.logistics.adapter.AllotAdapter;
import com.zx.haijixing.logistics.contract.WaitAllotContract;
import com.zx.haijixing.logistics.presenter.WaitAllotImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.share.pub.entry.EventBusEntity;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;
import com.zx.haijixing.util.HaiTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxToastUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/16 10:59
 *@描述 待派单
 */
public class WaitAllotFragment extends BaseFragment<WaitAllotImp> implements WaitAllotContract.WaitAllotView,OnRefreshLoadMoreListener,IResultPositionListener {
    @BindView(R.id.fragment_receive_data)
    RecyclerView rvData;
    @BindView(R.id.fragment_receive_lay1)
    ViewStub viewStub;
    @BindView(R.id.fragment_receive_refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.fragment_receive_noData)
    TextView noData;



    private AllotAdapter allotAdapter;

    private Map<String, String> params = new HashMap<>();
    private List<OrderTotalEntry.OrderEntry> orderEntries = new ArrayList<>();
    private String loginType;
    private String token;
    private int page = 1;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;
    private String sureWayBill = null;
    private CommonDialogFragment showSureMoney;

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

        EventBus.getDefault().register(this);
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        token = (String) instance.getParam("token", "null");
        loginType = (String) instance.getParam("login_type", "4");

        rvData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        allotAdapter = new AllotAdapter(orderEntries);
        rvData.setAdapter(allotAdapter);
        allotAdapter.setiResultPositionListener(this::positionResult);

        params.put("token", token);
        params.put("status", OtherConstants.DETAIL_WAIT_ALLOT+"");
        //params.put("params")
        params.put(OtherConstants.PAGE, page+"");
        params.put(OtherConstants.SIZE, 5+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
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

            case R.id.dialog_update_yes:
                showSureMoney.dismissAllowingStateLoss();
                Map<String,String> moneyParams = new HashMap<>();
                moneyParams.put("token",token);
                moneyParams.put("waybillId",sureWayBill);
                moneyParams.put("timestamp",System.currentTimeMillis()+"");
                moneyParams.put("sign","");
                moneyParams.put("sign",HaiTool.sign(moneyParams));
                mPresenter.sureMoneyMethod(moneyParams);
                break;
            case R.id.dialog_update_no:
                showSureMoney.dismissAllowingStateLoss();
                break;
        }
    }

    @Override
    public void waitAllotSuccess(OrderTotalEntry orderTotalEntry) {
        if (!isHide){
            skeletonScreen.hide();
            isHide = true;
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

        noData.setVisibility(orderEntries.size() == 0?View.VISIBLE:View.GONE);
    }

    @Override
    public void sureMoneySuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        doRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        params.put(OtherConstants.PAGE, page+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.waitAllotMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        doRefresh();
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
            doRefresh();
    }


    private void doRefresh(){
        EventBus.getDefault().post(new EventBusEntity(OtherConstants.RED_BOT));
        refresh.setNoMoreData(false);
        page = 1;
        orderEntries.clear();
        allotAdapter.notifyDataSetChanged();
        params.put(OtherConstants.PAGE, page+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.waitAllotMethod(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEntity entity){
        if (entity.getMsg() == OtherConstants.ALLOT_REQUEST){
            doRefresh();
        }
    }

    @Override
    public void positionResult(OrderTotalEntry.OrderEntry orderEntry, int tag) {
        sureWayBill = orderEntry.getWaybillId();
        showSureMoney = HaiDialogUtil.showUpdate(getFragmentManager(), "是否确认收款？", this::onViewClicked);
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
