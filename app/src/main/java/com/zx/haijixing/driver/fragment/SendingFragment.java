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
import com.zx.haijixing.driver.adapter.SendingAdapter;
import com.zx.haijixing.driver.contract.IResultPositionListener;
import com.zx.haijixing.driver.contract.SendingContract;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.driver.presenter.SendingImp;
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
 * @描述 配送中
 */
public class SendingFragment extends BaseFragment<SendingImp> implements SendingContract.SendingView, OnRefreshLoadMoreListener, IResultPositionListener,HaiDialogUtil.PayMoneyResultListener {
    @BindView(R.id.fragment_sending_data)
    RecyclerView sendingData;
    @BindView(R.id.fragment_sending_lay1)
    ViewStub viewStub;
    @BindView(R.id.fragment_sending_refresh)
    SmartRefreshLayout refresh;

    private SendingAdapter sendingAdapter;
    private SendingViewHolder sendingViewHolder;
    private Map<String, Object> params = new HashMap<>();
    private List<OrderTotalEntry.OrderEntry> orderEntries = new ArrayList<>();
    private String loginType;
    private int page = 1;
    private int flag = 0;//0部分 1全选
    private String token;
    private CommonDialogFragment showPay;
    private boolean canReceive = false;
    private boolean isTotal = false;
    private int totalData = 0;
    private int payType = 1;
    private String selectId = "";
    private String waybillId = "";
    private CommonDialogFragment showChangeMoney;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sending;
    }

    @Override
    protected void initData() {
        mPresenter = new SendingImp();
        refreshLayout = refresh;
    }

    @Override
    protected void initView(View view) {
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        loginType = (String) instance.getParam("login_type", "4");
        token = (String) instance.getParam("token", "token");

        ZxLogUtil.logError("<loginType>"+loginType);
        if (loginType.equals(OtherConstants.LOGIN_DRIVER)) {
            sendingViewHolder = new SendingViewHolder(viewStub.inflate());
        } else {

        }
        sendingData.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sendingAdapter = new SendingAdapter(orderEntries);
        sendingAdapter.setLoginType(loginType);
        sendingAdapter.setiResultPositionListener(this::positionResult);
        sendingData.setAdapter(sendingAdapter);

        params.put("token", token);
        params.put("status", OtherConstants.DETAIL_SENDING);
        //params.put("params")
        params.put(OtherConstants.PAGE, page);
        params.put(OtherConstants.SIZE, 5);

        mPresenter.sendingMethod(params);
        refresh.setOnRefreshLoadMoreListener(this);

        skeletonScreen = Skeleton.bind(sendingData).adapter(sendingAdapter)
                .shimmer(true)
                .load(R.layout.skeleton_order_center)
                .show();
    }

    @OnClick()
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_pay_no:
                isTotal = false;
                showPay.dismissAllowingStateLoss();
                break;
            case R.id.dialog_pay_yes:
                showPay.dismissAllowingStateLoss();
                complete();
                break;
            case R.id.dialog_pay_crash:
            case R.id.dialog_pay_crash_img:
                payType = 2;
                break;
            case R.id.dialog_pay_online:
            case R.id.dialog_pay_online_img:
                payType = 1;
                break;
        }
    }

    @Override
    public void sendingSuccess(OrderTotalEntry orderTotalEntry) {
        if (!isHide){
            skeletonScreen.hide();
            isHide = true;
        }

        String total = orderTotalEntry.getTotal();
        totalData = Integer.parseInt(ZxStringUtil.isEmpty(total)?"0":total);
        List<OrderTotalEntry.OrderEntry> rows = orderTotalEntry.getRows();
        if (rows.size() == 0) {
            refresh.finishRefresh(true);
            refresh.finishLoadMoreWithNoMoreData();
        } else {
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
        }
        if (flag == 1 && rows.size() > 0) {
            for (int i = 0; i < rows.size(); i++) {
                rows.get(i).setSelect(true);
            }
        } else if (flag == 0 && rows.size() > 0) {
            for (int i = 0; i < rows.size(); i++) {
                rows.get(i).setSelect(false);
            }
        }

        orderEntries.addAll(orderTotalEntry.getRows());
        sendingAdapter.notifyDataSetChanged();
        if (orderEntries.size() == 0)
            ZxToastUtil.centerToast("没有配送中订单");
    }

    @Override
    public void completeSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        orderEntries.clear();
        params.put(OtherConstants.PAGE,1);
        mPresenter.sendingMethod(params);
        sendingViewHolder.word1.setText(getString(R.string.select_all));
        sendingViewHolder.selectAll.setImageResource(R.mipmap.select_no);
        flag = 0;
        selectId = "";
    }

    @Override
    public void surePaySuccess(String msg) {
        Map<String, Object> param = new HashMap<>();
        param.put("token", token);
        param.put("selectAllFlag", flag);
        param.put("waybillIds", selectId);
        mPresenter.completeMethod(param);
    }

    @Override
    public void changePriceSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page < 1000)
            page++;
        params.put(OtherConstants.PAGE, page);
        mPresenter.sendingMethod(params);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh.setNoMoreData(false);
        page = 1;
        orderEntries.clear();
        params.put(OtherConstants.PAGE, page);
        mPresenter.sendingMethod(params);
    }

    @Override
    public void positionResult(OrderTotalEntry.OrderEntry orderEntry, int tag) {
        if (loginType.equals(OtherConstants.LOGIN_DRIVER)) {
            switch (tag){
                case 1:
                    uiSet();
                    break;
                case 2:
                    selectId = orderEntry.getWaybillId();
                    showPay = HaiDialogUtil.showPay(getFragmentManager(), SendingFragment.this::onViewClicked);
                    break;
                case 3:
                    waybillId = orderEntry.getWaybillId();
                    showChangeMoney = HaiDialogUtil.showChangeMoney(getFragmentManager(), this::payResult,orderEntry.getPrice());
                    break;
            }

        } else {
            waybillId = orderEntry.getWaybillId();
            showChangeMoney = HaiDialogUtil.showChangeMoney(getFragmentManager(), this::payResult,orderEntry.getPrice());
        }
    }




    @Override
    public void payResult(String result) {
        if ("cancel".equals(result)){
            showChangeMoney.dismissAllowingStateLoss();
        }else if (ZxStringUtil.isEmpty(result)){
            ZxToastUtil.centerToast("请输入运费");
        }else {
            showChangeMoney.dismissAllowingStateLoss();
            Map<String,Object> params = new HashMap<>();
            params.put("token",token);
            params.put("waybillId",waybillId);
            params.put("realPrice",ZxStringUtil.multiplication(result,"100",0));
            mPresenter.changePriceMethod(params);
        }
    }

    //出发
    private void complete() {
        Map<String, Object> param = new HashMap<>();
        param.put("token", token);
        param.put("selectAllFlag", flag);
        if (isTotal){
            isTotal = false;
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
            selectId = idBuilder.toString();
            if (!ZxStringUtil.isEmpty(selectId))
                selectId = selectId.substring(0, selectId.length() - 1);
        }
        param.put("waybillIds", selectId);
        param.put("payType",payType);
        mPresenter.surePayMethod(param);

    }

    //界面变化
    private void uiSet(){
        int all = 0;
        for (int i = 0; i < orderEntries.size(); i++) {
            OrderTotalEntry.OrderEntry fansBean = orderEntries.get(i);
            if (fansBean.isSelect())
                all++;
        }
        if (flag == 0) {
            sendingViewHolder.total.setText("共：" + all + "单");
            if (all == totalData) {
                sendingViewHolder.word1.setText(getString(R.string.cancel));
                sendingViewHolder.selectAll.setImageResource(R.mipmap.select_yes_solid);
                flag = 1;
            }
        } else {
            int less = totalData - orderEntries.size() + all;
            sendingViewHolder.total.setText("共：" + less + "单");
            if (less == 0) {
                sendingViewHolder.word1.setText(getString(R.string.select_all));
                sendingViewHolder.selectAll.setImageResource(R.mipmap.select_no);
                flag = 0;
            }
        }
        if (all > 0)
            canReceive = true;
    }
    class SendingViewHolder {
        @BindView(R.id.fragment_sending_selectAll)
        ImageView selectAll;
        @BindView(R.id.fragment_sending_word1)
        TextView word1;
        @BindView(R.id.fragment_sending_total)
        TextView total;
        @BindView(R.id.fragment_sending_complete)
        TextView complete;

        public SendingViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.fragment_sending_selectAll, R.id.fragment_sending_word1,R.id.fragment_sending_complete})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.fragment_sending_selectAll:
                case R.id.fragment_sending_word1:
                    selectAll();
                    break;
                case R.id.fragment_sending_complete:
                    if (canReceive) {
                        isTotal = true;
                        showPay = HaiDialogUtil.showPay(getFragmentManager(), SendingFragment.this::onViewClicked);
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
                        orderEntries.get(i).setSelect(true);
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
                sendingAdapter.notifyDataSetChanged();
            } else {
                ZxToastUtil.centerToast("暂无数据");
            }
        }
    }
}
