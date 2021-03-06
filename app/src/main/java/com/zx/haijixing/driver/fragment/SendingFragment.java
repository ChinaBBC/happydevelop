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
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/5 11:29
 * @描述 配送中
 */
public class SendingFragment extends BaseFragment<SendingImp> implements SendingContract.SendingView
        , OnRefreshLoadMoreListener, IResultPositionListener,HaiDialogUtil.PayMoneyResultListener,HaiDialogUtil.TruckResultListener {
    @BindView(R.id.fragment_sending_data)
    RecyclerView sendingData;
    @BindView(R.id.fragment_sending_lay1)
    ViewStub viewStub;
    @BindView(R.id.fragment_sending_refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.fragment_sending_noData)
    TextView noData;

    private SendingAdapter sendingAdapter;
    //private SendingViewHolder sendingViewHolder;
    private Map<String, String> params = new HashMap<>();
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
    private int showTag = 0;
    private String selectId = "";
    private String waybillId = "";
    private CommonDialogFragment showChangeMoney;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;
    private CommonDialogFragment sendComplete;

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
        EventBus.getDefault().register(this);
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        loginType = (String) instance.getParam("login_type", "4");
        String offline = (String) instance.getParam("offline", "0");
        String online = (String) instance.getParam("online", "0");
        token = (String) instance.getParam("token", "token");

        ArrayList<String> permissions = (ArrayList<String>) instance.getParam("limit",null);
        ZxLogUtil.logError("<<<<<sending fragment"+permissions.toString());
        if (offline.equals("1")){
            showTag = 2;
        }else if (online.equals("1")){
            showTag = 1;
            payType = 2;
        }
        /*ZxLogUtil.logError("<loginType>"+loginType);
        if (loginType.equals(OtherConstants.LOGIN_DRIVER)) {
            sendingViewHolder = new SendingViewHolder(viewStub.inflate());
        } else {

        }*/
        sendingData.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sendingAdapter = new SendingAdapter(orderEntries);
        sendingAdapter.setLoginType(loginType);
        sendingAdapter.setPermissions(permissions);
        sendingAdapter.setiResultPositionListener(this::positionResult);
        sendingData.setAdapter(sendingAdapter);

        params.put("token", token);
        params.put("status", OtherConstants.DETAIL_SENDING+"");
        //params.put("params")
        params.put(OtherConstants.PAGE, page+"");
        params.put(OtherConstants.SIZE, 5+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
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
            case R.id.dialog_update_no:
                sendComplete.dismissAllowingStateLoss();
                break;
            case R.id.dialog_update_yes:
                sendComplete.dismissAllowingStateLoss();
                sendCompleteMethod();
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
            refresh.setNoMoreData(false);
            refresh.finishRefreshWithNoMoreData();
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
        noData.setVisibility(orderEntries.size() == 0?View.VISIBLE:View.GONE);
    }

    @Override
    public void completeSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        orderEntries.clear();
        params.put(OtherConstants.PAGE,1+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.sendingMethod(params);
        //sendingViewHolder.word1.setText(getString(R.string.select_all));
        //sendingViewHolder.selectAll.setImageResource(R.mipmap.select_no);
        flag = 0;
        selectId = "";
        //sendingViewHolder.total.setText("共：0单");
        EventBus.getDefault().post(new EventBusEntity(OtherConstants.RED_BOT));
    }

    @Override
    public void surePaySuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        doRefresh();
       //sendCompleteMethod();
    }

    private void sendCompleteMethod(){
        Map<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("selectAllFlag", flag+"");
        param.put("waybillIds", selectId);
        param.put("timestamp",System.currentTimeMillis()+"");
        param.put("sign","");
        param.put("sign",HaiTool.sign(param));
        mPresenter.completeMethod(param);
    }
    @Override
    public void changePriceSuccess(String msg) {
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
        mPresenter.sendingMethod(params);

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
        //if (flag == 0 && sendingViewHolder != null)
            //sendingViewHolder.total.setText("共：0单");
        refresh.setNoMoreData(false);
        page = 1;
        orderEntries.clear();
        params.put(OtherConstants.PAGE, page+"");
        params.put("timestamp",System.currentTimeMillis()+"");
        params.put("sign","");
        params.put("sign",HaiTool.sign(params));
        mPresenter.sendingMethod(params);
    }
    @Override
    public void positionResult(OrderTotalEntry.OrderEntry orderEntry, int tag) {
        if (loginType.equals(OtherConstants.LOGIN_DRIVER)) {
            switch (tag){
                case 1:
                    //uiSet();
                    break;
                case 2:
                    selectId = orderEntry.getWaybillId();
                    int tempTag = 0;
                    if (showTag == 0 && !orderEntry.isCanOnlinePay()){
                        tempTag = 1;
                    }else {
                        tempTag = showTag;
                    }
                    showPay = HaiDialogUtil.showPay(getFragmentManager(),tempTag, SendingFragment.this::onViewClicked,this::truckResult);
                    break;
                case 3:
                    waybillId = orderEntry.getWaybillId();
                    showChangeMoney = HaiDialogUtil.showChangeMoney(getFragmentManager(), this::payResult,orderEntry.getPrice());
                    break;
                case 4:
                    selectId = orderEntry.getWaybillId();
                    sendComplete = HaiDialogUtil.showUpdate(getFragmentManager(), "是否确认配送完成？", this::onViewClicked);
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
            Map<String,String> param = new HashMap<>();
            param.put("token",token);
            param.put("waybillId",waybillId);
            param.put("realPrice",ZxStringUtil.multiplication(result,"100",0));
            param.put("timestamp",System.currentTimeMillis()+"");
            param.put("sign","");
            param.put("sign",HaiTool.sign(param));
            mPresenter.changePriceMethod(param);
        }
    }

    //完成
    private void complete() {
        Map<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("selectAllFlag", flag+"");
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
        param.put("payType",payType+"");
        param.put("timestamp",System.currentTimeMillis()+"");
        param.put("sign","");
        param.put("sign",HaiTool.sign(param));
        mPresenter.surePayMethod(param);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusEntity entity){
        if (entity.getMsg() == OtherConstants.STATUS_CHANGE){
            doRefresh();
        }
    }
  /*  //界面变化
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
    }*/

    @Override
    public void truckResult(int index) {
        payType = index;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /*class SendingViewHolder {
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
                        showPay = HaiDialogUtil.showPay(getFragmentManager(), SendingFragment.this::onViewClicked,SendingFragment.this::truckResult);
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
    }*/
}
