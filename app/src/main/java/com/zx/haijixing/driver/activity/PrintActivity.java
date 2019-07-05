package com.zx.haijixing.driver.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zx.haijixing.R;
import com.zx.haijixing.driver.adapter.PrintAdapter;
import com.zx.haijixing.share.RoutePathConstant;
import com.zx.haijixing.share.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = RoutePathConstant.PRINT)
public class PrintActivity extends BaseActivity {

    @BindView(R.id.common_title_back)
    ImageView back;
    @BindView(R.id.common_title_title)
    TextView title;
    @BindView(R.id.print_selectAll)
    ImageView selectAll;
    @BindView(R.id.print_word1)
    TextView word1;
    @BindView(R.id.print_total)
    TextView total;
    @BindView(R.id.print_print)
    TextView print;
    @BindView(R.id.print_data)
    RecyclerView printData;

    @Override
    protected void initView() {
        title.setText(getHaiString(R.string.print_detail));
        printData.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        printData.setAdapter(new PrintAdapter());
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_print;
    }

    @OnClick({R.id.common_title_back, R.id.print_selectAll, R.id.print_word1, R.id.print_print})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.print_selectAll:
            case R.id.print_word1:
                break;
            case R.id.print_print:
                break;
        }
    }
}
