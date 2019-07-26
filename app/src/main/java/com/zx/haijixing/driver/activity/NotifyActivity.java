package com.zx.haijixing.driver.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.NotifyAdapter;
import com.zx.haijixing.driver.contract.IResultPositionListener;
import com.zx.haijixing.driver.entry.OrderTotalEntry;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;
import com.zx.haijixing.share.pub.contract.NotifyContract;
import com.zx.haijixing.share.pub.entry.EventBusEntity;
import com.zx.haijixing.share.pub.entry.NotifyEntry;
import com.zx.haijixing.share.pub.imp.NotifyImp;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;

/**
 *
 *@作者 zx
 *@创建日期 2019/7/3 16:34
 *@描述 消息中心
 */
@Route(path = PathConstant.DRIVER_NOTIFY)
public class NotifyActivity extends BaseActivity<NotifyImp> implements NotifyContract.NotifyView,OnRefreshLoadMoreListener,IResultPositionListener {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.notify_data)
    RecyclerView notifyData;
    @BindView(R.id.notify_refresh)
    SmartRefreshLayout refresh;

    private int page = 1;
    private NotifyAdapter notifyAdapter;
    private String token = null;
    private List<NotifyEntry> notifyEntries = new ArrayList<>();

    @Override
    protected void initView() {
        title.setText(getString(R.string.notify_center));

        notifyAdapter = new NotifyAdapter(notifyEntries);
        notifyData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        notifyData.setAdapter(notifyAdapter);
        notifyAdapter.setiResultPositionListener(this::positionResult);

        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token","null");
        mPresenter.notifyMethod(token,page+"",8+"");
        refresh.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initInjector() {
        mPresenter = new NotifyImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notify;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void notifySuccess(List<NotifyEntry> notifyEntries) {
        if (notifyEntries.size() == 0){
            refresh.finishRefreshWithNoMoreData();
            refresh.finishLoadMoreWithNoMoreData();
        }else {
            refresh.finishLoadMore(true);
            refresh.finishRefresh(true);
        }
        this.notifyEntries.addAll(notifyEntries);
        notifyAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (page<1000)
            page++;
        mPresenter.notifyMethod(token,page+"",8+"");
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        notifyEntries.clear();
        mPresenter.notifyMethod(token,page+"",8+"");
    }

    @Override
    public void positionResult(OrderTotalEntry.OrderEntry orderEntry, int tag) {
        if (tag == 1){
            EventBus.getDefault().post(new EventBusEntity(OtherConstants.EVENT_RECEIVE));
            finish();
        }
    }
}
