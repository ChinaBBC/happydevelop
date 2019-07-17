package com.zx.haijixing.driver.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.OrderAdapter;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/5 10:15
 * @描述 订单中心
 */
@Route(path = PathConstant.DRIVER_ORDER)
public class OrderActivity extends BaseActivity {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.order_rv_data)
    RecyclerView data;


    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.all_orders));
        data.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        data.setAdapter(new OrderAdapter());
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @OnClick(R.id.common_title_back)
    public void onViewClicked() {
        finish();
    }
}
