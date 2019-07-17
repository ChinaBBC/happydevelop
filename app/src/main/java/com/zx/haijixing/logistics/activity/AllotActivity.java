package com.zx.haijixing.logistics.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.logistics.adapter.LogisticsMoveAdapter;
import com.zx.haijixing.share.OtherConstants;
import com.zx.haijixing.share.PathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zx.com.skytool.ZxToastUtil;

/**
 * @作者 zx
 * @创建日期 2019/7/17 16:30
 * @描述 派单
 */
@Route(path = PathConstant.ALLOT)
public class AllotActivity extends BaseActivity {

    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.allot_rv_data)
    RecyclerView allotRvData;
    private LogisticsMoveAdapter logisticsMoveAdapter;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.wait_allot_order));
        allotRvData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        logisticsMoveAdapter = new LogisticsMoveAdapter();
        logisticsMoveAdapter.setLoadType(OtherConstants.LOAD_ALLOT);
        logisticsMoveAdapter.setOnClickListener(this::onViewClicked);
        allotRvData.setAdapter(logisticsMoveAdapter);
    }

    @Override
    protected void initInjector() {

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
        }
    }
}
