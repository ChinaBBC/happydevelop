package com.zx.haijixing.logistics.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.QuantityClassAdapter;
import com.zx.haijixing.logistics.contract.QuantityClassConnect;
import com.zx.haijixing.logistics.presenter.QuantityClassImp;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;
import zx.com.skytool.ZxSharePreferenceUtil;

@Route(path = PathConstant.QUANTITY_CLASS)
public class QuantityClassActivity extends BaseActivity<QuantityClassImp> implements QuantityClassConnect.QuantityClassView {

    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.quantity_class_list)
    RecyclerView classList;
    private QuantityClassAdapter adapter;
    private String token;

    @Override
    protected void initView() {
        title.setText(getString(R.string.quantity_class_manage));
        ZxSharePreferenceUtil instance = ZxSharePreferenceUtil.getInstance();
        instance.init(this);
        token = (String) instance.getParam("token", "null");

        classList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new QuantityClassAdapter();
        classList.setAdapter(adapter);
    }

    @Override
    protected void initInjector() {
        mPresenter = new QuantityClassImp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quantity_class;
    }

    @OnClick({R.id.common_title_back, R.id.quantity_class_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.quantity_class_complete:
                finish();
                break;
        }
    }

    @Override
    public void quClassSuccess() {

    }
}
