package com.zx.haijixing.driver.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.RxHttpUtils;
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen;
import com.ethanhua.skeleton.Skeleton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.ReceiveAdapter;
import com.zx.haijixing.driver.contract.IResultPositionListener;
import com.zx.haijixing.driver.contract.OrderContract;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.driver.presenter.ReceiveImp;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.base.BaseFragment;
import com.zx.haijixing.util.CommonDialogFragment;
import com.zx.haijixing.util.HaiDialogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import zx.com.skytool.ZxLogUtil;
import zx.com.skytool.ZxSharePreferenceUtil;
import zx.com.skytool.ZxStringUtil;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/5 11:28
 * @描述 待接单
 */
public class ReceivedFragment extends BaseFragment<ReceiveImp> implements OrderContract.OrderView,IResultPositionListener,OnRefreshLoadMoreListener {
    @BindView(R.id.fragment_receive_data)
    RecyclerView rvData;
    @BindView(R.id.fragment_receive_lay1)
    ViewStub viewStub;
    @BindView(R.id.fragment_receive_refresh)
    SmartRefreshLayout refresh;

    private ReceiveAdapter receiveAdapter;

    private Map<String, Object> params = new HashMap<>();
    private List<OrderTotalEntry.OrderEntry> orderEntryList = new ArrayList<>();
    private int page = 1;
    private ReceiveViewHolder receiveViewHolder;
    private int flag = 0;//0部分 1全选
    private int temp = 0;//0部分 1全选
    private String token;
    private String selectId = "";
    private CommonDialogFragment showReceive;
    private boolean canReceive = false;
    private boolean isTotal = false;
    private int totalData = 0;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private boolean isHide = false;
    private String loginType;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_receive;
    }

    @Override
    protected void initData() {
        mPresenter = new ReceiveImp();
    }

    @Override
    protected void initView(View view) {

        rvData.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        receiveAdapter = new ReceiveAdapter(orderEntryList);
        receiveAdapter.setOnClickListener(this::positionResult);
        rvData.setAdapter(receiveAdapter);

        receiveViewHolder = new ReceiveViewHolder(viewStub.inflate());

        refresh.setOnRefreshLoadMoreListener(this);

        skeletonScreen = Skeleton.bind(rvData).adapter(receiveAdapter)
                .shimmer(true)
                .load(R.layout.skeleton_order_center)
                .show();
    }

    @OnClick()
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_receive_yes:
                showReceive.dismissAllowingStateLoss();
                overbooking();
                ZxLogUtil.logError("<<receive yes");
                break;
            case R.id.dialog_receive_no:
                ZxLogUtil.logError("<<receive no");
                isTotal = false;
                flag = temp;
                showReceive.dismissAllowingStateLoss();
                break;
        }
    }

    @Override
    public void orderSuccess(OrderTotalEntry list) {
        if (!isHide){
            skeletonScreen.hide();
            isHide = true;
        }

        String total = list.getTotal();
        totalData = Integer.parseInt(ZxStringUtil.isEmpty(total)?"0":total);
        List<OrderTotalEntry.OrderEntry> rows = list.getRows();
        if (rows.size() == 0){
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        }else {
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
        }
        if (flag == 1 && rows.size()>0){
            for (int i = 0; i< rows.size(); i++){
                rows.get(i).setSelect(true);
            }
        }else if (flag == 0 && rows.size()>0){
            for (int i = 0; i< rows.size(); i++){
                rows.get(i).setSelect(false);
            }
        }
        orderEntryList.addAll(list.getRows());
        receiveAdapter.setRefresh(false);
        receiveAdapter.notifyDataSetChanged();
        if (orderEntryList.size() == 0)
            ZxToastUtil.centerToast("没有未接的订单");
    }

    @Override
    public void receiveOrderSuccess(String msg) {
        ZxToastUtil.centerToast(msg);
        selectId = "";
        orderEntryList.clear();
        params.put(OtherConstants.PAGE,1);
        mPresenter.orderMethod(params);
        receiveViewHolder.word1.setText(getString(R.string.select_all));
        receiveViewHolder.selectAll.setImageResource(R.mipmap.select_no);
        flag = 0;
    }

    //下单
    private void overbooking() {
        Map<String,Object> param = new HashMap<>();
        param.put("token",token);
        param.put("selectAllFlag",flag);
        if (isTotal){
            isTotal = false;
            StringBuilder idBuilder = new StringBuilder();
            for (int i=0;i<orderEntryList.size();i++){
                OrderTotalEntry.OrderEntry fansBean = orderEntryList.get(i);
                if (flag == 0 && fansBean.isSelect()){
                    idBuilder.append(fansBean.getWaybillId());
                    idBuilder.append(",");
                }else if (flag == 1 && !fansBean.isSelect()){
                    idBuilder.append(fansBean.getWaybillId());
                    idBuilder.append(",");
                }
            }
            selectId = idBuilder.toString();
            if (!ZxStringUtil.isEmpty(selectId))
                selectId = selectId.substring(0,selectId.length()-1);
        }
        ZxLogUtil.logError("<vflag>"+flag+"<selectId>"+selectId);
        param.put("waybillIds",selectId);
        mPresenter.receiveOrderMethod(param);
    }

    //更新时间
    private void updateTime(){
        compositeDisposable.add(Observable.interval(60, 60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver(1)));
    }
    private DisposableObserver getObserver(final int id) {
        DisposableObserver disposableObserver = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                ZxLogUtil.logError("<00>"+orderEntryList.size()+"<ddd>"+String.valueOf(o));
                if (orderEntryList.size()>0){
                    receiveAdapter.setRefresh(true);
                    receiveAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        };

        return disposableObserver;
    }

    @Override
    public void positionResult(OrderTotalEntry.OrderEntry orderEntry, int tag) {
        if (tag == 1){
            int all = 0;
            for (int i=0;i<orderEntryList.size();i++){
                OrderTotalEntry.OrderEntry fansBean = orderEntryList.get(i);
                if (fansBean.isSelect())
                    all++;
            }
            if (flag == 0) {
                receiveViewHolder.total.setText("共：" + all + "单");
                if (all == totalData){
                    receiveViewHolder.word1.setText(getString(R.string.cancel));
                    receiveViewHolder.selectAll.setImageResource(R.mipmap.select_yes_solid);
                    flag = 1;
                }
            }else {
                int less = totalData - orderEntryList.size() + all;
                receiveViewHolder.total.setText("共：" + less + "单");
                if (less == 0){
                    receiveViewHolder.word1.setText(getString(R.string.select_all));
                    receiveViewHolder.selectAll.setImageResource(R.mipmap.select_no);
                    flag = 0;
                }
            }
            canReceive = (all>0);

        }else {
            selectId = orderEntry.getWaybillId();
            temp = flag;
            flag = 0;
            showReceive = HaiDialogUtil.showReceive(getFragmentManager(), this::onViewClicked, "共:1单");
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page<1000)
            page++;
        params.put(OtherConstants.PAGE,page);
        mPresenter.orderMethod(params);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh.setNoMoreData(false);
        page = 1;
        orderEntryList.clear();
        params.put(OtherConstants.PAGE,page);
        mPresenter.orderMethod(params);
    }


    class ReceiveViewHolder {
        @BindView(R.id.fragment_receive_selectAll)
        ImageView selectAll;
        @BindView(R.id.fragment_receive_word1)
        TextView word1;
        @BindView(R.id.fragment_receive_total)
        TextView total;
        @BindView(R.id.fragment_receive_receive)
        TextView receive;

        public ReceiveViewHolder(View view) {
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
                    String totals = total.getText().toString();
                    if (canReceive) {
                        temp = flag;
                        isTotal = true;
                        showReceive = HaiDialogUtil.showReceive(getFragmentManager(), ReceivedFragment.this::onViewClicked, totals);
                    }else {
                        ZxToastUtil.centerToast("您还没有选择订单");
                    }
                    break;
            }
        }

        private void selectAll(){
            if (orderEntryList.size()>0){
                if (flag == 0){
                    word1.setText(getString(R.string.cancel));
                    selectAll.setImageResource(R.mipmap.select_yes_solid);
                    flag = 1;
                    for (int i=0;i<orderEntryList.size();i++){
                        orderEntryList.get(i).setSelect(true);
                    }
                    total.setText("共："+totalData + "单");
                    canReceive = true;
                }else {
                    word1.setText(getString(R.string.select_all));
                    selectAll.setImageResource(R.mipmap.select_no);
                    flag = 0;
                    for (int i=0;i<orderEntryList.size();i++){
                        orderEntryList.get(i).setSelect(false);
                    }
                    total.setText("共：0单");
                    canReceive = false;
                }
                receiveAdapter.notifyDataSetChanged();
            }else {
                ZxToastUtil.centerToast("暂无数据");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(getContext());
        token = (String) instance.getParam("token", "token");
        loginType = (String) instance.getParam("login_type", "4");
        receiveAdapter.setLoginType(loginType);
        ZxLogUtil.logError("<<<<<on resume" + token);
        params.put("token", token);
        params.put("status",OtherConstants.DETAIL_WAIT_RECEIVE);
        //params.put("params")
        params.put(OtherConstants.PAGE, page);
        params.put(OtherConstants.SIZE, 5);
        orderEntryList.clear();
        mPresenter.orderMethod(params);
        updateTime();
    }

    @Override
    public void onPause() {
        super.onPause();
        ZxLogUtil.logError("<<<<<on pause");
        compositeDisposable.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiveViewHolder = null;
    }
}
