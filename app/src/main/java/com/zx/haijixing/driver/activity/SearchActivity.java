package com.zx.haijixing.driver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.SearchAdapter;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @作者 zx
 * @创建日期 2019/7/3 17:43
 * @描述 搜索
 */
@Route(path = RoutePathConstant.DRIVER_SEARCH)
public class SearchActivity extends BaseActivity {

    @BindView(R.id.search_scan_code)
    ImageView back;
    @BindView(R.id.search_order_number)
    EditText orderNumber;
    @BindView(R.id.search_search)
    TextView search;
    @BindView(R.id.search_rv_data)
    RecyclerView searchRvData;

    @Override
    protected void initView() {
        searchRvData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        searchRvData.setAdapter(new SearchAdapter());
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @OnClick({R.id.search_scan_code, R.id.search_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_scan_code:
                finish();
                break;
            case R.id.search_search:
                break;
        }
    }
}
