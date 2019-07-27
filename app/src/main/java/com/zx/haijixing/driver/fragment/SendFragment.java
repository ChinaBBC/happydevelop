package com.zx.haijixing.driver.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.DriverClassWheel;
import com.zx.haijixing.driver.adapter.SendAdapter;
import com.zx.haijixing.driver.contract.IResultPositionListener;
import com.zx.haijixing.driver.contract.SendContract;
import com.zx.haijixing.driver.entry.DriverClassEntry;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.driver.presenter.SendImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.CommonDialogFragment;
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
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/5 11:29
 * @描述 待出发
 */
public class SendFragment extends BaseFragment<SendImp> implements SendContract.SendView, OnRefreshLoadMoreListener, IResultPositionListener,HaiDialogUtil.TruckResultListener {
    @BindView(R.id.fragment_send_data)
    RecyclerView sendData;
    @BindView(R.id.fragment_send_lay1)
    ViewStub viewStub;
    @BindView(R.id.fragment_send_refresh)
    SmartRefreshLayout refresh;

    private Map<String, Object> params = new HashMap<>();
    private List<OrderTotalEntry.OrderEntry> orderEntries = new ArrayList<>();
    private int loginType = 0;
    private SendViewHolder sendViewHolder;
    private int page = 1;
    private int flag = 0;//0部分 1全选
    private String token;
    private CommonDialogFragment showSend;
    private boolean canReceive = false;
    private int totalData = 0;
    private SendAdapter sendAdapter;
    private DriverClassWheel driverClassAdapter;//班次选择
    private CommonDialogFragment showTruck;//班次选择
    private int temp = 0;//班次下标
    private List<DriverClassEntry> driverClassEntries;//班次信息
    private String bakkiId;//班次id
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_send;
    }

    @Override
    protected void initData() {
        mPresenter = new SendImp();
    }

    @Override
    protected void initView(View view) {
        sendData.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sendAdapter = new SendAdapter(orderEntries);
        sendAdapter.setiResultPositionListener(this::positionResult);
        sendData.setAdapter(sendAdapter);
        if (loginType == 0) {
            sendViewHolder = new SendViewHolder(viewStub.inflate());
            sendViewHolder.receive.setText(getHaiString(R.string.go_now));
        }
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        token = (String) instance.getParam("token", "token");
        ZxLogUtil.logError("<<<<<" + token);
        params.put("token", token);
        params.put("status", OtherConstants.DETAIL_WAIT_SEND);
        //params.put("params")
        params.put(OtherConstants.PAGE, page);
        params.put(OtherConstants.SIZE, 5);

        mPresenter.sendMethod(params);
        mPresenter.driverClassMethod(token);
        refresh.setOnRefreshLoadMoreListener(this);

        skeletonScreen = Skeleton.bind(sendData).adapter(sendAdapter)
                .shimmer(true)
                .load(R.layout.skeleton_order_center)
                .show();
    }


    @OnClick()
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_locate_yes:
                showSend.dismissAllowingStateLoss();
                depart();
                break;
            case R.id.dialog_locate_no:
                showSend.dismissAllowingStateLoss();
                bakkiId = null;
                break;
        }
    }

    @Override
    public void sendSuccess(OrderTotalEntry orderTotalEntry) {
        if (!isHide){
            skeletonScreen.hide();
            isHide = true;
        }
        String total = orderTotalEntry.getTotal();
        totalData = Integer.parseInt(ZxStringUtil.isEmpty(total)?"0":total);
        List<OrderTotalEntry.OrderEntry> rows = orderTotalEntry.getRows();
        if (rows.size() == 0) {
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        } else {
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
        }
        if (flag == 1 && rows.size() > 0) {
            sendViewHolder.total.setText("共：" + totalData + "单");
            for (int i = 0; i < rows.size(); i++) {
                rows.get(i).setSelect(true);
            }
        } else if (flag == 0 && rows.size() > 0) {
            for (int i = 0; i < rows.size(); i++) {
                rows.get(i).setSelect(false);
            }
        }

        orderEntries.addAll(orderTotalEntry.getRows());
        sendAdapter.notifyDataSetChanged();
        if (orderEntries.size() == 0)
            ZxToastUtil.centerToast("没有待出发的订单");
    }

    @Override
    public void departSuccess(String msg) {
        if ("1010".equals(msg)){
            if (driverClassAdapter == null)
                driverClassAdapter = new DriverClassWheel(driverClassEntries);
            showTruck = HaiDialogUtil.showTruck(getFragmentManager(), driverClassAdapter, this::truckResult);
        }else {
            ZxToastUtil.centerToast(msg);
            orderEntries.clear();
            params.put(OtherConstants.PAGE,1);
            mPresenter.sendMethod(params);
            sendViewHolder.word1.setText(getString(R.string.select_all));
            sendViewHolder.selectAll.setImageResource(R.mipmap.select_no);
            flag = 0;
        }
    }

    @Override
    public void driverClassSuccess(List<DriverClassEntry> driverClassEntries) {
        this.driverClassEntries = driverClassEntries;
    }

    //出发
    private void depart() {
        Map<String, Object> param = new HashMap<>();
        param.put("token", token);
        param.put("selectAllFlag", flag);
        StringBuilder idBuilder = new StringBuilder();

        for (int i = 0; i < orderEntries.size(); i++) {
            OrderTotalEntry.OrderEntry fansBean = orderEntries.get(i);
            if (flag == 0 && fansBean.isSelect()) {
                idBuilder.append(fansBean.getWaybillId());
                idBuilder.append(",");
            } else if (flag == 1 && !fansBean.isSelect()) {
                idBuilder.append(fansBean.getWaybillId());
                idBuilder.append(",");
            }
        }
        String selectId = idBuilder.toString();;
        if (!ZxStringUtil.isEmpty(selectId))
            selectId = selectId.substring(0, selectId.length() - 1);
        if (!ZxStringUtil.isEmpty(bakkiId))
            param.put("bkkiId",bakkiId);

        ZxLogUtil.logError("<<<<selectId>>"+selectId+"<>"+flag);
        param.put("waybillIds", selectId);
        bakkiId = null;
        mPresenter.departMethod(param);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        params.put(OtherConstants.PAGE, page);
        mPresenter.sendMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh.setNoMoreData(false);
        sendViewHolder.total.setText("共：0单");
        page = 1;
        orderEntries.clear();
        params.put(OtherConstants.PAGE, page);
        mPresenter.sendMethod(params);
    }

    @Override
    public void positionResult(OrderTotalEntry.OrderEntry orderEntry, int tag) {
        int all = 0;
        for (int i = 0; i < orderEntries.size(); i++) {
            OrderTotalEntry.OrderEntry fansBean = orderEntries.get(i);
            if (fansBean.isSelect())
                all++;
        }
        if (flag == 0) {
            sendViewHolder.total.setText("共：" + all + "单");
            if (all == totalData) {
                sendViewHolder.word1.setText(getString(R.string.cancel));
                sendViewHolder.selectAll.setImageResource(R.mipmap.select_yes_solid);
                flag = 1;
            }
        } else {
            int less = totalData - orderEntries.size() + all;
            sendViewHolder.total.setText("共：" + less + "单");
            if (less == 0) {
                sendViewHolder.word1.setText(getString(R.string.select_all));
                sendViewHolder.selectAll.setImageResource(R.mipmap.select_no);
                flag = 0;
            }
        }
        canReceive = (all>0);
    }

    @Override
    public void truckResult(int index) {
        ZxLogUtil.logError("<<<<<<<<<<<"+index);
        if (index>-1){
            temp = index;
        }else if (index == -1){
            DriverClassEntry driverClassEntry = driverClassEntries.get(temp);
            bakkiId = driverClassEntry.getBakkiId();
            showTruck.dismissAllowingStateLoss();
            showSend = HaiDialogUtil.showLocate(getFragmentManager(), SendFragment.this::onViewClicked);
        }else if (index == -2){
            showTruck.dismissAllowingStateLoss();
        }
    }

    class SendViewHolder {
        @BindView(R.id.fragment_receive_selectAll)
        ImageView selectAll;
        @BindView(R.id.fragment_receive_word1)
        TextView word1;
        @BindView(R.id.fragment_receive_total)
        TextView total;
        @BindView(R.id.fragment_receive_receive)
        TextView receive;

        public SendViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.fragment_receive_selectAll, R.id.fragment_receive_word1, R.id.fragment_receive_receive})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.fragment_receive_selectAll:
                case R.id.fragment_receive_word1:
                    selectAll();
                    break;
                case R.id.fragment_receive_receive:
                    if (canReceive) {
                        showSend = HaiDialogUtil.showLocate(getFragmentManager(), SendFragment.this::onViewClicked);
                    } else {
                        ZxToastUtil.centerToast("您还没有选择订单");
                    }
                    break;
            }
        }

        private void selectAll() {
            if (orderEntries.size() > 0) {
                if (flag == 0) {
                    word1.setText(getString(R.string.cancel));
                    selectAll.setImageResource(R.mipmap.select_yes_solid);
                    flag = 1;
                    for (int i = 0; i < orderEntries.size(); i++) {
                        OrderTotalEntry.OrderEntry orderEntry = orderEntries.get(i);
                        //if ("1".equals(orderEntry.getDadanFlag()))
                            orderEntry.setSelect(true);
                    }
                    total.setText("共：" + totalData + "单");
                    canReceive = true;
                } else {
                    word1.setText(getString(R.string.select_all));
                    selectAll.setImageResource(R.mipmap.select_no);
                    flag = 0;
                    for (int i = 0; i < orderEntries.size(); i++) {
                        orderEntries.get(i).setSelect(false);
                    }
                    total.setText("共：0单");
                    canReceive = false;
                }
                sendAdapter.notifyDataSetChanged();
            } else {
                ZxToastUtil.centerToast("暂无数据");
            }
        }
    }

}
